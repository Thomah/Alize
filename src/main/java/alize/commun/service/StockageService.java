package alize.commun.service;

import alize.commun.modele.*;
import alize.commun.modele.tables.pojos.Conducteur;
import alize.commun.modele.tables.pojos.Ligne;
import alize.commun.modele.tables.pojos.Terminus;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Map;

/**
 * Service associé à la gestion du stockage des données
 * 
 * @author Thomas [TH]
 * @version 1
 */
public interface StockageService {
	
	/* GESTION DES LIGNES */
	
	/**
	 * Récupère les lignes stockées en BDD
	 * 
	 * @return La liste des lignes stockées en BDD
	 * @author Thomas [TH]
	 * @version 1
	 */
	public List<Ligne> getLignes();

	/**
	 * Met à jour la ligne stockée en BDD
	 * 
	 * @param id L'identifiant de la ligne à mettre à jour
	 * @param colonne La colonne mise à jour
	 * @param valeur La valeur mise à jour
	 * @author Thomas [TH]
	 * @version 1
	 */
	public void updateLigne(int id, String colonne, Object valeur);
	
	/**
	 * Créer une nouvelle ligne en BDD
	 * 
	 * @author Thomas [TH]
	 * @version 1
	 */
	public void ajouterLigne();
	
	/**
	 * Supprime une ligne en BDD selon son ID
	 * 
	 * @param id L'identifiant de la ligne à supprimer
	 * @author Thomas [TH]
	 * @version 1
	 */
	public void supprimerLigne(int id);

	/* ATTRIBUTION DES VOIES AUX LIGNES */

	/**
	 * Récupère les voies non attribuées à la ligne d'identifiant donné stockées en BDD
	 * 
	 * @param idLigne L'identifiant de la ligne concernée
	 * @return La liste des voies non attribuées à la ligne d'identifiant donné
	 * @author Thomas [TH]
	 * @version 1
	 */
	public Map<Voie, String> getVoiesNonAttribuees(int idLigne);

	/**
	 * Récupère les voies attribuées à la ligne d'identifiant donné stockées en BDD
	 * 
	 * @param idLigne L'identifiant de la ligne concernée
	 * @return La liste des voies attribuées à la ligne d'identifiant donné
	 * @author Thomas [TH]
	 * @version 1
	 */
	public Map<Voie, String> getVoiesAttribuees(int idLigne);

	/**
	 * Ajoute une association ligne / voie en BDD
	 * 
	 * @param idVoie L'identifiant de la voie
	 * @param idLigne L'identifiant de la ligne
	 * @author Thomas [TH]
	 * @version 1
	 */
	public void ajouterLigneVoie(int idVoie, int idLigne);

	/**
	 * Supprime une association ligne / voie en BDD
	 * 
	 * @param idVoie L'identifiant de la voie
	 * @param idLigne L'identifiant de la ligne
	 * @author Thomas [TH]
	 * @version 1
	 */
	public void supprimerLigneVoie(int idVoie, int idLigne);
	
	/* GESTION DES VOIES */
	
	/**
	 * Récupère les voies stockées en BDD
	 * 
	 * @return La liste des voies stockées en BDD
	 * @author Thomas [TH]
	 * @version 1
	 */
	public List<Voie> getVoies();
	
	
	/**
	 * Récupère les voies de la ligne sélectionnée stockées en BDD
	 * 
	 * @param idLigne L'identifiant de la ligne concernée
	 * @return La liste des voies de la ligne sélectionnée stockées en BDD
	 * @author Thomas [TH]
	 * @version 1
	 */
	public List<Voie> getVoiesPourLaLigne(int idLigne);
	
	/**
	 * Récupère les informations stockées en BDD sur la voie indiquée par son id
	 * 
	 * @param idVoie L'identifiant de la voie
	 * @return La voie indiquée par son id stockées en BDD
	 * @author Thomas [TH]
	 * @version 1
	 */
	public Voie getVoie(int idVoie);
	
	/**
	 * Met à jour la ligne stockée en BDD
	 * 
	 * @param id L'identifiant de la voie à mettre à jour
	 * @param colonne La colonne mise à jour
	 * @param valeur La valeur mise à jour
	 * @author Thomas [TH]
	 * @version 1
	 */
	public void updateVoie(int id, String colonne, Object valeur);

	/**
	 * Créer une nouvelle voie en BDD
	 * 
	 * @author Thomas [TH]
	 * @version 1
	 */
	public void ajouterVoie();
	
	/**
	 * Supprime une voie en BDD selon son ID
	 * 
	 * @param id L'identifiant de la voie à supprimer
	 * @author Thomas [TH]
	 * @version 1
	 */
	public void supprimerVoie(int id);

	/* ATTRIBUTION DES TRANSITIONS AUX VOIES */

	/**
	 * Récupère les transitions non attribuées à la voie d'identifiant donné stockées en BDD
	 * 
	 * @param idVoie L'identifiant de la voie concernée
	 * @return La liste des transitions non attribuées à la voie d'identifiant donné
	 * @author Thomas [TH]
	 * @version 2
	 */
	public Map<Transition, String> getTransitionsNonAttribuees(int idVoie);

	/**
	 * Récupère les transitions attribuées à la voie d'identifiant donné stockées en BDD
	 * 
	 * @param idVoie L'identifiant de la voie concernée
	 * @return La liste des transitions attribuées à la voie d'identifiant donné
	 * @author Thomas [TH]
	 * @version 3
	 */
	public List<Transition> getTransitionsAttribuees(int idVoie);

	/**
	 * Récupère les transitions dont l'arrêt précédent est donné en paramètre.
	 * 
	 * @param idArretPrecedent L'identifiant de l'arrêt précédent
	 * @return La liste des transitions dont l'arrêt précédent est donné en paramètre
	 * @author Cyril [CS]
	 * @version 1
	 */
	public List<Transition> getTransitions(int idArretPrecedent);
	
	/**
	 * Récupère la transition d'ID donné en paramètre.
	 * 
	 * @param id l'ID de la transition
	 * @return La transition dont l'ID est donné en paramètre
	 * @author Cyril [CS]
	 * @version 1
	 */
	public Transition getTransition(int id);
	
	/**
	 * Ajoute une association voie / transition en BDD
	 * 
	 * @param idVoie L'identifiant de la voie
	 * @param idTransition L'identifiant de la transition
	 * @author Thomas [TH]
	 * @version 2
	 */
	public void ajouterVoieTransition(int idVoie, int idTransition);

	/**
	 * Supprime une association voie / transition en BDD
	 * 
	 * @param idVoie L'identifiant de la voie
	 * @param idTransition L'identifiant de la transition
	 * @author Thomas [TH]
	 * @version 2
	 */
	public void supprimerVoieTransition(int idVoie, int idTransition);
	
	/* GESTION DES ARRETS */
	
	/**
	 * Met à jour l'arret stocké en BDD
	 * 
	 * @param id L'identifiant de l'arret à mettre à jour
	 * @param colonne La colonne mise à jour
	 * @param valeur La valeur mise à jour
	 * @author Cyril [CS]
	 * @version 1
	 */
	public void updateArret(int id, String colonne, Object valeur);
	
	/**
	 * Récupère les arrets stockées en BDD
	 * 
	 * @return La liste des arrets stockées en BDD
	 * @author Thomas [TH]
	 * @version 1
	 */
	public List<Arret> getArrets();
	
	/**
	 * Récupère les arrets où les échanges conducteurs sont possibles stockées en BDD
	 * 
	 * @return La liste des arrets stockées en BDD
	 * @author Thomas [TH]
	 * @version 1
	 */
	public List<Arret> getArretsEchangesConducteurs();

	/**
	 * Récupère les dépôts stockées en BDD
	 * 
	 * @return La liste des dépôts stockées en BDD
	 * @author Thomas [TH]
	 * @version 1
	 */
	public List<Depot> getDepots();

	/**
	 * Récupère les terminus stockées en BDD
	 * 
	 * @return La liste des terminus stockées en BDD
	 * @author Thomas [TH]
	 * @version 1
	 */
	public List<Terminus> getTerminus();
	
	/**
	 * Récupère le terminus stocké en BDD en fonction de son ID
	 * 
	 * @param id L'identifiant du terminus
	 * @return Le terminus stockées en BDD en fonction de son ID
	 * @author Cyril [CS]
	 * @version 1
	 */
	public Terminus getTerminus(int id);
	
	/**
	 * Ajoute un arrêt en BDD
	 * 
	 * @author Cyril [CS]
	 * @version 1
	 */
	public void ajouterArret();
	
	/**
	 * Récupère l'arrets stocké en BDD en fonction de son ID
	 * 
	 * @param id L'identifiant de l'arrêt
	 * @return L'arret stocké en BDD en fonction de son ID
	 * @author Cyril [CS]
	 * @version 1
	 */
	public Arret getArret(int id);
	
	/**
	 * Supprime un arrêt en BDD selon son ID
	 * 
	 * @param id L'identifiant de l'arrêt à supprimer
	 * @author Cyril [CS]
	 * @version 1
	 */
	public void supprimerArret(int id);
	
	/**
	 * Récupère les arrets de la voie sélectionnée stockées en BDD
	 * 
	 * @param idVoie L'identifiant de la voie concernée
	 * @return La liste des arrets de la voie sélectionnée stockées en BDD
	 * @author Thomas [TH]
	 * @version 1
	 */
	public List<Arret> getArretsPourLaVoie(int idVoie);
	
	/**
	 * Récupère l'identifiant et le nom des terminus pour l'ID de voie donnée
	 * 
	 * @param idVoie L'identifiant de la voie concernée
	 * @return La liste des identifiants et noms des terminus pour l'ID de voie donnée
	 * @author Thomas [TH]
	 * @version 1
	 */
	public Map<Integer, String> getTerminusVoie(int idVoie);

	/* GESTION DES TRANSITIONS */

	/**
	 * Récupère les transitions stockées en BDD
	 * 
	 * @return La liste des transitions stockées en BDD
	 * @author Thomas [TH]
	 * @version 1
	 */
	public List<Transition> getTransitions();

	/**
	 * Met à jour la transitions stockée en BDD
	 * 
	 * @param id L'identifiant de la transitions à mettre à jour
	 * @param colonne La colonne mise à jour
	 * @param valeur La valeur mise à jour
	 * @author Thomas [TH]
	 * @version 1
	 */
	public void updateTransition(int id, String colonne, String valeur);

	/**
	 * Créer une nouvelle transition en BDD
	 * 
	 * @author Thomas [TH]
	 * @version 1
	 */
	public void ajouterTransition();

	/**
	 * Supprime une transition en BDD selon son ID
	 * 
	 * @param id L'identifiant de la transition à supprimer
	 * @author Thomas [TH]
	 * @version 1
	 */
	public void supprimerTransition(int id);
	
	/* GESTION DES ZONES DE CROISEMENT */
	
	/**
	 * Récupère les zones de croisement stockées en BDD
	 * 
	 * @return La liste des zones de croisement stockées en BDD
	 * @author Thomas [TH]
	 * @version 1
	 */
	public List<ZoneDeCroisement> getZonesDeCroisement();

	/**
	 * Met à jour la zone de croisement stockée en BDD
	 * 
	 * @param id L'identifiant de la zone de croisement à mettre à jour
	 * @param colonne La colonne mise à jour
	 * @param valeur La valeur mise à jour
	 * @author Thomas [TH]
	 * @version 1
	 */
	public void updateZoneDeCroisement(int id, String colonne, String valeur);

	/**
	 * Créer une nouvelle zone de croisement en BDD
	 * 
	 * @author Thomas [TH]
	 * @version 1
	 */
	public void ajouterZoneDeCroisement();

	/**
	 * Supprime une zone de croisement en BDD selon son ID
	 * 
	 * @param id L'identifiant de la zone de croisement à supprimer
	 * @author Thomas [TH]
	 * @version 1
	 */
	public void supprimerZoneDeCroisement(int id);
	
	/* GESTION DES PERIODICITES */
	
	/**
	 * Récupère les périodicités stockées en BDD
	 * 
	 * @return La liste des périodicités stockées en BDD
	 * @author Thomas [TH]
	 * @version 1
	 */
	public List<Periodicite> getPeriodicites();

	/**
	 * Renvoi la périodicité stockée dans la BDD en fonction de sont ID
	 * 
	 * @param id L'identifiant de la périodicité
	 * @return Periodicite La périodicité stockée dans la BDD
	 * @author Cyril [CS]
	 * @version 1
	 */
	public Periodicite getPeriodicite(int id);
	
	/**
	 * Récupère les périodicités pour la voie et l'arret sélectionnés stockées en BDD
	 * 
	 * @param idVoie L'identifiant de la voie concernée
	 * @param idArret L'identifiant de l'arret concerné
	 * @return La liste des périodicités pour la voie et l'arret sélectionnés stockées en BDD
	 * @author Thomas [TH]
	 * @version 1
	 */
	public List<Periodicite> getPeriodicites(int idVoie, int idArret);

	/**
	 * Créer une périodicité en BDD
	 *
	 * @param idVoie L'identifiant de la voie concernée
	 * @param idArret L'identifiant de l'arret concerné
	 * @author Thomas [TH]
	 * @version 1
	 */
	public void ajouterPeriodicite(int idVoie, int idArret);
	
	/**
	 * Supprime la périodicité indiquée stockées en BDD
	 *
	 * @param id L'identifiant de la périodicité à supprimer
	 * @author Thomas [TH]
	 * @version 1
	 */
	public void supprimerPeriodicite(int id);
	
	/**
	 * Met à jour la périodicité indiquée stockées en BDD
	 * 
	 * @param id L'identifiant de la périodicité à mettre à jour
	 * @param colonne La colonne mise à jour
	 * @param valeur La valeur mise à jour
	 * @author Thomas [TH]
	 * @version 1
	 */
	public void updatePeriodicite(int id, String colonne, Time valeur);

	/* GESTION DES INTERVALLES */

	/**
	 * Récupère les intervalles stockés en BDD
	 * 
	 * @return La liste des intervalles stockés en BDD
	 * @author Thomas [TH]
	 * @version 1
	 */
	public List<Intervalle> getIntervalles();

	/**
	 * Récupère l'intervalle stocké en BDD
	 * 
	 * @param id L'identifiant de l'intervalle souhaité
	 * @return L'intervalle correspondant à l'Id
	 * @author Cyril [CS]
	 * @version 1
	 */
	public Intervalle getIntervalle(int id);
	
	/**
	 * Récupère les intervalles du réseau stockés en BDD
	 * 
	 * @return La liste des intervalles stockes en BDD
	 * @author Cyril [CS]
	 * @version 1
	 */
	public List<Intervalle> getTempsImmobilisation();

	/**
	 * Récupère l'intervalle du réseau  stockés en BDD en fonction de son ID
	 * 
	 * @param idTempsImmobilisation L'identifiant de l'intervalle
	 * @return Intervalle L'intervalle correspondant à l'id passé en paramètre et stockes en BDD
	 * @author Cyril [CS]
	 * @version 1
	 */
	public Intervalle getTempsImmobilisation(int idTempsImmobilisation);

	/**
	 * Met à jour le temps d'immobilisation d'un arrêt
	 * 
	 * @param id L'identifiant de l'arrêt
	 * @param colonne L'attribut du temps d'immobilisation à modifier (MIN, PREF, MAX) 
	 * @param valeur La nouvelle valeur à enregistrer.
	 * @author Cyril [CS]
	 * @version 1
	 */
	public void updateTempsImmobilisationArret(int id, String colonne, Object valeur);
	
	/* GESTION DES TERMINUS */
	
	/**
	 * Renvoie 1 si l'arrêt définit par idArret est un terminus, 0 sinon
	 * 
	 * @param idArret L'identifiant de l'arrêt
	 * @return true si l'arret est un terminus, false sinon
	 * @author Cyril [CS]
	 * @version 1
	 */
	public boolean getEstTerminus(int idArret);

	/**
	 * Créer un terminus en BDD
	 * 
	 * @param idArret L'identifiant de l'arret concerné
	 * @author Cyril [CS]
	 * @version 1
	 */
	public void ajouterTerminus(int idArret);
	
	/**
	 * Supprime le terminus indiqué par l'ID de son arret en BDD
	 * 
	 * @param id L'identifiant de l'arret associer au terminus à supprimer
	 * @author Cyril [CS]
	 * @version 1
	 */
	void supprimerTerminus(int id);
	
	/* GESTION DES DEPOTS */
	
	/**
	 * Renvoie 1 si l'arrêt définit par idArret est un dépôt, 0 sinon
	 * 
	 * @param idArret L'identifiant de l'arrêt
	 * @return true si l'arret est un dépot, false sinon
	 * @author Cyril [CS]
	 * @version 1
	 */
	public boolean getEstDepot(int idArret);

	/**
	 * Créer un Dépôt en BDD
	 * 
	 * @param idArret L'identifiant de l'arret concerné
	 * @author Cyril [CS]
	 * @version 1
	 */
	void ajouterDepot(int idArret);

	/**
	 * Supprime le dépot indiqué par l'ID de son arret  en BDD
	 * 
	 * @param id L'identifiant de l'arret associer au dépôt à supprimer
	 * @author Cyril [CS]
	 * @version 1
	 */
	void supprimerDepot(int id);

	/* GESTION DES FEUILLES DE SERVICE */

	/**
	 * Récupère les feuilles de service stockées en BDD
	 * 
	 * @return La liste des feuilles de service stockées en BDD
	 * @author Thomas [TH]
	 * @version 1
	 */
	public List<Feuilledeservice> getFDS();

	/**
	 * Récupère la feuille de service applicable à la date donnée stockées en BDD
	 * 
	 * @param date La date voulue
	 * @return La feuille de service applicable à la date donnée stockées en BDD
	 * @author Thomas [TH]
	 * @version 1
	 */
	public Feuilledeservice getFDS(Date date);
	
	/**
	 * Met à jour la feuille de service indiquée stockées en BDD
	 * 
	 * @param id L'identifiant de la feuille de service à mettre à jour
	 * @param colonne La colonne mise à jour
	 * @param valeur La valeur mise à jour
	 * @author Thomas [TH]
	 * @version 1
	 */
	public void updateFDS(int id, String colonne, String valeur);

	/**
	 * Créer une nouvelle feuille de service en BDD
	 * 
	 * @author Thomas [TH]
	 * @version 1
	 */
	public void ajouterFDS();

	/**
	 * Supprime une feuille de service en BDD selon son ID
	 * 
	 * @param id L'identifiant de la feuille de service à supprimer
	 * @author Thomas [TH]
	 * @version 1
	 */
	public void supprimerFDS(int id);
	
	/* GESTION DES LIEUX */
	
	/**
	 * Récupère un lieu en fonction de son ID
	 * 
	 * @param id L'identifiant du lieu en fonction de l'ID
	 * @return Le lieu associé à l'ID
	 * @author Cyril [CS]
	 * @version 1
	 */
	public Lieu getLieu(int id);
	
	/**
	 * Récupère tous les lieux de la BDD
	 * 
	 * @return Les lieux de la BDD
	 * @author Cyril [CS]
	 * @version 1
	 */
	public List<Lieu> getLieux();
	
	
	/* ATTRIBUTION DES PERIODICITES AUX FEUILLES DE SERVICE */

	/**
	 * Récupère les périodicités non attribuées à la feuille de service d'identifiant donné stockées en BDD
	 * 
	 * @param idFDS L'identifiant de la feuille de service concernée
	 * @return La liste des périodicités non attribuées à la feuille de service d'identifiant donné
	 * @author Thomas [TH]
	 * @version 1
	 */
	public List<Periodicite> getPeriodicitesNonAttribuees(int idFDS);

	/**
	 * Récupère les périodicités attribuées à la feuille de service d'identifiant donné stockées en BDD
	 * 
	 * @param idFDS L'identifiant de la feuille de service concernée
	 * @return La liste des périodicités attribuées à la feuille de service d'identifiant donné
	 * @author Thomas [TH]
	 * @version 1
	 */
	public List<Periodicite> getPeriodicitesAttribuees(int idFDS);

	/**
	 * Ajoute une association fds / périodicité en BDD
	 * 
	 * @param idFDS L'identifiant de la feuille de service
	 * @param idPeriodicite L'identifiant de la périodicité
	 * @author Thomas [TH]
	 * @version 1
	 */
	public void ajouterFDSPeriodicite(int idFDS, int idPeriodicite);

	/**
	 * Supprime une association fds / périodicité en BDD
	 * 
	 * @param idFDS L'identifiant de la feuille de service
	 * @param idPeriodicite L'identifiant de la périodicité
	 * @author Thomas [TH]
	 * @version 1
	 */
	public void supprimerFDSPeriodicite(int idFDS, int idPeriodicite);
	
	/* GESTION DES SERVICES */

	/**
	 * Récupère les services stockés en BDD
	 * 
	 * @return La liste des services stockés en BDD
	 * @author Thomas [TH]
	 * @version 1
	 */
	public List<Service> getServices();

	/**
	 * Récupère les services correspondant à la feuille de service indiquée stockés en BDD
	 * 
	 * @param idFDS L'identifiant de la feuille de service
	 * @param date La date souhaitée
	 * @return La liste des services correspondant à la feuille de service indiquée stockés en BDD
	 * @author Thomas [TH]
	 * @version 1
	 */
	public List<Service> getServices(int idFDS, Date date);

	/**
	 * Récupère les services tous les services avec les conducteurs associés à la date indiquée stockés en BDD
	 * 
	 * @param date La date souhaitée
	 * @return La liste des services tous les services avec les conducteurs associés à la date indiquée stockés en BDD
	 * @author Thomas [TH]
	 * @version 1
	 */
	public Map<Service, Integer> getServices(String date);
	
	/**
	 * Met à jour le service indiqué stocké en BDD
	 * 
	 * @param id L'identifiant du service à mettre à jour
	 * @param colonne La colonne mise à jour
	 * @param valeur La valeur mise à jour
	 * @author Thomas [TH]
	 * @version 1
	 */
	public void updateService(int id, String colonne, String valeur);

	/**
	 * Créer un nouveau service en BDD
	 * 
	 * @author Thomas [TH]
	 * @version 1
	 */
	public void ajouterService();

	/**
	 * Supprime un service en BDD selon son ID
	 * 
	 * @param id L'identifiant du service à supprimer
	 * @author Thomas [TH]
	 * @version 1
	 */
	public void supprimerService(int id);

	/* GESTION DES VACATIONS */

	/**
	 * Récupère les vacations pour le service et le véhicule indiqués stockées en BDD
	 * 
	 * @param idService L'identifiant du service
	 * @param idVehicule L'identifiant du véhicule
	 * @return La liste des vacations pour le service et le véhicule indiqués stockées en BDD
	 * @author Thomas [TH]
	 * @version 2
	 */
	public List<Vacation> getVacations(int idService, int idVehicule);

	/**
	 * Met à jour la vacation indiquée stockée en BDD
	 * 
	 * @param id L'identifiant de la vacation à mettre à jour
	 * @param colonne La colonne mise à jour
	 * @param valeur La valeur mise à jour
	 * @author Thomas [TH]
	 * @version 1
	 */
	public void updateVacation(int id, String colonne, String valeur);

	/**
	 * Créer une nouvelle vacation en BDD
	 * 
	 * @param idService L'identifiant du service associé
	 * @param idVehicule L'identifiant du véhicule associé
	 * @author Thomas [TH]
	 * @version 2
	 */
	public void ajouterVacation(int idService, int idVehicule);

	/**
	 * Supprime une vacation en BDD selon son ID
	 * 
	 * @param id L'identifiant de la vacation à supprimer
	 * @author Thomas [TH]
	 * @version 1
	 */
	public void supprimerVacation(int id);
	
	/* GESTION DES VEHICULES */

	/**
	 * Récupère les véhicules stockés en BDD
	 * 
	 * @return La liste des vacations stockées en BDD
	 * @author Thomas [TH]
	 * @version 1
	 */
	public List<Vehicule> getVehicules();

	/**
	 * Met à jour le véhicule indiqué stockée en BDD
	 * 
	 * @param id L'identifiant du véhicule à mettre à jour
	 * @param colonne La colonne mise à jour
	 * @param valeur La valeur mise à jour
	 * @author Thomas [TH]
	 * @version 1
	 */
	public void updateVehicule(int id, String colonne, String valeur);

	/**
	 * Créer un nouveau véhicule en BDD
	 * 
	 * @author Thomas [TH]
	 * @version 1
	 */
	public void ajouterVehicule();

	/**
	 * Supprime un véhicule en BDD selon son ID
	 * 
	 * @param id L'identifiant du véhicule à supprimer
	 * @author Thomas [TH]
	 * @version 1
	 */
	public void supprimerVehicule(int id);
	
	/* GESTION DES ASSOCIATIONS SERVICES - CONDUCTEURS */
	
	/**
	 * Met à jour l'association service - conducteur indiquée stockée en BDD
	 * 
	 * @param idService L'identifiant du service à mettre à jour
	 * @param date La date concernée
	 * @param colonne La colonne mise à jour
	 * @param valeur La valeur mise à jour
	 * @author Thomas [TH]
	 * @version 1
	 */
	public void updateServiceConducteur(int idService, String date, String colonne, String valeur);
	
	/**
	 * Créer une nouvelle association service - conducteur en BDD
	 * 
	 * @param idService L'identifiant du service associé
	 * @param dateSQL La date du jour concerné
	 * @param idConducteur L'identifiant du conducteur associé
	 * @author Thomas [TH]
	 * @version 1
	 */
	public void ajouterServiceConducteur(int idService, Date dateSQL, int idConducteur);
	
	/**
	 * Supprime une association service - conducteur en BDD selon son ID
	 * 
	 * @param idService L'identifiant du véhicule à supprimer
	 * @param dateSQL La date concernée
	 * @author Thomas [TH]
	 * @version 1
	 */
	public void supprimerServiceConducteur(int idService, Date dateSQL);
	
	/* GESTION DES CONDUCTEURS */

	/**
	 * Récupère les conducteurs stockés en BDD
	 * 
	 * @return La liste des conducteurs stockés en BDD
	 * @author Thomas [TH]
	 * @version 1
	 */
	public List<Conducteur> getConducteurs();

	/**
	 * Met à jour le conducteur indiquée stocké en BDD
	 * 
	 * @param id L'identifiant du conducteur à mettre à jour
	 * @param colonne La colonne mise à jour
	 * @param valeur La valeur mise à jour
	 * @author Thomas [TH]
	 * @version 1
	 */
	public void updateConducteur(int id, String colonne, String valeur);

	/**
	 * Créer un nouveau conducteur en BDD
	 * 
	 * @author Thomas [TH]
	 * @version 1
	 */
	public void ajouterConducteur();

	/**
	 * Supprime un conducteur en BDD selon son ID
	 * 
	 * @param id L'identifiant du conducteur à supprimer
	 * @author Thomas [TH]
	 * @version 1
	 */
	public void supprimerConducteur(int id);
	
	/* GESTION DU DIAGRAMME DE LIGNE */

	/**
	 * Récupère les arrets d'une ligne pour être affichés sur un diagramme de ligne
	 * 
	 * @param idLigne L'identifiant de la ligne
	 * @return La liste des arrets d'une ligne
	 * @author Thomas [TH]
	 * @version 1
	 */
	public List<List<Arret>> getArretsDiagramme(int idLigne);

	/**
	 * Récupère les actions d'une ligne
	 * 
	 * @param idLigne L'identifiant de la ligne
	 * @return La liste des actions d'une ligne
	 * @author Thomas [TH]
	 * @version 1
	 */
	public List<List<Action>> getActionsPourLaLigne(int idLigne);
	
	/**
	 * Récupère les actions d'une voie
	 * 
	 * @param idVoie L'identifiant de la voie
	 * @return La liste des actions d'une voie
	 * @author Thomas [TH]
	 * @version 1
	 */
	public List<Action> getActionsPourLaVoie(int idVoie);
	
}
