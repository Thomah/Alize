package alize.eole.thread;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

import alize.commun.Heure;
import alize.commun.service.StockageService;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.socket.TextMessage;

import com.sun.javafx.TempState;

import alize.commun.modele.tables.pojos.Arret;
import alize.commun.modele.tables.pojos.Depot;
import alize.commun.modele.tables.pojos.Intervalle;
import alize.commun.modele.tables.pojos.Periodicite;
import alize.commun.modele.tables.pojos.Terminus;
import alize.commun.modele.tables.pojos.Transition;
import alize.commun.modele.tables.pojos.Vehicule;
import alize.commun.modele.tables.pojos.Voie;
import alize.eole.modele.ArretM;
import alize.eole.modele.DepotM;
import alize.eole.modele.LieuM;
import alize.eole.modele.PeriodiciteM;
import alize.eole.modele.VehiculeM;
import alize.eole.websocket.handler.WebsocketEndPoint;


public class CalculTask extends Thread {

	
	private StockageService stockageService;
	private WebsocketEndPoint websocket;
	private LocalDateTime debutEole;
	private LocalDateTime finEole;
	
	private String journal = "";
	
	private List<DepotM> listeDepotsM;
	private List<VehiculeM> listeVehiculesM;
	private List<PeriodiciteM> listePeriodicitesM;
	private List<ArretM> listeArretsM;
	
	@Override
	public void run() { 
		initialisation();
		
		PeriodiciteM premierePeriodicite = trouverPremierePeriodicite();
		if(premierePeriodicite==null){
			ajouterLigneLog("Aucune Periodicite");
		}else{
			ajouterLigneLog("La premiere periodicite est : " + premierePeriodicite.toString());
		}
		ArretM arretDebutJournee = trouverArretM(premierePeriodicite.getPeriodicite().getId());
		Heure heureDebutDeJournee = new Heure(premierePeriodicite.getPeriodicite().getDebut());
		
		
		
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
	
	
	private void initialisation(){
		ajouterLigneLog(" INITIALISATION ");
		initialisationDepots();
		initialisationVehicules(listeDepotsM.get(0));
		initialisationPeriodicites();
		initialisationArrets();
	}
	
	private void initialisationDepots(){
		//INITIALISATION DES DEPOTS :
		List<Depot> listeDepots = stockageService.getDepots();
		listeDepotsM = new ArrayList<DepotM>();
		for (Depot d : listeDepots) {
			DepotM depot = new DepotM(d, stockageService.getArret(d
					.getArretId()));
			listeDepotsM.add(depot);
			ajouterLigneLog("Insertion depot : " + depot.getDepot().getId() + ".");
		}
	}
	
	private void initialisationArrets(){
		//INITIALISATION DES ARRETS :
		List<Arret> listeArrets = stockageService.getArrets();
		listeArretsM = new ArrayList<ArretM>();
		for (Arret a : listeArrets) {
			ArretM arret = new ArretM(a);
			listeArretsM.add(arret);
			ajouterLigneLog("Insertion arret : " + arret.getArret().getId() + ".");
		}
	}
	
	private void initialisationVehicules(LieuM lieu){
		// INITIALISATION DES VEHICULES :
		List<Vehicule> listeVehicules = stockageService.getVehicules();
		listeVehiculesM = new ArrayList<VehiculeM>();
		for (Vehicule c : listeVehicules) {
			VehiculeM vehicule = new VehiculeM(c, lieu);
			listeVehiculesM.add(vehicule);
			lieu.ajouterUnVehicule(vehicule);
			ajouterLigneLog("Insertion vehicule : " + vehicule.toString() + " (dans " + lieu.toString()+").");
		}
	}
	
	private void initialisationPeriodicites(){
		List<Periodicite> listePeriodicites = stockageService.getPeriodicites();
		listePeriodicitesM = new ArrayList<PeriodiciteM>();
		for (Periodicite p : listePeriodicites) {
			PeriodiciteM periodicite = new PeriodiciteM(p);
			listePeriodicitesM.add(periodicite);
			ajouterLigneLog("Insertion periodicite : " + periodicite.toString() + ".");
		}
	}
	
	private PeriodiciteM trouverPremierePeriodicite(){
		if(listePeriodicitesM.size()!=0){
			Heure heureMin = new Heure(100,0,0);
			Heure heureDebut = new Heure(0,0,0);
			PeriodiciteM periodicite = listePeriodicitesM.get(0);
			for(PeriodiciteM p : listePeriodicitesM){
				heureDebut= new Heure(p.getPeriodicite().getDebut());
				if(heureDebut.comparerHeure(heureMin)==-1){
					heureMin=heureDebut;
					periodicite = p;
				}
			}
			return periodicite;
		}else{
			return null;
		}
	}
	
	
	private void assignerVehiculesLieu(List<Vehicule> listeVehicules, LieuM lieu){
		System.out.println("Lieu : " + lieu +"\nVehcs : " +listeVehicules);
		for(Vehicule v : listeVehicules){
			VehiculeM vehicule = new VehiculeM(v,lieu);

			System.out.println("\nVehc : " +v);
			lieu.getListeVehiculesPresents().add(vehicule);
		}

		System.out.println("\nVehcs : " +listeVehicules);
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
			
			return time;
			
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	
	public ArretM trouverArretM(int id){
		ArretM arret = null;
		for(ArretM a : listeArretsM){
			if(a.getArret().getId()==id){
				arret=a;
			}
		}
		return arret;
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
