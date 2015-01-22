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

import alize.commun.exception.CalculException;
import alize.commun.modele.*;
import alize.commun.modele.tables.pojos.Periodicite;
import alize.commun.modele.tables.pojos.Vehicule;
import alize.eole.websocket.handler.WebsocketEndPoint;

public class TimerTask extends Thread {

	
	private static CalculTask calculTask;
	private StockageService stockageService;
	private WebsocketEndPoint websocket;
	private LocalDateTime debutEole;
	private LocalDateTime finEole;
	
	private String journal = "";
	private boolean stop = false;
	
	@Override
	public void run() {
		ajouterLigneLog("DEBUT DES CALCULS");
		
		calculTask = new CalculTask();
		calculTask.setStockageService(stockageService);
		calculTask.setWebsocket(websocket);
		calculTask.setFinEole(finEole);
		calculTask.setTimerTask(this);
		calculTask.start();

		setDebutEole(LocalDateTime.now());

		while (LocalDateTime.now().isBefore(finEole)&&stop==false) {
			ZonedDateTime zdtDebut = Tasks.getTimerTask().getDebutEole()
					.atZone(ZoneId.systemDefault());
			ZonedDateTime zdtFin = Tasks.getTimerTask().getFinEole()
					.atZone(ZoneId.systemDefault());
			double percent = (((double) (new Date()).getTime() - (double) zdtDebut
					.toInstant().toEpochMilli()) / ((double) zdtFin.toInstant()
					.toEpochMilli() - (double) zdtDebut.toInstant()
					.toEpochMilli())) * 100;
			String jSon = "{'avancement':'" + Double.toString(percent)
					+ "','journal':'" + journal + "'}";
			jSon = jSon.replace('\'', '\"');
			websocket.sendMessage(new TextMessage(jSon));

			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
		ajouterLigneLog("FIN DES CALCULS");
		String jSon = "{'avancement':'" + Double.toString(100)
				+ "','journal':'" + journal + "'}";
		jSon = jSon.replace('\'', '\"');
		websocket.sendMessage(new TextMessage(jSon));
	}
	
	public void stopperTimer(String s){
		stop = true;
		ajouterLigneLog(s);
	}
	
	
	public void ajouterLigneLog(String message) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		journal += "<p>[" + LocalDateTime.now().format(formatter) + "] "
				+ message + "</p>";
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

	public String getJournal() {
		return journal;
	}

	public void setJournal(String journal) {
		this.journal = journal;
	}
	
	
	
	
	
	
	
	
	
	
	
/*	private StockageService stockageService;
	private WebsocketEndPoint websocket;
	private LocalDateTime debutEole;
	private LocalDateTime finEole;

	private String journal = "";

	private List<Depot> listeDepots = new ArrayList<Depot>();
	private List<Vehicule> listeVehicules =  new ArrayList<Vehicule>();
	private List<Periodicite> listePeriodicites =  new ArrayList<Periodicite>();
	private List<Arret> listeArrets =  new ArrayList<Arret>();


	@Override
	public void run() {
		setDebutEole(LocalDateTime.now());
		try {
			ajouterLigneLog("Test ");
			
			initialisation();
		

			//Periodicite premierePeriodicite = trouverPremierePeriodicite();
			//ajouterLigneLog("Premiere periodicite : "+ premierePeriodicite.toString());
			
			//Arret arretDebutJournee = trouverArretM(premierePeriodicite.getId());
			//Heure heureDebutDeJournee = new Heure(premierePeriodicite.getDebut());

			

			while (LocalDateTime.now().isBefore(finEole)) {
				ajouterLigneLog("Test ");
				ZonedDateTime zdtDebut = Tasks.getCalculTask().getDebutEole()
						.atZone(ZoneId.systemDefault());
				ZonedDateTime zdtFin = Tasks.getCalculTask().getFinEole()
						.atZone(ZoneId.systemDefault());
				double percent = (((double) (new Date()).getTime() - (double) zdtDebut.toInstant().toEpochMilli()) / ((double) zdtFin.toInstant().toEpochMilli() - (double) zdtDebut.toInstant().toEpochMilli())) * 100;
				System.out.println("Test 2");
				ajouterLigneLog("Test 2 ");
				String jSon = "{'avancement':'" + Double.toString(percent)
						+ "','journal':'" + journal + "'}";
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
		} catch (CalculException e1) {
			
			
			double percent = 100;

			System.out.println("Exception : " + e1.getMessage());
			ajouterLigneLog("Le calcul a été suspendu suite à l'exception suivante :");
			ajouterLigneLog(e1.getMessage());
			
			String jSon = "{'avancement':'" + Double.toString(percent)
					+ "','journal':'" + journal + "'}";
			jSon = jSon.replace('\'', '\"');
			websocket.sendMessage(new TextMessage(jSon));
		}
	}

	private void initialisation() throws CalculException {
		ajouterLigneLog(" INITIALISATION ");
		initialisationDepots();

		Depot depot = listeDepots.get(0);
		Lieu lieu = stockageService.getLieu(depot.getId());

		initialisationVehicules(lieu);
		initialisationPeriodicites();
		initialisationArrets();
	}

	private void initialisationDepots() throws CalculException {
		// INITIALISATION DES DEPOTS :

		List<Depot> listeDep = stockageService.getDepots();
		
		if (listeDep.isEmpty()) {
			throw new CalculException("Aucun dépôt enregistré.");
		}
		for (Depot d : listeDep) {
			listeDepots.add(d);
			d.setArret(stockageService.getArret(d.getId()));
			ajouterLigneLog("Insertion depot : " + d.getId() + ".");
		}
	}

	private void initialisationArrets() throws CalculException {
		// INITIALISATION DES ARRETS :
		List<Arret> listeArrets = stockageService.getArrets();
		if (listeArrets.isEmpty()) {
			throw new CalculException("Aucun arrêt enregistré.");
		}
		listeArrets = new ArrayList<Arret>();
		for (Arret a : listeArrets) {
			listeArrets.add(a);
			a.setLieu(stockageService.getLieu(a.getId()));
			ajouterLigneLog("Insertion arret : " + a.getId() + ".");
		}
	}

	private void initialisationVehicules(Lieu lieu) throws CalculException {
		// INITIALISATION DES VEHICULES :
		List<Vehicule> listeVehicules = stockageService.getVehicules();
		if (listeVehicules.isEmpty()) {
			throw new CalculException("Aucun véhicule enregistré.");
		}
		listeVehicules = new ArrayList<Vehicule>();
		for (Vehicule c : listeVehicules) {
			alize.commun.modele.Vehicule vehicule = (alize.commun.modele.Vehicule) c;
			listeVehicules.add(vehicule);
			lieu.ajouterUnVehicule(vehicule);
			ajouterLigneLog("Insertion vehicule : " + vehicule.toString()
					+ " (dans " + lieu.toString() + ").");
		}
	}

	private void initialisationPeriodicites() throws CalculException {
		List<Periodicite> listePeriodicites = stockageService.getPeriodicites();
		if (listePeriodicites.isEmpty()) {
			throw new CalculException("Aucune périodicitée enregistrée.");
		}
		listePeriodicites = new ArrayList<Periodicite>();
		for (Periodicite p : listePeriodicites) {
			Periodicite periodicite = (Periodicite) stockageService
					.getPeriodicite(p.getId());
			listePeriodicites.add(periodicite);
			ajouterLigneLog("Insertion periodicite : " + periodicite.toString()
					+ ".");
		}
	}

	private Periodicite trouverPremierePeriodicite() {
		
			Heure heureMin = new Heure(100, 0, 0);
			Heure heureDebut = new Heure(0, 0, 0);
			Periodicite periodicite = listePeriodicites.get(0);
			for (Periodicite p : listePeriodicites) {
				heureDebut = new Heure(p.getDebut());
				if (heureDebut.comparerHeure(heureMin) == -1) {
					heureMin = heureDebut;
					periodicite = p;
				}
			}
			return periodicite;
	}

	// Calculer le temps de parcours à partir d'une liste de transitions (depuis
	// le départ du premier arrêt jusqu'à l'arrivée du dernier arrêt)
	public Heure calculerTempsParcours(List<Transition> listeTransitions) {
		Heure tempsParcours = new Heure(0, 0, 0);

		for (Transition t : listeTransitions) {
			tempsParcours.ajouterHeure(new Heure(t.getDuree()));
			Arret a = t.getArretSuivant();
			Intervalle i = (Intervalle) stockageService.getIntervalle(a
					.getTempsimmobilisationId());
			tempsParcours.ajouterHeure(new Heure(i.getPref()));
		}
		return tempsParcours;
	}

	public Arret trouverArretM(int id) {
		Arret arret = null;
		for (Arret a : listeArrets) {
			if (a.getId() == id) {
				arret = a;
			}
		}
		return arret;
	}

	public void ajouterLigneLog(String message) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		journal += "<p>[" + LocalDateTime.now().format(formatter) + "] "
				+ message + "</p>";
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
	}*/

}
