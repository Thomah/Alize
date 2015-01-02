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
	/* GESTION DES ARRETS */
	
	/**
	 * Récupère les arrets stockées en BDD
	 * 
	 * @name getArrets
	 * @description Récupère les arrets stockées en BDD
	 * @return List<Arret> La liste des arrets stockées en BDD
	 * @author Thomas [TH]
	 * @date 1 déc. 2014
	 * @version 1
	 */
	public List<Arret> getArrets();
	
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
	


}
