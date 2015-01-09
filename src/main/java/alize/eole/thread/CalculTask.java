package alize.eole.thread;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

import alize.commun.service.StockageService;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.springframework.web.socket.TextMessage;

import com.sun.javafx.TempState;

import alize.commun.modele.tables.pojos.Arret;
import alize.commun.modele.tables.pojos.Intervalle;
import alize.commun.modele.tables.pojos.Periodicite;
import alize.commun.modele.tables.pojos.Terminus;
import alize.commun.modele.tables.pojos.Transition;
import alize.commun.modele.tables.pojos.Voie;
import alize.eole.websocket.handler.WebsocketEndPoint;


public class CalculTask extends Thread {

	
	private StockageService stockageService;
	private WebsocketEndPoint websocket;
	private LocalDateTime debutEole;
	private LocalDateTime finEole;
	
	private String journal = "";
	@Override
	public void run() { 
		
		
		List<Periodicite> listePeriodicites = stockageService.getPeriodicites();
		Periodicite p = listePeriodicites.get(0);
		Voie voie = stockageService.getVoie(p.getIdVoie());
		int idArretTemoin = p.getIdArret();
		//Calcul du temps de parcours du point de départ de la voie à l'arrêt spécifier dans la périodicité
		Terminus terminusDepart = stockageService.getTerminus(voie.getTerminusdepartId());
		calculerTempsParcours(terminusDepart.getId(), idArretTemoin);
		
		setDebutEole(LocalDateTime.now());
			while(LocalDateTime.now().isBefore(finEole)) {
				
				ZonedDateTime zdtDebut = Tasks.getCalculTask().getDebutEole().atZone(ZoneId.systemDefault());
				ZonedDateTime zdtFin = Tasks.getCalculTask().getFinEole().atZone(ZoneId.systemDefault());
				double percent = (((double)(new Date()).getTime() - (double)zdtDebut.toInstant().toEpochMilli()) / ((double)zdtFin.toInstant().toEpochMilli() - (double)zdtDebut.toInstant().toEpochMilli())) * 100;		

				String jSon = "{'avancement':'" + Double.toString(percent) + "','journal':'" + journal + "'}";
				jSon = jSon.replace('\'', '\"');
				websocket.sendMessage(new TextMessage(jSon));
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace(); 
				}
			}
	}
	
	public Time calculerTempsParcours(int idArretDepart, int idArretArrive){
		try {
			SimpleDateFormat formater = new SimpleDateFormat("hh:mm:ss");
			Date date;
			date = formater.parse("00:00:00");
			java.sql.Time time = new Time(date.getTime());
			Long tempsDeParcours = time.getTime();
			
			Arret arretDepart = stockageService.getArret(idArretDepart);
			Arret arretArrive = stockageService.getArret(idArretArrive);	
			Arret arretCourant = arretDepart;
			
			do{
				Intervalle intervalle = stockageService.getTempsImmobilisation(arretCourant.getTempsimmobilisationId());	
				tempsDeParcours += intervalle.getPref().getTime();
				//Récuperer la transition de l'arret courant
				Transition transition = stockageService.getTransition(arretCourant.getId());	
				//Ajouter le temps de transition au temps de parcours
				tempsDeParcours += transition.getDuree().getTime();
				//Remplacer l'arrêt courant par l'arrêt de destination 
				arretCourant = stockageService.getArret(transition.getArretsuivantId());
			}while(arretCourant!=arretArrive);
			
			
			//!!!!!!!!!!!!!!!!!!! On doit retourner le temps de parcours
			
			return time;
			
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public void ajouterLigneLog(String message) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		journal+="<p>[" + LocalDateTime.now().format(formatter) + "] " + message + "</p>";
	}

	public StockageService getStockageService() {
		return stockageService;
	}
	
	public void setStockageService(StockageService stockageService) {
		this.stockageService = stockageService;
	}

	public WebsocketEndPoint getWebsocket() {
		return websocket;
	}
	
	public void setWebsocket(WebsocketEndPoint websocket) {
		this.websocket = websocket;
	}
	
	public LocalDateTime getDebutEole() {
		return debutEole;
	}

	private void setDebutEole(LocalDateTime debutEole) {
		this.debutEole = debutEole;
	}

	public LocalDateTime getFinEole() {
		return finEole;
	}

	public void setFinEole(LocalDateTime finEole) {
		this.finEole = finEole;
	}

}
