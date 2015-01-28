package alize.commun.service;

import alize.commun.modele.*;
import alize.commun.modele.tables.pojos.Conducteur;
import alize.commun.modele.tables.pojos.Intervalle;
import alize.commun.modele.tables.pojos.Ligne;
import alize.commun.modele.tables.pojos.Periodicite;
import alize.commun.modele.tables.pojos.Terminus;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Map;

/**
 * Service associé à la gestion du stockage des données
 * 
 * @name StockageService
 * @author Thomas [TH]
 * @date 1 déc. 2014
 * @version 1
 */
public interface StockageService {
	
	/* GESTION DES LIGNES */
	
	/**
	 * Récupère les lignes stockées en BDD
	 * 
	 * @name getLignes
	 * @description Récupère les lignes stockées en BDD
	 * @return List<Ligne> La liste des lignes stockées en BDD
	 * @author Thomas [TH]
	 * @date 1 déc. 2014
	 * @version 1
	 */
	public List<Ligne> getLignes();

	/**
	 * Met à jour la ligne stockée en BDD
	 * 
	 * @name updateLigne
	 * @description Met à jour la ligne stockée en BDD
	 * @param id L'identifiant de la ligne à mettre à jour
	 * @param colname La colonne mise à jour
	 * @param newvalue La valeur mise à jour
	 * @author Thomas [TH]
	 * @date 2 jan. 2015
	 * @version 1
	 */
	public void updateLigne(int id, String colonne, Object valeur);
	
	/**
	 * Créer une nouvelle ligne en BDD
	 * 
	 * @name ajouterLigne
	 * @description Créer une nouvelle ligne en BDD
	 * @author Thomas [TH]
	 * @date 2 jan. 2015
	 * @version 1
	 */
	public void ajouterLigne();
	
	/**
	 * Supprime une ligne en BDD selon son ID
	 * 
	 * @name supprimerLigne
	 * @description Supprime une ligne en BDD selon son ID
	 * @param id L'identifiant de la ligne à supprimer
	 * @author Thomas [TH]
	 * @date 2 jan. 2015
	 * @version 1
	 */
	public void supprimerLigne(int id);

	/* ATTRIBUTION DES VOIES AUX LIGNES */

	/**
	 * Récupère les voies non attribuées à la ligne d'identifiant donné stockées en BDD
	 * 
	 * @name getVoiesNonAttribuees
	 * @description Récupère les voies non attribuées à la ligne d'identifiant donné stockées en BDD
	 * @return La liste des voies non attribuées à la ligne d'identifiant donné
	 * @author Thomas [TH]
	 * @date 4 jan. 2015
	 * @version 1
	 */
	public Map<Voie, String> getVoiesNonAttribuees(int idLigne);

	/**
	 * Récupère les voies attribuées à la ligne d'identifiant donné stockées en BDD
	 * 
	 * @name getVoiesAttribuees
	 * @description Récupère les voies attribuées à la ligne d'identifiant donné stockées en BDD
	 * @return La liste des voies attribuées à la ligne d'identifiant donné
	 * @author Thomas [TH]
	 * @date 4 jan. 2015
	 * @version 1
	 */
	public Map<Voie, String> getVoiesAttribuees(int idLigne);

	/**
	 * Ajoute une association ligne / voie en BDD
	 * 
	 * @name ajouterLigneVoie
	 * @description Ajoute une association ligne / voie en BDD
	 * @param idVoie L'identifiant de la voie
	 * @param idLigne L'identifiant de la ligne
	 * @author Thomas [TH]
	 * @date 4 jan. 2015
	 * @version 1
	 */
	public void ajouterLigneVoie(int idVoie, int idLigne);

	/**
	 * Supprime une association ligne / voie en BDD
	 * 
	 * @name supprimerLigneVoie
	 * @description Supprime une association ligne / voie en BDD
	 * @param idVoie L'identifiant de la voie
	 * @param idLigne L'identifiant de la ligne
	 * @author Thomas [TH]
	 * @date 4 jan. 2015
	 * @version 1
	 */
	public void supprimerLigneVoie(int idVoie, int idLigne);
	
	/* GESTION DES VOIES */
	
	/**
	 * Récupère les voies stockées en BDD
	 * 
	 * @name getVoies
	 * @description Récupère les voies stockées en BDD
	 * @return La liste des voies stockées en BDD
	 * @author Thomas [TH]
	 * @date 1 déc. 2014
	 * @version 1
	 */
	public List<Voie> getVoies();
	
	
	/**
	 * Récupère les voies de la ligne sélectionnée stockées en BDD
	 * 
	 * @name getVoiesPourLaLigne
	 * @description Récupère les voies de la ligne sélectionnée stockées en BDD
	 * @return La liste des voies de la ligne sélectionnée stockées en BDD
	 * @author Thomas [TH]
	 * @date 4 déc. 2014
	 * @version 1
	 */
	public List<Voie> getVoiesPourLaLigne(int idLigne);
	
	/**
	 * Récupère les informations stockées en BDD sur la voie indiquée par son id
	 * 
	 * @name getVoie
	 * @description Récupère les informations stockées en BDD sur la voie indiquée par son id
	 * @param id L'identifiant de la voie
	 * @return La voie indiquée par son id stockées en BDD
	 * @author Thomas [TH]
	 * @date 16 jan. 2014
	 * @version 1
	 */
	public Voie getVoie(int idVoie);
	
	/**
	 * Met à jour la ligne stockée en BDD
	 * 
	 * @name updateVoie
	 * @description Met à jour la voie stockée en BDD
	 * @param id L'identifiant de la voie à mettre à jour
	 * @param colname La colonne mise à jour
	 * @param newvalue La valeur mise à jour
	 * @author Thomas [TH]
	 * @date 2 jan. 2015
	 * @version 1
	 */
	public void updateVoie(int id, String colonne, Object valeur);

	/**
	 * Créer une nouvelle voie en BDD
	 * 
	 * @name ajouterVoie
	 * @description Créer une nouvelle voie en BDD
	 * @author Thomas [TH]
	 * @date 2 jan. 2015
	 * @version 1
	 */
	public void ajouterVoie();
	
	/**
	 * Supprime une voie en BDD selon son ID
	 * 
	 * @name supprimerVoie
	 * @description Supprime une voie en BDD selon son ID
	 * @param id L'identifiant de la voie à supprimer
	 * @author Thomas [TH]
	 * @date 2 jan. 2015
	 * @version 1
	 */
	public void supprimerVoie(int id);

	/* ATTRIBUTION DES TRANSITIONS AUX VOIES */

	/**
	 * Récupère les transitions non attribuées à la voie d'identifiant donné stockées en BDD
	 * 
	 * @name getTransitionsNonAttribues
	 * @description Récupère les transitions non attribuées à la voie d'identifiant donné stockées en BDD
	 * @return La liste des transitions non attribuées à la voie d'identifiant donné
	 * @author Thomas [TH]
	 * @date 5 jan. 2015
	 * @version 2
	 */
	public Map<Transition, String> getTransitionsNonAttribuees(int idVoie);

	/**
	 * Récupère les transitions attribuées à la voie d'identifiant donné stockées en BDD
	 * 
	 * @name getTransitionsAttribuees
	 * @description Récupère les transitions attribuées à la voie d'identifiant donné stockées en BDD
	 * @return La liste des transitions attribuées à la voie d'identifiant donné
	 * @author Thomas [TH]
	 * @date 5 jan. 2015
	 * @version 3
	 */
	public List<Transition> getTransitionsAttribuees(int idVoie);

	/**
	 * Récupère les transitions dont l'arrêt précédent est donné en paramètre.
	 * 
	 * @name getTransitions
	 * @description Récupère les transitions dont l'arrêt précédent est donné en paramètre.
	 * @return La liste des transitions dont l'arrêt précédent est donné en paramètre
	 * @param idArretPrecedent l'ID de l'arrêt précédent
	 * @author Cyril [CS]
	 * @date 21 jan. 2015
	 * @version 1
	 */
	public List<Transition> getTransitions(int idArretPrecedent);
	
	
	/**
	 * Récupère la transition d'ID donné en paramètre.
	 * 
	 * @name getTransition
	 * @description Récupère la transition d'ID donné en paramètre.
	 * @return La transition dont l'ID est donné en paramètre
	 * @param id l'ID de la transition
	 * @author Cyril [CS]
	 * @date 21 jan. 2015
	 * @version 1
	 */
	public Transition getTransition(int id);
	
	
	/**
	 * Ajoute une association voie / transition en BDD
	 * 
	 * @name ajouterVoieTransition
	 * @description Ajoute une association voie / transition en BDD
	 * @param idVoie L'identifiant de la voie
	 * @param idTransition L'identifiant de la transition
	 * @author Thomas [TH]
	 * @date 5 jan. 2015
	 * @version 2
	 */
	public void ajouterVoieTransition(int idVoie, int idTransition);

	/**
	 * Supprime une association voie / transition en BDD
	 * 
	 * @name supprimerVoieTransition
	 * @description Supprimer une association voie / transition en BDD
	 * @param idVoie L'identifiant de la voie
	 * @param idTransition L'identifiant de la transition
	 * @author Thomas [TH]
	 * @date 5 jan. 2015
	 * @version 2
	 */
	public void supprimerVoieTransition(int idVoie, int idTransition);
	
	/* GESTION DES ARRETS */
	
	/**
	 * Met à jour l'arret stocké en BDD
	 * 
	 * @name updateArret
	 * @description  Met à jour l'arret stocké en BDD
	 * @param id L'identifiant de l'arret à mettre à jour
	 * @param colname La colonne mise à jour
	 * @param newvalue La valeur mise à jour
	 * @author Cyril [CS]
	 * @date 08 janv. 2014
	 * @version 1
	 */
	public void updateArret(int id, String colonne, Object valeur);
	
	/**
	 * Récupère les arrets stockées en BDD
	 * 
	 * @name getArrets
	 * @description Récupère les arrets stockées en BDD
	 * @return La liste des arrets stockées en BDD
	 * @author Thomas [TH]
	 * @date 1 déc. 2014
	 * @version 1
	 */
	public List<Arret> getArrets();
	
	/**
	 * Récupère les arrets où les échanges conducteurs sont possibles stockées en BDD
	 * 
	 * @name getArretsEchangesConducteurs
	 * @description Récupère les arrets où les échanges conducteurs sont possibles stockées en BDD
	 * @return La liste des arrets stockées en BDD
	 * @author Thomas [TH]
	 * @date 11 déc. 2014
	 * @version 1
	 */
	public List<Arret> getArretsEchangesConducteurs();

	/**
	 * Récupère les dépôts stockées en BDD
	 * 
	 * @name getDepots
	 * @description Récupère les dépôts stockées en BDD
	 * @return La liste des dépôts stockées en BDD
	 * @author Thomas [TH]
	 * @date 7 jan. 2015
	 * @version 1
	 */
	public List<alize.commun.modele.Depot> getDepots();

	/**
	 * Récupère les terminus stockées en BDD
	 * 
	 * @name getTerminus
	 * @description Récupère les terminus stockées en BDD
	 * @return La liste des terminus stockées en BDD
	 * @author Thomas [TH]
	 * @date 7 jan. 2015
	 * @version 1
	 */
	public List<Terminus> getTerminus();
	
	/**
	 * Récupère le terminus stocké en BDD en fonction de son ID
	 * 
	 * @name getTerminus
	 * @description Récupère le terminus stocké en BDD en fonction de son ID
	 * @return Le terminus stockées en BDD en fonction de son ID
	 * @author Cyril [CS]
	 * @date 9 jan. 2015
	 * @version 1
	 */
	public Terminus getTerminus(int id);
	
	/**
	 * Ajoute un arrêt en BDD
	 * 
	 * @name ajouterArret
	 * @description Ajoute un arrêt en BDD
	 * @author Cyril [CS]
	 * @date 8 jan. 2015
	 * @version 1
	 */
	public void ajouterArret();
	
	/**
	 * Récupère l'arrets stocké en BDD en fonction de son ID
	 * 
	 * @name getArret
	 * @description Récupère l'arrets stocké en BDD en fonction de son ID
	 * @return L'arret stocké en BDD en fonction de son ID
	 * @author Cyril [CS]
	 * @date 09 déc. 2014
	 * @version 1
	 */
	public Arret getArret(int id);
	
	/**
	 * Supprime un arrêt en BDD selon son ID
	 * 
	 * @name supprimerArret
	 * @description Supprime un arrêt en BDD selon son ID
	 * @param id L'identifiant de l'arrêt à supprimer
	 * @author Cyril [CS]
	 * @date 8 jan. 2015
	 * @version 1
	 */
	public void supprimerArret(int id);
	
	/**
	 * Récupère les arrets de la voie sélectionnée stockées en BDD
	 * 
	 * @name getArretsPourLaVoie
	 * @description Récupère les arrets de la voie sélectionnée stockées en BDD
	 * @return List<Arret> La liste des arrets de la voie sélectionnée stockées en BDD
	 * @author Thomas [TH]
	 * @date 4 déc. 2014
	 * @version 1
	 */
	public List<Arret> getArretsPourLaVoie(int idVoie);
	
	/**
	 * Récupère l'identifiant et le nom des terminus pour l'ID de voie donnée
	 * 
	 * @name getTerminusVoie
	 * @description Récupère l'identifiant et le nom des terminus pour l'ID de voie donnée
	 * @return La liste des identifiants et noms des terminus pour l'ID de voie donnée
	 * @author Thomas [TH]
	 * @date 2 jan. 2015
	 * @version 1
	 */
	public Map<Integer, String> getTerminusVoie(int idVoie);

	/* GESTION DES TRANSITIONS */

	/**
	 * Récupère les transitions stockées en BDD
	 * 
	 * @name getTransitions
	 * @description Récupère les transitions stockées en BDD
	 * @return List<Arret> La liste des transitions stockées en BDD
	 * @author Thomas [TH]
	 * @date 3 déc. 2014
	 * @version 1
	 */
	public List<Transition> getTransitions();

	/**
	 * Met à jour la transitions stockée en BDD
	 * 
	 * @name updateTransition
	 * @description Met à jour la transitions stockée en BDD
	 * @param id L'identifiant de la transitions à mettre à jour
	 * @param colname La colonne mise à jour
	 * @param newvalue La valeur mise à jour
	 * @author Thomas [TH]
	 * @date 3 jan. 2015
	 * @version 1
	 */
	public void updateTransition(int id, String colname, String newvalue);

	/**
	 * Créer une nouvelle transition en BDD
	 * 
	 * @name ajouterTransition
	 * @description Créer une nouvelle transition en BDD
	 * @author Thomas [TH]
	 * @date 3 jan. 2015
	 * @version 1
	 */
	public void ajouterTransition();

	/**
	 * Supprime une transition en BDD selon son ID
	 * 
	 * @name supprimerTransition
	 * @description Supprime une transition en BDD selon son ID
	 * @param id L'identifiant de la transition à supprimer
	 * @author Thomas [TH]
	 * @date 3 jan. 2015
	 * @version 1
	 */
	public void supprimerTransition(int id);
	
	/* GESTION DES ZONES DE CROISEMENT */
	
	/**
	 * Récupère les zones de croisement stockées en BDD
	 * 
	 * @name getZonesDeCroisement
	 * @description Récupère les zones de croisement stockées en BDD
	 * @return La liste des zones de croisement stockées en BDD
	 * @author Thomas [TH]
	 * @date 18 jan. 2014
	 * @version 1
	 */
	public List<ZoneDeCroisement> getZonesDeCroisement();

	/**
	 * Met à jour la zone de croisement stockée en BDD
	 * 
	 * @name updateZoneDeCroisement
	 * @description Met à jour la zone de croisement stockée en BDD
	 * @param id L'identifiant de la zone de croisement à mettre à jour
	 * @param colname La colonne mise à jour
	 * @param newvalue La valeur mise à jour
	 * @author Thomas [TH]
	 * @date 18 jan. 2015
	 * @version 1
	 */
	public void updateZoneDeCroisement(int id, String colname, String newvalue);

	/**
	 * Créer une nouvelle zone de croisement en BDD
	 * 
	 * @name ajouterZoneDeCroisement
	 * @description Créer une nouvelle zone de croisement en BDD
	 * @author Thomas [TH]
	 * @date 18 jan. 2015
	 * @version 1
	 */
	public void ajouterZoneDeCroisement();

	/**
	 * Supprime une zone de croisement en BDD selon son ID
	 * 
	 * @name supprimerZoneDeCroisement
	 * @description Supprime une zone de croisement en BDD selon son ID
	 * @param id L'identifiant de la zone de croisement à supprimer
	 * @author Thomas [TH]
	 * @date 18 jan. 2015
	 * @version 1
	 */
	public void supprimerZoneDeCroisement(int id);
	
	/* GESTION DES PERIODICITES */
	
	/**
	 * Récupère les périodicités stockées en BDD
	 * 
	 * @name getPeriodicites
	 * @description Récupère les périodicités stockées en BDD
	 * @return List<Periodicite> La liste des périodicités stockées en BDD
	 * @author Thomas [TH]
	 * @date 1 déc. 2014
	 * @version 1
	 */
	public List<alize.commun.modele.Periodicite> getPeriodicites();

	
	/**
	 * Renvoi la périodicité stockée dans la BDD en fonction de sont ID
	 * 
	 * @name getPeriodicite
	 * @description Renvoi la périodicité stockée dans la BDD en fonction de sont ID
	 * @return Periodicite La périodicité stockée dans la BDD
	 * @author Cyril [CS]
	 * @date 19 jan. 2015
	 * @version 1
	 */
	public alize.commun.modele.Periodicite getPeriodicite(int id);
	
	
	/**
	 * Récupère les périodicités pour la voie et l'arret sélectionnés stockées en BDD
	 * 
	 * @name getPeriodicites
	 * @description Récupère les périodicités pour la voie et l'arret sélectionnés stockées en BDD
	 * @param idVoie L'identifiant de la voie concernée
	 * @param idArret L'identifiant de l'arret concerné
	 * @return List<Periodicite> La liste des périodicités pour la voie et l'arret sélectionnés stockées en BDD
	 * @author Thomas [TH]
	 * @date 17 déc. 2014
	 * @version 1
	 */
	public List<Periodicite> getPeriodicites(int idVoie, int idArret);

	/**
	 * Créer une périodicité en BDD
	 * @name ajouterPeriodicite
	 * @description Créer une périodicité en BDD
	 * @param idVoie L'identifiant de la voie concernée
	 * @param idArret L'identifiant de l'arret concerné
	 * @author Thomas [TH]
	 * @date 18 déc. 2014
	 * @version 1
	 */
	public void ajouterPeriodicite(int idVoie, int idArret);
	
	/**
	 * Supprime la périodicité indiquée stockées en BDD
	 * @name supprimerPeriodicite
	 * @description Supprime la périodicité indiquée stockées en BDD
	 * @param id L'identifiant de la périodicité à supprimer
	 * @author Thomas [TH]
	 * @date 17 déc. 2014
	 * @version 1
	 */
	public void supprimerPeriodicite(int id);
	
	/**
	 * Met à jour la périodicité indiquée stockées en BDD
	 * @name updatePeriodicite
	 * @description Met à jour la périodicité indiquée stockées en BDD
	 * @param id L'identifiant de la périodicité à mettre à jour
	 * @param colname La colonne mise à jour
	 * @param newvalue La valeur mise à jour
	 * @author Thomas [TH]
	 * @date 17 déc. 2014
	 * @version 1
	 */
	public void updatePeriodicite(int id, String colonne, Time valeur);


	/* GESTION DES INTERVALLES */

	/**
	 * Récupère les intervalles stockés en BDD
	 * @name getIntervalles
	 * @description Récupère les intervalles stockés en BDD
	 * @return La liste des intervalles stockés en BDD
	 * @author Thomas [TH]
	 * @date 6 jan. 2015
	 * @version 1
	 */
	public List<alize.commun.modele.Intervalle> getIntervalles();

	/**
	 * Récupère l'intervalle stocké en BDD
	 * @name getIntervalle
	 * @description Récupère l'intervalle stocké en BDD
	 * @return L'intervalle correspondant à l'Id
	 * @author Cyril [CS]
	 * @date 16 jan. 2015
	 * @version 1
	 */
	public alize.commun.modele.Intervalle getIntervalle(int id);
	
	/**
	 * Récupère les intervalles du réseau stockés en BDD
	 * 
	 * @name getTempsImmobilisation
	 * @description Récupère les intervalles du réseau stockés en BDD
	 * @return List<Intervalle> La liste des intervalles stockes en BDD
	 * @author Cyril [CS]
	 * @date 06 jan. 2015
	 * @version 1
	 */
	public List<alize.commun.modele.Intervalle> getTempsImmobilisation();

	/**
	 * Récupère l'intervalle du réseau  stockés en BDD en fonction de son ID
	 * 
	 * @name getTempsImmobilisation
	 * @description Récupère l'intervalle du réseau  stockés en BDD en fonction de son ID
	 * @param id L'identifiant de l'intervalle
	 * @return Intervalle L'intervalle correspondant à l'id passé en paramètre et stockes en BDD
	 * @author Cyril [CS]
	 * @date 06 jan. 2015
	 * @version 1
	 */
	public Intervalle getTempsImmobilisation(int idTempsImmobilisation);

	/**
	 * Met à jour le temps d'immobilisation d'un arrêt
	 * 
	 * @name updateTempsImmobilisation
	 * @description Met à jour le temps d'immobilisation d'un arrêt
	 * @param id L'identifiant de l'arrêt
	 * @param colonne L'attribut du temps d'immobilisation à modifier (MIN, PREF, MAX) 
	 * @param valeur La nouvelle valeur à enregistrer.
	 * @author Cyril [CS]
	 * @date 07 jan. 2015
	 * @version 1
	 */
	public void updateTempsImmobilisationArret(int id, String colonne, Object valeur);
	
	/* GESTION DES TERMINUS */
	
	/**
	 * Renvoie 1 si l'arrêt définit par idArret est un terminus, 0 sinon
	 * 
	 * @name getTerminusIDArret
	 * @description Renvoie 1 si l'arrêt définit par idArret est un terminus, 0 sinon
	 * @param id L'identifiant de l'arrêt
	 * @author Cyril [CS]
	 * @date 08 jan. 2015
	 * @version 1
	 */
	public boolean getEstTerminus(int idArret);

	/**
	 * Créer un terminus en BDD
	 * @name ajouterTerminus
	 * @description Créer un terminus en BDD
	 * @param id L'identifiant du terminus concerné
	 * @param idArret L'identifiant de l'arret concerné
	 * @author Cyril [CS]
	 * @date 08 jan. 2015
	 * @version 1
	 */
	public void ajouterTerminus( int idArret);
	
	/**
	 * Supprime le terminus indiqué par l'ID de son arret  en BDD
	 * @name supprimerTerminus
	 * @description Supprime le terminus indiqué par l'ID de son arret  en BDD
	 * @param id L'identifiant de l'arret associer au terminus à supprimer
	 * @author Cyril [CS]
	 * @date 08 jan. 2015
	 * @version 1
	 */
	void supprimerTerminus(int id);
	
	/* GESTION DES DEPOTS */
	
	/**
	 * Renvoie 1 si l'arrêt définit par idArret est un dépôt, 0 sinon
	 * 
	 * @name getTerminusIDArret
	 * @description Renvoie 1 si l'arrêt définit par idArret est un dépôt, 0 sinon
	 * @param id L'identifiant de l'arrêt
	 * @author Cyril [CS]
	 * @date 08 jan. 2015
	 * @version 1
	 */
	public boolean getEstDepot(int idArret);

	/**
	 * Créer un Dépôt en BDD
	 * @name ajouterDepot
	 * @description Créer un dépôt en BDD
	 * @param id L'identifiant du dépôt concerné
	 * @param idArret L'identifiant de l'arret concerné
	 * @author Cyril [CS]
	 * @date 08 jan. 2015
	 * @version 1
	 */
	void ajouterDepot(int idArret);

	/**
	 * Supprime le dépot indiqué par l'ID de son arret  en BDD
	 * @name supprimerDepot
	 * @description Supprime le dépot indiqué par l'ID de son arret  en BDD
	 * @param id L'identifiant de l'arret associer au dépôt à supprimer
	 * @author Cyril [CS]
	 * @date 08 jan. 2015
	 * @version 1
	 */
	void supprimerDepot(int id);


	/* GESTION DES FEUILLES DE SERVICE */

	/**
	 * Récupère les feuilles de service stockées en BDD
	 * 
	 * @name getFDS
	 * @description Récupère les feuilles de service stockées en BDD
	 * @return La liste des feuilles de service stockées en BDD
	 * @author Thomas [TH]
	 * @date 9 jan. 2015
	 * @version 1
	 */
	public List<Feuilledeservice> getFDS();

	/**
	 * Récupère la feuille de service applicable à la date donnée stockées en BDD
	 * 
	 * @name getFDS
	 * @description Récupère la feuille de service applicable à la date donnée stockées en BDD
	 * @param date La date voulue
	 * @return La feuille de service applicable à la date donnée stockées en BDD
	 * @author Thomas [TH]
	 * @date 26 jan. 2015
	 * @version 1
	 */
	public Feuilledeservice getFDS(Date date);
	
	/**
	 * Met à jour la feuille de service indiquée stockées en BDD
	 * @name updateFDS
	 * @description Met à jour la feuille de service indiquée stockées en BDD
	 * @param id L'identifiant de la feuille de service à mettre à jour
	 * @param colname La colonne mise à jour
	 * @param newvalue La valeur mise à jour
	 * @author Thomas [TH]
	 * @date 9 jan. 2015
	 * @version 1
	 */
	public void updateFDS(int id, String colname, String newvalue);

	/**
	 * Créer une nouvelle feuille de service en BDD
	 * 
	 * @name ajouterFDS
	 * @description Créer une nouvelle feuille de service en BDD
	 * @author Thomas [TH]
	 * @date 9 jan. 2015
	 * @version 1
	 */
	public void ajouterFDS();

	/**
	 * Supprime une feuille de service en BDD selon son ID
	 * 
	 * @name supprimerFDS
	 * @description Supprime une feuille de service en BDD selon son ID
	 * @param id L'identifiant de la feuille de service à supprimer
	 * @author Thomas [TH]
	 * @date 9 jan. 2015
	 * @version 1
	 */
	public void supprimerFDS(int id);

	
	/* GESTION DES LIEUX */
	
	
	/**
	 * Récupère un lieu en fonction de son ID
	 * 
	 * @name getLieu
	 * @description Récupère un lieu en fonction de son ID
	 * @param id L'identifiant du lieu en fonction de l'ID
	 * @author Cyril [CS]
	 * @date 19 jan. 2015
	 * @version 1
	 */
	public Lieu getLieu(int id);
	
	/**
	 * Récupère tous les lieux de la BDD
	 * 
	 * @name getLieux
	 * @description Récupère tous les lieux de la BDD
	 * @author Cyril [CS]
	 * @date 19 jan. 2015
	 * @version 1
	 */
	public List<Lieu> getLieux();
	
	
	/* ATTRIBUTION DES PERIODICITES AUX FEUILLES DE SERVICE */

	/**
	 * Récupère les périodicités non attribuées à la feuille de service d'identifiant donné stockées en BDD
	 * 
	 * @name getPeriodicitesNonAttribuees
	 * @description Récupère les périodicités non attribuées à la feuille de service d'identifiant donné stockées en BDD
	 * @param idFDS L'identifiant de la feuille de service concernée
	 * @return La liste des périodicités non attribuées à la feuille de service d'identifiant donné
	 * @author Thomas [TH]
	 * @date 9 jan. 2015
	 * @version 1
	 */
	public List<Periodicite> getPeriodicitesNonAttribuees(int idFDS);

	/**
	 * Récupère les périodicités attribuées à la feuille de service d'identifiant donné stockées en BDD
	 * 
	 * @name getPeriodicitesAttribuees
	 * @description Récupère les périodicités attribuées à la feuille de service d'identifiant donné stockées en BDD
	 * @param idFDS L'identifiant de la feuille de service concernée
	 * @return La liste des périodicités attribuées à la feuille de service d'identifiant donné
	 * @author Thomas [TH]
	 * @date 9 jan. 2015
	 * @version 1
	 */
	public List<Periodicite> getPeriodicitesAttribuees(int idFDS);

	/**
	 * Ajoute une association fds / périodicité en BDD
	 * 
	 * @name ajouterFDSPeriodicite
	 * @description Ajoute une association fds / périodicité en BDD
	 * @param idFDS L'identifiant de la feuille de service
	 * @param idPeriodicite L'identifiant de la périodicité
	 * @author Thomas [TH]
	 * @date 9 jan. 2015
	 * @version 1
	 */
	public void ajouterFDSPeriodicite(int idFDS, int idPeriodicite);

	/**
	 * Supprime une association fds / périodicité en BDD
	 * 
	 * @name supprimerFDSPeriodicite
	 * @description Supprime une association fds / périodicité en BDD
	 * @param idVoie L'identifiant de la voie
	 * @param idLigne L'identifiant de la ligne
	 * @author Thomas [TH]
	 * @date 9 jan. 2015
	 * @version 1
	 */
	public void supprimerFDSPeriodicite(int idFDS, int idPeriodicite);
	
	/* GESTION DES SERVICES */

	/**
	 * Récupère les services stockés en BDD
	 * 
	 * @name getServices
	 * @description Récupère les services stockés en BDD
	 * @return La liste des services stockés en BDD
	 * @author Thomas [TH]
	 * @date 10 jan. 2015
	 * @version 1
	 */
	public List<Service> getServices();

	/**
	 * Récupère les services correspondant à la feuille de service indiquée stockés en BDD
	 * 
	 * @name getServices
	 * @description Récupère les services correspondant à la feuille de service indiquée stockés en BDD
	 * @return La liste des services correspondant à la feuille de service indiquée stockés en BDD
	 * @author Thomas [TH]
	 * @date 29 jan. 2015
	 * @version 1
	 */
	public List<Service> getServices(int idFDS, Date date);

	/**
	 * Récupère les services tous les services avec les conducteurs associés à la date indiquée stockés en BDD
	 * 
	 * @name getServices
	 * @description Récupère les services tous les services avec les conducteurs associés à la date indiquée stockés en BDD
	 * @param La date souhaitée
	 * @return La liste des services tous les services avec les conducteurs associés à la date indiquée stockés en BDD
	 * @author Thomas [TH]
	 * @date 15 jan. 2015
	 * @version 1
	 */
	public Map<Service, Integer> getServices(String date);
	
	/**
	 * Met à jour le service indiqué stocké en BDD
	 * @name updateService
	 * @description Met à jour le service indiqué stocké en BDD
	 * @param id L'identifiant du service à mettre à jour
	 * @param colname La colonne mise à jour
	 * @param newvalue La valeur mise à jour
	 * @author Thomas [TH]
	 * @date 10 jan. 2015
	 * @version 1
	 */
	public void updateService(int id, String colname, String newvalue);

	/**
	 * Créer un nouveau service en BDD
	 * 
	 * @name ajouterService
	 * @description Créer un nouveau service en BDD
	 * @author Thomas [TH]
	 * @date 10 jan. 2015
	 * @version 1
	 */
	public void ajouterService();

	/**
	 * Supprime un service en BDD selon son ID
	 * 
	 * @name supprimerService
	 * @description Supprime un service en BDD selon son ID
	 * @param id L'identifiant du service à supprimer
	 * @author Thomas [TH]
	 * @date 10 jan. 2015
	 * @version 1
	 */
	public void supprimerService(int id);

	/* GESTION DES VACATIONS */

	/**
	 * Récupère les vacations pour le service et le véhicule indiqués stockées en BDD
	 * 
	 * @name getVacations
	 * @description Récupère les vacations pour le service et le véhicule indiqués stockées en BDD
	 * @param idService L'identifiant du service
	 * @param idVehicule L'identifiant du véhicule
	 * @return La liste des vacations pour le service et le véhicule indiqués stockées en BDD
	 * @author Thomas [TH]
	 * @date 11 jan. 2015
	 * @version 2
	 */
	public List<Vacation> getVacations(int idService, int idVehicule);

	/**
	 * Met à jour la vacation indiquée stockée en BDD
	 * @name updateVacation
	 * @description Met à jour la vacation indiquée stockée en BDD
	 * @param id L'identifiant de la vacation à mettre à jour
	 * @param colname La colonne mise à jour
	 * @param newvalue La valeur mise à jour
	 * @author Thomas [TH]
	 * @date 11 jan. 2015
	 * @version 1
	 */
	public void updateVacation(int id, String colname, String newvalue);

	/**
	 * Créer une nouvelle vacation en BDD
	 * 
	 * @name ajouterVacation
	 * @description Créer une nouvelle vacation en BDD
	 * @param idService L'identifiant du service associé
	 * @param idVehicule L'identifiant du véhicule associé
	 * @author Thomas [TH]
	 * @date 11 jan. 2015
	 * @version 2
	 */
	public void ajouterVacation(int idService, int idVehicule);

	/**
	 * Supprime une vacation en BDD selon son ID
	 * 
	 * @name supprimerVacation
	 * @description Supprime une vacation en BDD selon son ID
	 * @param id L'identifiant de la vacation à supprimer
	 * @author Thomas [TH]
	 * @date 11 jan. 2015
	 * @version 1
	 */
	public void supprimerVacation(int id);
	
	/* GESTION DES VEHICULES */

	/**
	 * Récupère les véhicules stockés en BDD
	 * 
	 * @name getVehicules
	 * @description Récupère les véhicules stockés en BDD
	 * @return La liste des vacations stockées en BDD
	 * @author Thomas [TH]
	 * @date 11 jan. 2015
	 * @version 1
	 */
	public List<alize.commun.modele.Vehicule> getVehicules();

	/**
	 * Met à jour le véhicule indiqué stockée en BDD
	 * @name updateVehicule
	 * @description Met à jour le véhicule indiqué stockée en BDD
	 * @param id L'identifiant du véhicule à mettre à jour
	 * @param colname La colonne mise à jour
	 * @param newvalue La valeur mise à jour
	 * @author Thomas [TH]
	 * @date 12 jan. 2015
	 * @version 1
	 */
	public void updateVehicule(int id, String colname, String newvalue);

	/**
	 * Créer un nouveau véhicule en BDD
	 * 
	 * @name ajouterVehicule
	 * @description Créer un nouveau véhicule en BDD
	 * @author Thomas [TH]
	 * @date 12 jan. 2015
	 * @version 1
	 */
	public void ajouterVehicule();

	/**
	 * Supprime un véhicule en BDD selon son ID
	 * 
	 * @name supprimerVehicule
	 * @description Supprime un véhicule en BDD selon son ID
	 * @param id L'identifiant du véhicule à supprimer
	 * @author Thomas [TH]
	 * @date 12 jan. 2015
	 * @version 1
	 */
	public void supprimerVehicule(int id);
	
	/* GESTION DES ASSOCIATIONS SERVICES - CONDUCTEURS */
	
	/**
	 * Met à jour l'association service - conducteur indiquée stockée en BDD
	 * @name updateVehicule
	 * @description Met à jour l'association service - conducteur indiquée stockée en BDD
	 * @param id L'identifiant du service à mettre à jour
	 * @param date La date concernée
	 * @param colname La colonne mise à jour
	 * @param newvalue La valeur mise à jour
	 * @author Thomas [TH]
	 * @date 12 jan. 2015
	 * @version 1
	 */
	public void updateServiceConducteur(int idService, String date, String colname, String newvalue);
	
	/**
	 * Créer une nouvelle association service - conducteur en BDD
	 * 
	 * @name ajouterServiceConducteur
	 * @description Créer une nouvelle association service - conducteur en BDD
	 * @param idService L'identifiant du service associé
	 * @param dateSQL La date du jour concerné
	 * @param idConducteur L'identifiant du conducteur associé
	 * @author Thomas [TH]
	 * @date 12 jan. 2015
	 * @version 1
	 */
	public void ajouterServiceConducteur(int idService, Date dateSQL, int idConducteur);
	
	/**
	 * Supprime une association service - conducteur en BDD selon son ID
	 * 
	 * @name supprimerVehicule
	 * @description Supprime une association service - conducteur en BDD selon son ID
	 * @param idService L'identifiant du véhicule à supprimer
	 * @param dateSQL La date concernée
	 * @author Thomas [TH]
	 * @date 12 jan. 2015
	 * @version 1
	 */
	public void supprimerServiceConducteur(int idService, Date dateSQL);
	
	/* GESTION DES CONDUCTEURS */

	/**
	 * Récupère les conducteurs stockés en BDD
	 * 
	 * @name getConducteurs
	 * @description Récupère les conducteurs stockés en BDD
	 * @return La liste des conducteurs stockés en BDD
	 * @author Thomas [TH]
	 * @date 12 jan. 2015
	 * @version 1
	 */
	public List<Conducteur> getConducteurs();

	/**
	 * Met à jour le conducteur indiquée stocké en BDD
	 * @name updateVehicule
	 * @description Met à jour le conducteur indiquée stocké en BDD
	 * @param id L'identifiant du conducteur à mettre à jour
	 * @param colname La colonne mise à jour
	 * @param newvalue La valeur mise à jour
	 * @author Thomas [TH]
	 * @date 19 jan. 2015
	 * @version 1
	 */
	public void updateConducteur(int id, String colname, String newvalue);

	/**
	 * Créer un nouveau conducteur en BDD
	 * 
	 * @name ajouterServiceConducteur
	 * @description Créer un nouveau conducteur en BDD
	 * @author Thomas [TH]
	 * @date 19 jan. 2015
	 * @version 1
	 */
	public void ajouterConducteur();

	/**
	 * Supprime un conducteur en BDD selon son ID
	 * 
	 * @name supprimerConducteur
	 * @description Supprime un conducteur en BDD selon son ID
	 * @param id L'identifiant du conducteur à supprimer
	 * @author Thomas [TH]
	 * @date 19 jan. 2015
	 * @version 1
	 */
	public void supprimerConducteur(int id);
	
	/* GESTION DU DIAGRAMME DE LIGNE */

	/**
	 * Récupère les arrets d'une ligne pour être affichés sur un diagramme de ligne
	 * 
	 * @name getArretsDiagramme
	 * @description Récupère les arrets d'une ligne pour être affichés sur un diagramme de ligne
	 * @param idLigne L'identifiant de la ligne
	 * @return La liste des arrets d'une ligne
	 * @author Thomas [TH]
	 * @date 15 jan. 2015
	 * @version 1
	 */
	public List<List<Arret>> getArretsDiagramme(int idLigne);

	/**
	 * Récupère les actions d'une ligne
	 * 
	 * @name getActionsPourLaLigne
	 * @description Récupère les actions d'une ligne
	 * @param idLigne L'identifiant de la ligne
	 * @return La liste des actions d'une ligne
	 * @author Thomas [TH]
	 * @date 19 jan. 2015
	 * @version 1
	 */
	public List<List<Action>> getActionsPourLaLigne(int idLigne);
	
	/**
	 * Récupère les actions d'une voie
	 * 
	 * @name getActionsPourLaLigne
	 * @description Récupère les actions d'une voie
	 * @param idVoie L'identifiant de la voie
	 * @return La liste des actions d'une voie
	 * @author Thomas [TH]
	 * @date 19 jan. 2015
	 * @version 1
	 */
	public List<Action> getActionsPourLaVoie(int idVoie);
	
}
