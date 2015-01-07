package alize.commun.service;

import alize.commun.modele.tables.pojos.*;

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
	 * @return List<Voie> La liste des voies stockées en BDD
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
	 * @return List<Voie> La liste des voies de la ligne sélectionnée stockées en BDD
	 * @author Thomas [TH]
	 * @date 4 déc. 2014
	 * @version 1
	 */
	public List<Voie> getVoiesPourLaLigne(int idLigne);

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

	/* ATTRIBUTION DES ARRETS AUX VOIES */

	/**
	 * Récupère les arrets non attribués à la voie d'identifiant donné stockées en BDD
	 * 
	 * @name getArretsNonAttribues
	 * @description Récupère les arrets non attribués à la voie d'identifiant donné stockées en BDD
	 * @return La liste des arrets non attribués à la voie d'identifiant donné
	 * @author Thomas [TH]
	 * @date 5 jan. 2015
	 * @version 1
	 */
	public Map<Arret, String> getArretsNonAttribues(int idVoie);

	/**
	 * Récupère les arrets attribués à la voie d'identifiant donné stockées en BDD
	 * 
	 * @name getArretsNonAttribues
	 * @description Récupère les arrets attribués à la voie d'identifiant donné stockées en BDD
	 * @return La liste des arrets attribués à la voie d'identifiant donné
	 * @author Thomas [TH]
	 * @date 5 jan. 2015
	 * @version 1
	 */
	public Map<Arret, String> getArretsAttribues(int idVoie);

	/**
	 * Ajoute une association voies / arrets en BDD
	 * 
	 * @name ajouterLigneVoie
	 * @description Ajoute une association voies / arrets en BDD
	 * @param idVoie L'identifiant de la voie
	 * @param idArret L'identifiant de l'arrêt
	 * @author Thomas [TH]
	 * @date 5 jan. 2015
	 * @version 1
	 */
	public void ajouterVoieArret(int idVoie, int idArret);

	/**
	 * Supprime une association voies / arrets en BDD
	 * 
	 * @name supprimerVoieArret
	 * @description Supprimer une association voies / arrets en BDD
	 * @param idVoie L'identifiant de la voie
	 * @param idArret L'identifiant de l'arrêt
	 * @author Thomas [TH]
	 * @date 5 jan. 2015
	 * @version 1
	 */
	public void supprimerVoieArret(int idVoie, int idArret);
	
	/* GESTION DES ARRETS */
	
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
	 * Récupère les dépôts stockées en BDD
	 * 
	 * @name getDepots
	 * @description Récupère les dépôts stockées en BDD
	 * @return La liste des dépôts stockées en BDD
	 * @author Thomas [TH]
	 * @date 7 jan. 2015
	 * @version 1
	 */
	public List<Depot> getDepots();

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
	public List<Periodicite> getPeriodicites();

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

	/* INTERVALLES */

	/**
	 * Récupère les intervalles stockés en BDD
	 * @name getIntervalles
	 * @description Récupère les intervalles stockés en BDD
	 * @return La liste des intervalles stockés en BDD
	 * @author Thomas [TH]
	 * @date 6 jan. 2015
	 * @version 1
	 */
	public List<Intervalle> getIntervalles();

}
