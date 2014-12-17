package alize.commun.service;

import alize.commun.modele.tables.pojos.*;
import alize.commun.modele.tables.records.PeriodiciteRecord;

import java.sql.Time;
import java.util.List;

import org.jooq.TableField;

/**
 * Service associé à la gestion du stockage des données
 * 
 * @name StockageService
 * @author Thomas [TH]
 * @date 1 déc. 2014
 * @version 1
 */
public interface StockageService {

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
	 * @return List<Arret> La liste des périodicités pour la voie et l'arret sélectionnés stockées en BDD
	 * @author Thomas [TH]
	 * @date 17 déc. 2014
	 * @version 1
	 */
	public List<Periodicite> getPeriodicites(int idVoie, int idArret);

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
