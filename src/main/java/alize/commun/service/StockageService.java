package alize.commun.service;

import alize.commun.modele.tables.pojos.*;

import java.util.List;

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
	

}
