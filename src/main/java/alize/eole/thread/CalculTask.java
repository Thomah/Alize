package alize.eole.thread;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.datetime.joda.LocalDateTimeParser;
import org.springframework.format.datetime.joda.LocalTimeParser;
import org.springframework.web.socket.TextMessage;

import com.sun.glass.ui.CommonDialogs.Type;

import alize.commun.Heure;
import alize.commun.exception.CalculException;
import alize.commun.exception.SolutionIncomptatibleException;
import alize.commun.modele.Action;
import alize.commun.modele.Arret;
import alize.commun.modele.Depot;
import alize.commun.modele.Intervalle;
import alize.commun.modele.Lieu;
import alize.commun.modele.Terminus;
import alize.commun.modele.Transition;
import alize.commun.modele.TypeAction;
import alize.commun.modele.Vehicule;
import alize.commun.modele.Voie;
import alize.commun.modele.ZoneDeCroisement;
import alize.commun.modele.tables.Periodicite;
import alize.commun.modele.tables.pojos.VoieTransition;
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
	private List<Voie> listeVoies = new ArrayList<Voie>();
	private List<Terminus> listeTerminus = new ArrayList<Terminus>();
	private List<List<Action>> tableauActions = new ArrayList<>();
	
	
	
	private int debutJourneeSec = 0;
	private int finJourneeSec = 0;
	private Heure heure = new Heure(0,0,0);
	private Heure heure0 = new Heure(0,0,0);
	
	@Override
	public void run() {
		stockageService.supprimerToutesLesActions();
		try {
			Heure debutJournee = new Heure(4, 0, 0);
			debutJourneeSec = debutJournee.toInt();
			Heure finJournee = new Heure(26, 0, 0);
			finJourneeSec = finJournee.toInt();
			initialisation();
			int n = 0;

			ajouterLigneLog("Go");
			int horloge, time;
			int iterator = -1;
			while (iterator < 10) {
				System.out.println(iterator);
				ajouterLigneLog("Nouvelle itération.");
				tableauActions.add(new ArrayList<Action>());
				nettoyerLeReseau();
				horloge = debutJourneeSec;
				try {
					iterator++;
					while (horloge < finJourneeSec) {
						// System.out.println("Horloge :" + horloge + "/" +
						// finJourneeSec);
						for (Vehicule v : listeVehicules) {
							if (v.getHeureProchainDepart() == horloge) {
								Lieu l = trouverLieu(v.getLieuActuel().getId());
								if (l.isArret()) {

									Arret a = trouverArret(l.getId());

								
									//System.out.println("Arret :" + a);

									Terminus terminus = trouverTerminus(a
											.getId());
									if (terminus != null) {
										// Choix d'une voie
										List<Voie> voiesPossibles = new ArrayList<Voie>();
										for (Voie voie : listeVoies) {
											if (voie.getTerminusdepartId() == a
													.getId()) {
												voiesPossibles.add(voie);
											}
										}
										int nbVoiePossible = voiesPossibles
												.size();
										n = random(0, nbVoiePossible);
										Voie nouvelleVoie = voiesPossibles
												.get(n);
										v.setVoieActuelle(nouvelleVoie);
										stockageService
												.ajouterAction(
														toTime(v.getHeureProchainDepart()),
														(int) v.getId(),
														trouverVoie(
																v.getVoieActuelle()
																		.getId())
																.getId(),
														TypeAction.CHANGER_VOIE
																.ordinal(),
														nouvelleVoie.getId());
										tableauActions.get(iterator).add(
												trouverDerniereAction());

										boolean voieCommerciale = true;
										if (nouvelleVoie.getEstcommerciale() == 0) {
											voieCommerciale = false;
										}
										if (voieCommerciale != v
												.isEstCommercial()) {

											if (v.isEstCommercial()) {
												stockageService
														.ajouterAction(
																toTime(v.getHeureProchainDepart()),
																(int) v.getId(),
																v.getVoieActuelle()
																		.getId(),
																TypeAction.DEVENIR_NON_COMMERCIAL
																		.ordinal(),
																nouvelleVoie
																		.getId());
											} else {
												stockageService
														.ajouterAction(
																toTime(v.getHeureProchainDepart()),
																(int) v.getId(),
																v.getVoieActuelle()
																		.getId(),
																TypeAction.DEVENIR_COMMERCIAL
																		.ordinal(),
																nouvelleVoie
																		.getId());
											}
											stockageService.ajouterAction(toTime(v
													.getHeureProchainDepart()), (int) v
													.getId(), v.getVoieActuelle()
													.getId(), TypeAction.ARRIVER_ARRET
													.ordinal(), a.getId());
											tableauActions.get(iterator).add(
													trouverDerniereAction());
											v.setEstCommercial(voieCommerciale);
										}
									}

									Lieu nouveauLieu;
									Transition nouvelleTransition = null;

									for (int i = 0; i < a
											.getListeTransitionsPossibles()
											.size(); i++) {
										Transition t = trouverTransition(a
												.getListeTransitionsPossibles()
												.get(i).getId());
										if (v.getVoieActuelle()
												.contientTransition(t.getId())) {
											nouvelleTransition = t;
										}
									}
									if (nouvelleTransition == null) {
										throw new SolutionIncomptatibleException(
												"Aucune transition correspondant à la voie : "
														+ v.getVoieActuelle());
									}
									nouveauLieu = trouverLieu(nouvelleTransition
											.getId());

									if (nouveauLieu.estoccupe()) {
										throw new SolutionIncomptatibleException(
												"Conflit d'entrée dans la transition "
														+ nouveauLieu.getId()
														+ "(Occupee par : "
														+ nouveauLieu
																.getListeVehiculesPresents()
														+ ").");
									}

									if (nouvelleTransition
											.getZonedecroisementId() != null) {
										ZoneDeCroisement z = trouverZoneDeCroisement(nouvelleTransition
												.getZonedecroisementId());

										if (z.isOccupee()) {
											throw new SolutionIncomptatibleException(
													"Conflit d'entrée dans la zone de croisement "
															+ z.getNom());
										}
									}

									v.changerLieu(trouverLieu(nouvelleTransition
											.getId()));

									v.setHeureProchainDepart(v
											.getHeureProchainDepart()
											+ timeToInt(nouvelleTransition
													.getDuree()));
									heure.toHeure(v.getHeureProchainDepart());

//									System.out.println(toTime(horloge)
//											+ " Véhicule " + v.getId()
//											+ " : Arret " + a.getId()
//											+ " --> Transition "
//											+ nouvelleTransition.getId() + " ("
//											+ heure.toString() + ").");
//									ajouterLigneLog(toTime(horloge)
//											+ " Véhicule " + v.getId()
//											+ " : Arret " + a.getId()
//											+ " --> Transition "
//											+ nouvelleTransition.getId() + " ("
//											+ heure.toString() + ").");
									stockageService.ajouterAction(toTime(v
											.getHeureProchainDepart()), (int) v
											.getId(), v.getVoieActuelle()
											.getId(), TypeAction.QUITTER_ARRET
											.ordinal(), a.getId());
									tableauActions.get(iterator).add(
											trouverDerniereAction());

								} else {
									Transition t = trouverTransition(l.getId());

									Arret nouvelArret = trouverArret(t
											.getArretSuivant().getId());
									Lieu nouveauLieu = trouverLieu(nouvelArret
											.getId());
									if (nouveauLieu.estoccupe()
											&& nouvelArret.getId() != listeDepots
													.get(0).getId()) {
										throw new SolutionIncomptatibleException(
												"Conflit d'entrée dans l'arret : "
														+ nouveauLieu.getId());
									}

									v.changerLieu(trouverLieu(nouvelArret
											.getId()));

									Intervalle i = stockageService
											.getIntervalle(nouvelArret
													.getTempsimmobilisationId());

									n = random((new Heure(i.getMin())).toInt(),
											(new Heure(i.getMax())).toInt());
									time = v.getHeureProchainDepart() + n;

									v.setHeureProchainDepart(time);

									heure.toHeure(v.getHeureProchainDepart());
//									System.out.println(toTime(horloge)
//											+ " Véhicule " + v.getId()
//											+ " : Transition " + t.getId()
//											+ " --> Arret "
//											+ nouvelArret.getId() + " ("
//											+ heure.toString() + ").");
//									ajouterLigneLog(toTime(horloge)
//											+ " Véhicule " + v.getId()
//											+ " : Transition " + t.getId()
//											+ " --> Arret "
//											+ nouvelArret.getId() + " ("
//											+ heure.toString() + ").");

									stockageService.ajouterAction(toTime(time),
											(int) v.getId(), v
													.getVoieActuelle().getId(),
											TypeAction.ARRIVER_ARRET.ordinal(),
											nouvelArret.getId());
								}
							}
						}
						horloge++;
					}
				} catch (SolutionIncomptatibleException e) {
					ajouterLigneLog("Exception : " + e.getMessage());
				}
			}

			evaluation();

			timerTask.stopperTimer("Fin des calculs");
		} catch (CalculException e1) {
			timerTask.stopperTimer("Exception : " + e1.getMessage());
		}

	}
	
	private void evaluation(){
		List<Action> meilleuresActions = meilleuresActions();
		stockageService.supprimerToutesLesActions();
		for(Action a : meilleuresActions){
			stockageService.ajouterAction(a.getTime(), a.getVehiculeId(), a.getVoieId(), a.getTypeaction(), a.getParametre());
		}
	}
	
	private List<Action> meilleuresActions(){
		int poidsMax=0;
		int indexMeilleureListeAction = 0;
		for(List<Action> listeActions : tableauActions){
			int poids = 0;
			for(int i = 0; i<listeActions.size(); i++){
				Action a = listeActions.get(i);
				System.out.println("Id de l'action : " + a.getId());
				if(a.getTypeaction() == TypeAction.QUITTER_ARRET.ordinal()){
					Heure heureDepart =  new Heure (a.getTime());
					Action prochaineArrivee = trouverProchaineAction(TypeAction.ARRIVER_ARRET.ordinal(), listeActions, i, a.getParametre());

					System.out.println("Prochaine arrivée quitterArret : " + prochaineArrivee.getId());
					if(prochaineArrivee!=null){
						Heure heureArrivee = new Heure (prochaineArrivee.getTime());
						Heure tpsParcours = heureArrivee.soustraireHeures(heureArrivee,heureDepart);
						poids+=tpsParcours.toInt();
					}
				}
				if(a.getTypeaction() == TypeAction.ARRIVER_ARRET.ordinal()){
					Heure heureDepart =  new Heure (a.getTime());
					Action prochaineArrivee = trouverProchaineAction(TypeAction.QUITTER_ARRET.ordinal(), listeActions, i, a.getParametre());
					System.out.println("Prochaine arrivée arriverArret : " + prochaineArrivee.getId());
					if(prochaineArrivee!=null){
						Heure heureArrivee = new Heure (prochaineArrivee.getTime());
						Heure tpsParcours = heureArrivee.soustraireHeures(heureArrivee,heureDepart);
						poids-=tpsParcours.toInt();
					}
				}
				if(a.getTypeaction() == TypeAction.DEVENIR_COMMERCIAL.ordinal()){
					Heure heureDepart =  new Heure (a.getTime());
					Action prochaineArrivee = trouverProchaineAction(TypeAction.DEVENIR_NON_COMMERCIAL.ordinal(), listeActions, i, a.getParametre());
					System.out.println("Prochaine arrivée devenir commercial : " + prochaineArrivee.getId());
					if(prochaineArrivee!=null){
						Heure heureArrivee = new Heure (prochaineArrivee.getTime());
						Heure tpsParcours = heureArrivee.soustraireHeures(heureArrivee,heureDepart);
						poids+=tpsParcours.toInt();
					}
				}
				if(a.getTypeaction() == TypeAction.DEVENIR_NON_COMMERCIAL.ordinal()){
					Heure heureDepart =  new Heure (a.getTime());
					Action prochaineArrivee = trouverProchaineAction(TypeAction.DEVENIR_COMMERCIAL.ordinal(), listeActions, i, a.getParametre());
					System.out.println("Prochaine arrivée devenir non commercial : " + prochaineArrivee.getId());
					if(prochaineArrivee!=null){
						Heure heureArrivee = new Heure (prochaineArrivee.getTime());
						Heure tpsParcours = heureArrivee.soustraireHeures(heureArrivee,heureDepart);
						poids-=tpsParcours.toInt();
					}
				}
			}
			System.out.println(poids);
			if(listeActions==tableauActions.get(0)){
				poidsMax = poids;
			}
			if(poids>poidsMax){
				poidsMax=poids;
				indexMeilleureListeAction=tableauActions.indexOf(listeActions);
			}
		}
		return tableauActions.get(indexMeilleureListeAction);
	}

	
	private Action trouverProchaineAction(int typeAction, List<Action> listeAction, int curseur, int parametre){
		System.out.println(typeAction  + "---" + curseur + "---"  + parametre);
		for(int i = curseur+1; i< listeAction.size(); i++){
			System.out.println("Type  " + listeAction.get(i).getTypeaction());
			System.out.println("Param" + listeAction.get(i).getParametre());
			if(listeAction.get(i).getTypeaction()==typeAction && listeAction.get(i).getParametre()==parametre){
				return listeAction.get(i);
			}
		}
		return null;
	}
	
	@SuppressWarnings("deprecation")
	private Time toTime(int secondes){
		int heures = secondes / 3600;
		secondes%=3600;
		int minutes = secondes/60;
		secondes%=60;
		return new Time(heures,minutes, secondes);	
	}
	
	private int random(int min, int max){
			return Math.round((int) (Math.random() * (max - min)) + min);
	}
	
	private void nettoyerLeReseau(){
		Lieu lieu = trouverLieu(listeDepots.get(0).getId());
		for(Vehicule v: listeVehicules){
			v.changerLieu(lieu);
			v.setHeureProchainDepart(random(debutJourneeSec, finJourneeSec));
		}
	}
	
	private void initialisation() throws CalculException {
		listeArrets.clear();
		listeDepots.clear();
		listeLieux.clear();
		listePeriodicites.clear();
		listesZoneDeCroisements.clear();
		listeTransitions.clear();
		listeVehicules.clear();
		listeVoies.clear();
		
		ajouterLigneLog(" INITIALISATION ");
		initialisationDepots();
		initialisationZonesDeCroisements();
		initialisationArrets();
		initialisationTransitions();
		initialisationVoies();
		initialisationTerminus();
		
		Lieu lieu = trouverLieu(listeDepots.get(0).getId());
		initialisationVehicules(lieu);
	}

	private void initialisationVoies(){
		List<Voie> listeV = stockageService.getVoies();
		List<VoieTransition> listeVoieTransition = stockageService.getVoiesTransitions();
		Transition t = new Transition();
		for(Voie v : listeV){
			listeVoies.add(v);
			for(VoieTransition vt : listeVoieTransition){
				if(vt.getVoieId()==v.getId()){
					t=trouverTransition(vt.getTransitionId());
					v.getTransitions().add(t);
					t.setVoie(trouverVoie(v.getId()));
				}
			}			
		}
	}
	
	private void initialisationTerminus(){
		List<Terminus> listeT = stockageService.getTerminus();
		for(Terminus t : listeT){
			listeTerminus.add(t);
		}
	}
	
	private void initialisationZonesDeCroisements(){
		List<ZoneDeCroisement> listeZdc = stockageService.getZonesDeCroisement();
		for(ZoneDeCroisement z : listeZdc){
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
			
			vehicule.setHeureProchainDepart(random(debutJourneeSec, debutJourneeSec + 7200));
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
	
	private Terminus trouverTerminus(int id){
		Terminus terminus =null;
		for(Terminus d : listeTerminus){
			if(d.getId()==id){
				terminus = d;
			}
		}
		return terminus;
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
	
	private Voie trouverVoie(int id){
		Voie voie =null;
		for(Voie v : listeVoies){
			if(v.getId()==id){
				voie = v;
			}
		}
		return voie;
	}
	
	private Action trouverDerniereAction(){
		List<Action> actions = stockageService.getActions();
		return actions.get(actions.size()-1);
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
