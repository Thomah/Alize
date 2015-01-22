package alize.eole.thread;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.socket.TextMessage;

import alize.commun.Heure;
import alize.commun.exception.CalculException;
import alize.commun.modele.Arret;
import alize.commun.modele.Depot;
import alize.commun.modele.Intervalle;
import alize.commun.modele.Lieu;
import alize.commun.modele.Transition;
import alize.commun.modele.Vehicule;
import alize.commun.modele.ZoneDeCroisement;
import alize.commun.modele.tables.Periodicite;
import alize.commun.modele.tables.pojos.Zonedecroisement;
import alize.commun.service.StockageService;
import alize.eole.websocket.handler.WebsocketEndPoint;

public class CalculTask extends Thread {

	
	private StockageService stockageService;
	private WebsocketEndPoint websocket;
	private LocalDateTime debutEole;
	private LocalDateTime finEole;
	
	private TimerTask timerTask;


	private List<Lieu> listeLieux = new ArrayList<Lieu>();
	private List<Depot> listeDepots = new ArrayList<Depot>();
	private List<Transition> listeTransitions = new ArrayList<Transition>();
	private List<Vehicule> listeVehicules =  new ArrayList<Vehicule>();
	private List<ZoneDeCroisement> listesZoneDeCroisements = new ArrayList<ZoneDeCroisement>();
	private List<Periodicite> listePeriodicites =  new ArrayList<Periodicite>();
	private List<Arret> listeArrets =  new ArrayList<Arret>();
	
	private int debutJourneeSec = 0;
	private int finJourneeSec = 0;
	private Heure heure = new Heure(0,0,0);
	private Heure heure0 = new Heure(0,0,0);
	
	@Override
	public void run() {
		
	try {
			Heure debutJournee=new Heure (4,0,0);
			debutJourneeSec = debutJournee.toInt();
			Heure finJournee = new Heure (25,0,0);
			finJourneeSec = finJournee.toInt();
			initialisation();
			
			int n = 0;
			
			int horloge = debutJourneeSec;
			ajouterLigneLog("Go");
			while(horloge!=finJourneeSec){
				
				for(Vehicule v : listeVehicules){
				//Vehicule v=listeVehicules.get(0);
					if(v.getHeureProchainDepart() == horloge){
						Lieu l = v.getLieuActuel();
						if(l.isArret()){
							Arret a = trouverArret(l.getId());
							n = random(0, a.getListeTransitionsPossibles().size());
							Transition nouvelleTransition = a.getListeTransitionsPossibles().get(n);
							if(nouvelleTransition.getZonedecroisementId()!=null){
								ZoneDeCroisement z = trouverZoneDeCroisement(nouvelleTransition.getZonedecroisementId());
								if(z.isOccupee()){
									//throw new SolutionIncomptatibleException("Conflit d'entrée dans la zone de croisement " + z.getNom());
								}else{
									z.setOccupee(true);
								}
							}
							v.changerLieu(trouverLieu(nouvelleTransition.getId()));
							v.setHeureProchainDepart(v.getHeureProchainDepart()+timeToInt(nouvelleTransition.getDuree()));
							heure.toHeure(v.getHeureProchainDepart());
							ajouterLigneLog("Véhicule " + v.getId() +" : Arret " + a.getId() + " --> Transition " + nouvelleTransition.getId() + " (" + heure.toString() +").");
						}else{
							Transition t = trouverTransition(l.getId());
							Arret nouvelArret = t.getArretSuivant();
							v.changerLieu(nouvelArret.getLieu());
							Intervalle i = stockageService.getIntervalle(nouvelArret.getTempsimmobilisationId());
							n = random((new Heure(i.getMin())).toInt(), (new Heure(i.getMax())).toInt());
							v.setHeureProchainDepart(v.getHeureProchainDepart()+n);
							v.setHeureProchainDepart(v.getHeureProchainDepart()+n);
							heure.toHeure(v.getHeureProchainDepart());
							ajouterLigneLog("Véhicule " + v.getId() +" : Transition " + t.getId() + " --> Arret " + nouvelArret.getId() + " (" + heure.toString() +").");
						}
					}
				}
				
				horloge++;
			}
			
			timerTask.stopperTimer("Fin des calculs");
		} catch (CalculException e1) {
			timerTask.stopperTimer("Exception : " + e1.getMessage());
		}
		
		
	}

	private int random(int min, int max){
			return Math.round((int) (Math.random() * (max - min)) + min);
	}
	
	private void initialisation() throws CalculException {
		ajouterLigneLog(" INITIALISATION ");
		initialisationDepots();
		initialisationZonesDeCroisements();
		initialisationArrets();
		initialisationTransitions();
		
		Lieu lieu = trouverLieu(listeDepots.get(0).getId());
		initialisationVehicules(lieu);
	}

	private void initialisationZonesDeCroisements(){
		List<ZoneDeCroisement> listeZdc = stockageService.getZonesDeCroisement();
		for(ZoneDeCroisement z : listeZdc){
			z.setOccupee(false);
			z.setListeTransitionsConcernees(new ArrayList<Transition>());
			listesZoneDeCroisements.add(z);
		}
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

	
	private void initialisationTransitions() throws CalculException {
		// INITIALISATION DES ARRETS :
		List<Transition> listeT = stockageService.getTransitions();
		if (listeT.isEmpty()) {
			throw new CalculException("Aucune transition enregistrée.");
		}
		for (Transition t : listeT) {
			
			Lieu l = stockageService.getLieu(t.getId());
			
			l.setEstUnArret(false);
			t.setLieu(l);
			if(t.getArretprecedentId()==null){
				throw new CalculException("La transition d'ID " + t.getId() +" ,' n'a pas d'arrêt précédent.");
			}
			if(t.getArretsuivantId()==null){
				throw new CalculException("La transition d'ID " + t.getId() +" ,' n'a pas d'arrêt suivant.");
			}
			Heure duree = new Heure(t.getDuree());
			if(duree.comparerHeure(heure0)==0){
				throw new CalculException("La transition d'ID " + t.getId() +" ,' a une durée de 00:00:00.");
			}
			t.setArretPrecedent(trouverArret(t.getArretprecedentId()));
			t.setArretSuivant(trouverArret(t.getArretsuivantId()));
			listeLieux.add(l);
			listeTransitions.add(t);
			if(t.getZonedecroisementId()!=null){
				ZoneDeCroisement z = trouverZoneDeCroisement(t.getZonedecroisementId());
				z.getListeTransitionsConcernees().add(t);
			}
			ajouterLigneLog("Insertion transition : " + t.getId() + ".");
		}
	}
	
	private void initialisationArrets() throws CalculException {
		// INITIALISATION DES ARRETS :
		List<Arret> listeA = stockageService.getArrets();
		if (listeA.isEmpty()) {
			throw new CalculException("Aucun arrêt enregistré.");
		}
		listeArrets = new ArrayList<Arret>();
		for (Arret a : listeA) {
			Lieu l = stockageService.getLieu(a.getId());
			Intervalle i = stockageService.getIntervalle(a.getTempsimmobilisationId());
			l.setEstUnArret(true);
			a.setLieu(l);
			a.setListeTransitionsPossibles(stockageService.getTransitions(a.getId()));
			listeLieux.add(l);
			listeArrets.add(a);
			ajouterLigneLog("Insertion arret : " + a.getId() + ".");
		}
	}

	
	private void initialisationVehicules(Lieu lieu) throws CalculException {
		// INITIALISATION DES VEHICULES :
		List<Vehicule> listeV = stockageService.getVehicules();
		if (listeV.isEmpty()) {
			throw new CalculException("Aucun véhicule enregistré.");
		}
		for (Vehicule c : listeV) {
			alize.commun.modele.Vehicule vehicule = (alize.commun.modele.Vehicule) c;
			
			vehicule.setHeureProchainDepart(random(debutJourneeSec, debutJourneeSec+180));
			vehicule.setLieuActuel(lieu);
			listeVehicules.add(vehicule);
			lieu.ajouterUnVehicule(vehicule);
			ajouterLigneLog("Insertion vehicule : " + vehicule.toString()
					+ " (dans " + lieu.toString() + ").");
		}
	}

	private void initialisationPeriodicites() throws CalculException {
		List<alize.commun.modele.Periodicite> listePeriodicites = stockageService.getPeriodicites();
		if (listePeriodicites.isEmpty()) {
			throw new CalculException("Aucune périodicitée enregistrée.");
		}
		listePeriodicites = new ArrayList<alize.commun.modele.Periodicite>();
		for (alize.commun.modele.Periodicite p : listePeriodicites) {
			alize.commun.modele.Periodicite periodicite = stockageService.getPeriodicite(p.getId());
			listePeriodicites.add(periodicite);
			ajouterLigneLog("Insertion periodicite : " + periodicite.toString()
					+ ".");
		}
	}

	private ZoneDeCroisement trouverZoneDeCroisement(int id){
		ZoneDeCroisement zoneDeCroisement = null;
		for(ZoneDeCroisement z : listesZoneDeCroisements){
			if(z.getId()==id){
				zoneDeCroisement = z;
			}
		}
		return zoneDeCroisement;
	}
	
	private Lieu trouverLieu(int id){
		Lieu lieu = null;
		for(Lieu l : listeLieux){
			if(l.getId()==id){
				lieu = l;
			}
		}
		return lieu;
	}
	
	private Arret trouverArret(int id){
		Arret arret = null;
		for(Arret a : listeArrets){
			if(a.getId()==id){
				arret = a;
			}
		}
		return arret;
	}
	
	
	private Transition trouverTransition(int id){
		Transition transition = null;
		for(Transition t : listeTransitions){
			if(t.getId()==id){
				transition = t;
			}
		}
		return transition;
	}
	
	private Periodicite trouverPremierePeriodicite() {
		
//			Heure heureMin = new Heure(100, 0, 0);
//			Heure heureDebut = new Heure(0, 0, 0);
//			Periodicite periodicite = listePeriodicites.get(0);
//			for (Periodicite p : listePeriodicites) {
//				heureDebut = new Heure(p.getDebut());
//				//if (heureDebut.comparerHeure(heureMin) == -1) {
//					heureMin = heureDebut;
//					periodicite = p;
//				//}
//			}
//			return periodicite;
		return null;
	}

	// Calculer le temps de parcours à partir d'une liste de transitions (depuis
	// le départ du premier arrêt jusqu'à l'arrivée du dernier arrêt)
	public Heure calculerTempsParcours(List<Transition> listeTransitions) {
		Heure tempsParcours = new Heure(0, 0, 0);

		for (Transition t : listeTransitions) {
			//tempsParcours.ajouterHeure(new Heure(t.getDuree()));
			Arret a = t.getArretSuivant();
			Intervalle i = (Intervalle) stockageService.getIntervalle(a
					.getTempsimmobilisationId());
			//tempsParcours.ajouterHeure(new Heure(i.getPref()));
		}
		return tempsParcours;
	}

	
	private int timeToInt(Time t){
		Heure h = new Heure(t);
		return h.toInt();
	}
	
	public void ajouterLigneLog(String message) {
		this.getTimerTask().ajouterLigneLog(message);
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

	public TimerTask getTimerTask() {
		return timerTask;
	}

	public void setTimerTask(TimerTask timerTask) {
		this.timerTask = timerTask;
	}
	
	

}
