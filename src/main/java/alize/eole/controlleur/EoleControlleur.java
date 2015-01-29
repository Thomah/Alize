package alize.eole.controlleur;

import static alize.commun.Constantes.*;
import static alize.eole.constante.Communes.*;
import static alize.eole.constante.Contraintes.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;

import org.jooq.tools.json.JSONArray;
import org.jooq.tools.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import alize.commun.modele.*;
import alize.commun.modele.tables.pojos.Periodicite;
import alize.commun.modele.tables.pojos.Vehicule;
import alize.commun.service.StockageService;

/**
 * Controlleur principal du module Eole
 * 
 * @author Thomas
 * @date 21/11/2014
 *
 */
@Controller
public class EoleControlleur {

	public static final SimpleDateFormat PERIODE_FORMAT = new SimpleDateFormat(
			"hh:mm:ss");
	
	@Autowired
	private StockageService stockageService;

	/**
	 * Affiche la page principale du module Eole
	 * 
	 * @param model
	 *            Les données associées à la page
	 * @return La page associée
	 * @author Thomas
	 * @date 21/11/2014
	 */
	@RequestMapping(value = URL_INDEX, method = GET)
	public ModelAndView afficherDashboard(ModelMap model) {
		ModelAndView view = new ModelAndView(URL_MODULE + SLASH + JSP_INDEX);
		view.addObject(URL_MODULE_CLE, URL_MODULE);
		view.addObject(URL_PAGE_CLE, URL_INDEX);
		return view;
	}

	/**
	 * Affiche la page de définition des contraintes du module Eole
	 * 
	 * @param model
	 *            Les données associées à la page
	 * @return La page associée
	 * @author Thomas
	 * @date 21/11/2014
	 */
	@RequestMapping(value = URL_CONTRAINTES, method = GET)
	public ModelAndView afficherContraintes(ModelMap model) {
		ModelAndView view = new ModelAndView(URL_MODULE + SLASH
				+ JSP_CONTRAINTES);
		view.addObject(URL_MODULE_CLE, URL_MODULE);
		view.addObject(URL_PAGE_CLE, URL_CONTRAINTES);

		Properties prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream(NOM_FICHIER);

			// Chargement du fichier de propriétés
			prop.load(input);

			// Temps de travail max
			String str = prop.getProperty(TEMPS_TRAVAIL_MAX_LABEL);
			if(str == null) {
				str = "00:00";
			}
			view.addObject(TEMPS_TRAVAIL_MAX_LABEL, str);
			
			// Temps de conduite max
			str = prop.getProperty(TEMPS_CONDUITE_MAX_LABEL);
			if(str == null) {
				str = "00:00";
			}
			view.addObject(TEMPS_CONDUITE_MAX_LABEL, str);

			// Temps de pause min
			str = prop.getProperty(TEMPS_PAUSE_MIN_LABEL);
			if(str == null) {
				str = "00:00";
			}
			view.addObject(TEMPS_PAUSE_MIN_LABEL, str);
			
			// Temps de pause max
			str = prop.getProperty(TEMPS_PAUSE_MAX_LABEL);
			if(str == null) {
				str = "00:00";
			}
			view.addObject(TEMPS_PAUSE_MAX_LABEL, str);

			// Temps de début de journée
			str = prop.getProperty(TEMPS_DEBUT_JOURNEE);
			if(str == null) {
				str = "00:00:00";
			}
			view.addObject(TEMPS_DEBUT_JOURNEE, str);

			// Temps de fin de journée
			str = prop.getProperty(TEMPS_FIN_JOURNEE);
			if(str == null) {
				str = "00:00:00";
			}
			view.addObject(TEMPS_FIN_JOURNEE, str);

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		view.addObject("lignes", stockageService.getLignes());

		return view;
	}
	
	/**
	 * Retourne en AJAX la liste des voies associées à la ligne sélectionnée
	 * 
	 * @param idLigne
	 *            L'identifiant de la ligne souhaitée
	 * @return La page associée
	 * @author Thomas
	 * @date 21/11/2014
	 */
	@RequestMapping(value = URL_CONTRAINTES + "/updateContraintes", method = POST)
	public @ResponseBody String updateContraintes(@RequestParam String tempsConduiteMax, @RequestParam String tempsTravailMax, @RequestParam String tempsPauseMin, @RequestParam String tempsPauseMax, @RequestParam String tempsDebutJournee, @RequestParam String tempsFinJournee) {
		
		Properties properties = new Properties();
		properties.setProperty(TEMPS_TRAVAIL_MAX_LABEL, tempsTravailMax);
		properties.setProperty(TEMPS_CONDUITE_MAX_LABEL, tempsConduiteMax);
		properties.setProperty(TEMPS_PAUSE_MIN_LABEL, tempsPauseMin);
		properties.setProperty(TEMPS_PAUSE_MAX_LABEL, tempsPauseMax);
		properties.setProperty(TEMPS_DEBUT_JOURNEE, tempsDebutJournee);
		properties.setProperty(TEMPS_FIN_JOURNEE, tempsFinJournee);

		File file = new File(NOM_FICHIER);
		try {
			FileOutputStream fileOut = new FileOutputStream(file);
			properties.store(fileOut, "Contraintes");
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "ok";
	}

	/**
	 * Retourne en AJAX la liste des voies associées à la ligne sélectionnée
	 * 
	 * @param idLigne
	 *            L'identifiant de la ligne souhaitée
	 * @return La page associée
	 * @author Thomas
	 * @date 21/11/2014
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = URL_CONTRAINTES + "/selectLigne", method = POST)
	public @ResponseBody String getListeVoies(@RequestParam int idLigne) {
		List<Voie> voies = stockageService.getVoiesPourLaLigne(idLigne);
		JSONArray array = new JSONArray();
		for (Voie v : voies) {
			JSONObject object = new JSONObject();
			object.put("'id'", v.getId());
			object.put("'direction'", "'" + v.getDirection() + "'");
			object.put("'idTerminusDepart'", v.getTerminusdepartId());
			object.put("'idTerminusArrivee'", v.getTerminusarriveeId());
			array.add(object);

		}
		String validJSONString = array.toString().replace("'", "\"")
				.replace("=", ":");

		return validJSONString;
	}

	/**
	 * Retourne en AJAX la liste des voies associées à la ligne sélectionnée
	 * @param idVoie L'identifiant de la voie souhaitée
	 * @return La liste des arrets au format JSON
	 * @author Thomas
	 * @date 21 nov. 2014
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = URL_CONTRAINTES + "/selectVoie", method = POST)
	public @ResponseBody String getListeArrets(@RequestParam int idVoie) {
		List<Arret> arrets = stockageService.getArretsPourLaVoie(idVoie);
		JSONArray array = new JSONArray();
		for (Arret a : arrets) {
			JSONObject object = new JSONObject();
			object.put("'id'", a.getId());
			object.put("'nom'", "'" + a.getNom() + "'");
			array.add(object);
		}
		String validJSONString = array.toString().replace("'", "\"")
				.replace("=", ":");
		return validJSONString;
	}

	/**
	 * Retourne en AJAX la liste des périodicités associées à la voie et à
	 * l'arret sélectionnés
	 * 
	 * @param idVoie
	 *            L'identifiant de la voie souhaitée
	 * @param idArret
	 *            L'identifiant de l'arret souhaité
	 * @return La liste des périodicités au format JSON
	 * @author Thomas
	 * @date 21/11/2014
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = URL_CONTRAINTES + "/selectArret", method = POST)
	public @ResponseBody String getListePeriodicites(@RequestParam int idVoie,
			@RequestParam int idArret) {
		List<Periodicite> periodicites = stockageService.getPeriodicites(
				idVoie, idArret);
		JSONArray array = new JSONArray();
		for (Periodicite p : periodicites) {
			JSONObject object = new JSONObject();
			object.put("'id'", p.getId());
			object.put("'debut'", "'" + p.getDebut().toString() + "'");
			object.put("'fin'", "'" + p.getFin().toString() + "'");
			object.put("'periode'", "'" + p.getPeriode().toString() + "'");
			array.add(object);
		}
		String validJSONString = array.toString().replace("'", "\"")
				.replace("=", ":");
		return validJSONString;
	}

	/**
	 * Ajouter en AJAX une périodicité vide
	 * 
	 * @param idVoie
	 *            L'identifiant de la voie souhaitée
	 * @param idArret
	 *            L'identifiant de l'arret souhaité
	 * @return La liste des périodicités au format JSON
	 * @author Thomas
	 * @date 21/11/2014
	 */
	@RequestMapping(value = URL_CONTRAINTES + "/ajouterPeriodicite", method = POST)
	public @ResponseBody String ajouterPeriodicite(@RequestParam int idVoie,
			@RequestParam int idArret) {
		stockageService.ajouterPeriodicite(idVoie, idArret);
		return "ok";
	}

	/**
	 * Ajouter en AJAX une périodicité vide
	 * 
	 * @param id
	 *            L'identifiant de la périodicité à supprimer
	 * @return La liste des périodicités au format JSON
	 * @author Thomas
	 * @date 21/11/2014
	 */
	@RequestMapping(value = URL_CONTRAINTES + "/supprimerPeriodicite", method = POST)
	public @ResponseBody String supprimerPeriodicite(@RequestParam int id) {
		stockageService.supprimerPeriodicite(id);
		return "ok";
	}

	/**
	 * Met à jour en AJAX les périodicités
	 * 
	 * @param id
	 *            L'identifiant de la périodicité
	 * @param newvalue
	 *            La valeur mise à jour
	 * @param colname
	 *            Le nom de la propriété mise à jour
	 * @return La liste des périodicités au format JSON
	 * @author Thomas
	 * @date 21/11/2014
	 */
	@RequestMapping(value = URL_CONTRAINTES + "/updatePeriodicite", method = POST)
	public @ResponseBody String updatePeriodicite(@RequestParam int id,
			@RequestParam String newvalue, @RequestParam String colname) {
		Time valeur;
		try {
			valeur = new Time(PERIODE_FORMAT.parse(newvalue).getTime());
			stockageService.updatePeriodicite(id, colname, valeur);
		} catch (ParseException e) {
		}
		return "ok";
	}

	/* GESTION DES FEUILLES DE SERVICES */

	/**
	 * Affiche la JSP de gestion des feuilles de service
	 * 
	 * @name afficherFDS
	 * @description Affiche la JSP de gestion des feuilles de service
	 * @return La vue de la JSP de gestion des feuilles de service
	 * @author Thomas [TH]
	 * @date 9 jan. 2015
	 * @version 1
	 */
	@RequestMapping(value = URL_FDS, method = GET)
	public ModelAndView afficherFDS() {
		ModelAndView view = new ModelAndView(URL_MODULE + SLASH + JSP_FDS);
		view.addObject(URL_MODULE_CLE, URL_MODULE);
		view.addObject(URL_PAGE_CLE, URL_FDS);
		return view;
	}

	/**
	 * Retourne en AJAX la liste des feuilles de service au format JSON
	 * 
	 * @name getListeFDS
	 * @description Retourne en AJAX la liste des feuilles de service au format JSON
	 * @return La liste des feuilles de service au format JSON
	 * @author Thomas [TH]
	 * @date 9 jan. 2015
	 * @version 1
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = URL_FDS + "/get", method = POST)
	public @ResponseBody String getListeFDS() {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		List<Feuilledeservice> fds = stockageService.getFDS();
		JSONArray array = new JSONArray();
		for (Feuilledeservice f : fds) {
			JSONObject object = new JSONObject();
			object.put("'id'", f.getId());
			object.put("'couleur'", "'" + f.getCouleur() + "'");
			object.put("'debutSaison'", "'" + format.format(f.getDebutsaison()) + "'");
			object.put("'finSaison'", "'" + format.format(f.getFinsaison()) + "'");
			array.add(object);
		}
		String validJSONString = array.toString().replace("'", "\"")
				.replace("=", ":");
		return validJSONString;
	}

	/**
	 * Met à jour en AJAX la feuille de service sélectionnée
	 * 
	 * @name updateFDS
	 * @description Met à jour en AJAX la feuille de service sélectionnée
	 * @param id L'identifiant de la ligne à mettre à jour
	 * @param newvalue La nouvelle valeur saisie
	 * @param colname La colonne mise à jour
	 * @param coltype Le type de la valeur mise à jour
	 * @return "ok" si tout s'est bien passé
	 * @author Thomas [TH]
	 * @date 9 jan. 2015
	 * @version 1
	 */
	@RequestMapping(value = URL_FDS + "/update", method = POST)
	public @ResponseBody String updateFDS(@RequestParam int id,
			@RequestParam String newvalue, @RequestParam String colname) {
		stockageService.updateFDS(id, colname, newvalue);
		return "ok";
	}

	/**
	 * Créer en AJAX une nouvelle feuille de service
	 * 
	 * @name ajouterFDS
	 * @description Créer en AJAX une nouvelle feuille de service
	 * @return "ok" si tout s'est bien passé
	 * @author Thomas [TH]
	 * @date 9 jan. 2015
	 * @version 1
	 */
	@RequestMapping(value = URL_FDS + "/ajouter", method = POST)
	public @ResponseBody String ajouterFDS() {
		stockageService.ajouterFDS();
		return "ok";
	}

	/**
	 * Supprime en AJAX la feuille de service d'identifiant donné en paramètre
	 * 
	 * @name supprimerFDS
	 * @description Supprime en AJAX la feuille de service d'identifiant donné en paramètre
	 * @param id L'identifiant de la feuille de service à supprimer
	 * @return "ok" si tout s'est bien passé
	 * @author Thomas [TH]
	 * @date 9 jan. 2015
	 * @version 1
	 */
	@RequestMapping(value = URL_FDS + "/supprimer", method = POST)
	public @ResponseBody String supprimerFDS(@RequestParam int id) {
		stockageService.supprimerFDS(id);
		return "ok";
	}
	
	/* ATTRIBUTION DES PERIODICITES AUX FEUILLES DE SERVICES */
	
	/**
	 * Affiche la JSP de gestion des attributions fds / periodicites
	 * 
	 * @name afficherFDSPeriodicites
	 * @description Affiche la JSP de gestion des attributions fds / periodicites
	 * @return La vue de la JSP de gestion des attributions fds / periodicites
	 * @author Thomas [TH]
	 * @date 9 jan. 2015
	 * @version 1
	 */
	@RequestMapping(value = URL_FDS_PERIODICITES, method = GET)
	public ModelAndView afficherFDSPeriodicites() {
		ModelAndView view = new ModelAndView(URL_MODULE + SLASH + JSP_FDS_PERIODICITES);
		view.addObject(URL_MODULE_CLE, URL_MODULE);
		view.addObject(URL_PAGE_CLE, URL_FDS_PERIODICITES);
		return view;
	}
	
	/**
	 * Retourne en AJAX la liste des périodicités non attribuées au format JSON
	 * 
	 * @name getListePeriodicitesNonAttribuees
	 * @description Retourne en AJAX la liste des périodicités non attribuées au format JSON
	 * @param idFDS L'identifiant de la feuille de service concernée
	 * @return La liste des périodicités non attribuées au format JSON
	 * @author Thomas [TH]
	 * @date 9 jan. 2015
	 * @version 1
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = URL_FDS_PERIODICITES + "/get/nonattribuees", method = POST)
	public @ResponseBody String getListePeriodicitesNonAttribuees(@RequestParam int idFDS) {
		List<Periodicite> periodicites = stockageService.getPeriodicitesNonAttribuees(idFDS);
		JSONArray array = new JSONArray();
		for (Periodicite p : periodicites) {
			JSONObject object = new JSONObject();
			object.put("'id'",p.getId());
			object.put("'voie'", "'" + p.getIdVoie() + "'");
			object.put("'arret'", "'" + p.getIdArret() + "'");
			object.put("'debut'", "'" + p.getDebut() + "'");
			object.put("'fin'", "'" + p.getFin() + "'");
			object.put("'periode'", "'" + p.getPeriode() + "'");
			array.add(object);
		}
		String validJSONString = array.toString().replace("'", "\"")
				.replace("=", ":");
		return validJSONString;
	}

	/**
	 * Retourne en AJAX la liste des périodicités attribuées au format JSON
	 * 
	 * @name getListeVoiesAttribuees
	 * @description Retourne en AJAX la liste des périodicités attribuées au format JSON
	 * @param idFDS L'identifiant de la feuille de service concernée
	 * @return La liste des périodicités attribuées au format JSON
	 * @author Thomas [TH]
	 * @date 9 jan. 2015
	 * @version 1
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = URL_FDS_PERIODICITES + "/get/attribuees", method = POST)
	public @ResponseBody String getListePeriodicitesAttribuees(@RequestParam int idFDS) {
		List<Periodicite> periodicites = stockageService.getPeriodicitesAttribuees(idFDS);
		JSONArray array = new JSONArray();
		for (Periodicite p : periodicites) {
			JSONObject object = new JSONObject();
			object.put("'id'",p.getId());
			object.put("'voie'", "'" + p.getIdVoie() + "'");
			object.put("'arret'", "'" + p.getIdArret() + "'");
			object.put("'debut'", "'" + p.getDebut() + "'");
			object.put("'fin'", "'" + p.getFin() + "'");
			object.put("'periode'", "'" + p.getPeriode() + "'");
			array.add(object);
		}
		String validJSONString = array.toString().replace("'", "\"")
				.replace("=", ":");
		return validJSONString;
	}

	/**
	 * Créer en AJAX une nouvelle association fds / periodicites
	 * 
	 * @name ajouterLigneVoie
	 * @description Créer en AJAX une nouvelle association fds / periodicites
	 * @param idFDS L'identifiant de la feuille de service concernée
	 * @param idPeriodicite L'identifiant de la périodicité concernée
	 * @return "ok" si tout s'est bien passé
	 * @author Thomas [TH]
	 * @date 9 jan. 2015
	 * @version 1
	 */
	@RequestMapping(value = URL_FDS_PERIODICITES + "/ajouter", method = POST)
	public @ResponseBody String ajouterFDSPeriodicite(@RequestParam int idFDS, @RequestParam int idPeriodicite) {
		stockageService.ajouterFDSPeriodicite(idFDS, idPeriodicite);
		return "ok";
	}

	/**
	 * Supprime en AJAX l'association fds / periodicites donnée en paramètre
	 * 
	 * @name supprimerLigne
	 * @description Supprime en AJAX l'association fds / periodicites donnée en paramètre
	 * @param idFDS L'identifiant de la feuille de service concernée
	 * @param idPeriodicite L'identifiant de la périodicité concernée
	 * @return "ok" si tout s'est bien passé
	 * @author Thomas [TH]
	 * @date 9 jan. 2015
	 * @version 1
	 */
	@RequestMapping(value = URL_FDS_PERIODICITES + "/supprimer", method = POST)
	public @ResponseBody String supprimerFDSPeriodicite(@RequestParam int idFDS, @RequestParam int idPeriodicite) {
		stockageService.supprimerFDSPeriodicite(idFDS, idPeriodicite);
		return "ok";
	}
	
	/* GESTION DES SERVICES */

	/**
	 * Affiche la JSP de gestion des services
	 * 
	 * @name afficherServices
	 * @description Affiche la JSP de gestion des services
	 * @return La vue de la JSP de gestion des services
	 * @author Thomas [TH]
	 * @date 10 jan. 2015
	 * @version 1
	 */
	@RequestMapping(value = URL_SERVICES, method = GET)
	public ModelAndView afficherServices() {
		ModelAndView view = new ModelAndView(URL_MODULE + SLASH + JSP_SERVICES);
		view.addObject(URL_MODULE_CLE, URL_MODULE);
		view.addObject(URL_PAGE_CLE, URL_SERVICES);
		return view;
	}

	/**
	 * Retourne en AJAX la liste des services au format JSON
	 * 
	 * @name getServices
	 * @description Retourne en AJAX la liste des services au format JSON
	 * @return La liste des services au format JSON
	 * @author Thomas [TH]
	 * @date 10 jan. 2015
	 * @version 1
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = URL_SERVICES + "/get", method = POST)
	public @ResponseBody String getServices() {
		List<Service> services = stockageService.getServices();
		JSONArray array = new JSONArray();
		for (Service s : services) {
			JSONObject object = new JSONObject();
			object.put("'id'", s.getId());
			object.put("'feuilledeservice_id'", "'" + s.getFeuilledeserviceId() + "'");
			array.add(object);
		}
		String validJSONString = array.toString().replace("'", "\"")
				.replace("=", ":");
		return validJSONString;
	}

	/**
	 * Met à jour en AJAX le service sélectionné
	 * 
	 * @name updateService
	 * @description Met à jour en AJAX le service sélectionné
	 * @param id L'identifiant du service à mettre à jour
	 * @param newvalue La nouvelle valeur saisie
	 * @param colname La colonne mise à jour
	 * @return "ok" si tout s'est bien passé
	 * @author Thomas [TH]
	 * @date 10 jan. 2015
	 * @version 1
	 */
	@RequestMapping(value = URL_SERVICES + "/update", method = POST)
	public @ResponseBody String updateService(@RequestParam int id,
			@RequestParam String newvalue, @RequestParam String colname) {
		stockageService.updateService(id, colname, newvalue);
		return "ok";
	}

	/**
	 * Créer en AJAX un nouveau service
	 * ajouterService
	 * @name ajouterFDS
	 * @description Créer en AJAX un nouveau service
	 * @return "ok" si tout s'est bien passé
	 * @author Thomas [TH]
	 * @date 10 jan. 2015
	 * @version 1
	 */
	@RequestMapping(value = URL_SERVICES + "/ajouter", method = POST)
	public @ResponseBody String ajouterService() {
		stockageService.ajouterService();
		return "ok";
	}

	/**
	 * Supprime en AJAX le service d'identifiant donné en paramètre
	 * 
	 * @name supprimerService
	 * @description Supprime en AJAX le service d'identifiant donné en paramètre
	 * @param id L'identifiant du service à supprimer
	 * @return "ok" si tout s'est bien passé
	 * @author Thomas [TH]
	 * @date 10 jan. 2015
	 * @version 1
	 */
	@RequestMapping(value = URL_SERVICES + "/supprimer", method = POST)
	public @ResponseBody String supprimerService(@RequestParam int id) {
		stockageService.supprimerService(id);
		return "ok";
	}

	/* GESTION DES VACATIONS */

	/**
	 * Affiche la JSP de gestion des vacations d'un service donné en paramètre
	 * 
	 * @name afficherVacationsService
	 * @description Affiche la JSP de gestion des vacations d'un service donné en paramètre
	 * @return La vue de la JSP de gestion des vacations d'un service donné en paramètre
	 * @author Thomas [TH]
	 * @date 11 jan. 2015
	 * @version 2
	 */
	@RequestMapping(value = URL_VACATIONS + "service", method = GET)
	public ModelAndView afficherVacationsService() {
		ModelAndView view = new ModelAndView(URL_MODULE + SLASH + JSP_VACATIONS_SERVICE);
		view.addObject(URL_MODULE_CLE, URL_MODULE);
		view.addObject(URL_PAGE_CLE, URL_VACATIONS);
		return view;
	}
	
	/**
	 * Affiche la JSP de gestion des vacations d'un véhicule donné en paramètre
	 * 
	 * @name afficherVacationsVehicule
	 * @description Affiche la JSP de gestion des vacations d'un véhicule donné en paramètre
	 * @return La vue de la JSP de gestion des vacations d'un véhicule donné en paramètre
	 * @author Thomas [TH]
	 * @date 12 jan. 2015
	 * @version 1
	 */
	@RequestMapping(value = URL_VACATIONS + "vehicule", method = GET)
	public ModelAndView afficherVacationsVehicule() {
		ModelAndView view = new ModelAndView(URL_MODULE + SLASH + JSP_VACATIONS_VEHICULE);
		view.addObject(URL_MODULE_CLE, URL_MODULE);
		view.addObject(URL_PAGE_CLE, URL_VACATIONS);
		return view;
	}

	/**
	 * Retourne en AJAX la liste des vacations pour le service et le véhicule indiqués au format JSON
	 * 
	 * @name getVacations
	 * @description Retourne en AJAX la liste des vacations pour le service et le véhicule indiqués au format JSON
	 * @param idService L'identifiant du service
	 * @param idService L'identifiant du véhicule
	 * @return La liste des vacations pour le service et le véhicule indiqués au format JSON
	 * @author Thomas [TH]
	 * @date 11 jan. 2015
	 * @version 2
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = URL_VACATIONS + "/get", method = POST)
	public @ResponseBody String getVacations(@RequestParam int idService, @RequestParam int idVehicule) {
		SimpleDateFormat formater = new SimpleDateFormat("hh:mm:ss");
		List<Vacation> vacations = stockageService.getVacations(idService, idVehicule);
		JSONArray array = new JSONArray();
		for (Vacation v : vacations) {
			JSONObject object = new JSONObject();
			object.put("'id'", v.getId());
			object.put("'heureDebut'", "'" + formater.format(v.getHeuredebut()) + "'");
			object.put("'heureFin'", "'" + formater.format(v.getHeurefin()) + "'");
			object.put("'arretEchangeConducteurDebut_id'", "'" + v.getArretechangeconducteurdebutId() + "'");
			object.put("'arretEchangeConducteurFin_id'", "'" + v.getArretechangeconducteurfinId() + "'");
			object.put("'vehicule_id'", "'" + v.getVehiculeId() + "'");
			object.put("'service_id'", "'" + v.getServiceId() + "'");
			array.add(object);
		}
		String validJSONString = array.toString().replace("'", "\"")
				.replace("=", ":");
		return validJSONString;
	}

	/**
	 * Met à jour en AJAX la vacation sélectionnée
	 * 
	 * @name updateVacation
	 * @description Met à jour en AJAX la vacation sélectionnée
	 * @param id L'identifiant de la vacation à mettre à jour
	 * @param newvalue La nouvelle valeur saisie
	 * @param colname La colonne mise à jour
	 * @return "ok" si tout s'est bien passé
	 * @author Thomas [TH]
	 * @date 11 jan. 2015
	 * @version 1
	 */
	@RequestMapping(value = URL_VACATIONS + "/update", method = POST)
	public @ResponseBody String updateVacation(@RequestParam int id,
			@RequestParam String newvalue, @RequestParam String colname) {
		stockageService.updateVacation(id, colname, newvalue);
		return "ok";
	}

	/**
	 * Créer en AJAX une nouvelle vacation pour le service et le véhicule indiqués
	 * @name ajouterVacation
	 * @description Créer en AJAX une nouvelle vacation pour le service et le véhicule indiqués
	 * @param idService L'identifiant du service
	 * @param idVehicule L'identifiant du véhicule
	 * @return "ok" si tout s'est bien passé
	 * @author Thomas [TH]
	 * @date 11 jan. 2015
	 * @version 2
	 */
	@RequestMapping(value = URL_VACATIONS + "/ajouter", method = POST)
	public @ResponseBody String ajouterVacation(@RequestParam int idService, @RequestParam int idVehicule) {
		stockageService.ajouterVacation(idService, idVehicule);
		return "ok";
	}

	/**
	 * Supprime en AJAX le service d'identifiant donné en paramètre
	 * 
	 * @name supprimerVacation
	 * @description Supprime en AJAX la vacation d'identifiant donné en paramètre
	 * @param id L'identifiant de la vacation à supprimer
	 * @return "ok" si tout s'est bien passé
	 * @author Thomas [TH]
	 * @date 11 jan. 2015
	 * @version 1
	 */
	@RequestMapping(value = URL_VACATIONS + "/supprimer", method = POST)
	public @ResponseBody String supprimerVacation(@RequestParam int id) {
		stockageService.supprimerVacation(id);
		return "ok";
	}
	
	/* GESTION DES VEHICULES */
	
	/**
	 * Affiche la JSP de gestion des véhicules
	 * 
	 * @name afficherVehicules
	 * @description Affiche la JSP de gestion des véhicules
	 * @return La vue de la JSP de gestion des véhicules
	 * @author Thomas [TH]
	 * @date 12 jan. 2015
	 * @version 1
	 */
	@RequestMapping(value = URL_VEHICULES, method = GET)
	public ModelAndView afficherVehicules() {
		ModelAndView view = new ModelAndView(URL_MODULE + SLASH + JSP_VEHICULES);
		view.addObject(URL_MODULE_CLE, URL_MODULE);
		view.addObject(URL_PAGE_CLE, URL_VEHICULES);
		return view;
	}
	
	/**
	 * Retourne en AJAX la liste des vehicules au format JSON
	 * 
	 * @name getVacations
	 * @description Retourne en AJAX la liste des vacations au format JSON
	 * @return La liste des vacations au format JSON
	 * @author Thomas [TH]
	 * @date 11 jan. 2015
	 * @version 1
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = URL_VEHICULES + "/get", method = POST)
	public @ResponseBody String getVehicules() {
		List<alize.commun.modele.Vehicule> vehicules = stockageService.getVehicules();
		JSONArray array = new JSONArray();
		for (Vehicule v : vehicules) {
			JSONObject object = new JSONObject();
			object.put("'id'", v.getId());
			object.put("'type'",  "'" + v.getTypevehicule() + "'");
			array.add(object);
		}
		String validJSONString = array.toString().replace("'", "\"")
				.replace("=", ":");
		return validJSONString;
	}
	
	/**
	 * Met à jour en AJAX le véhicule sélectionné
	 * 
	 * @name updateVehicule
	 * @description Met à jour en AJAX le véhicule sélectionné
	 * @param id L'identifiant du véhicule à mettre à jour
	 * @param newvalue La nouvelle valeur saisie
	 * @param colname La colonne mise à jour
	 * @return "ok" si tout s'est bien passé
	 * @author Thomas [TH]
	 * @date 12 jan. 2015
	 * @version 1
	 */
	@RequestMapping(value = URL_VEHICULES + "/update", method = POST)
	public @ResponseBody String updateVehicule(@RequestParam int id,
			@RequestParam String newvalue, @RequestParam String colname) {
		stockageService.updateVehicule(id, colname, newvalue);
		return "ok";
	}

	/**
	 * Créer en AJAX un nouveau véhicule
	 * @name ajouterVehicule
	 * @description Créer en AJAX un nouveau véhicule
	 * @return "ok" si tout s'est bien passé
	 * @author Thomas [TH]
	 * @date 12 jan. 2015
	 * @version 1
	 */
	@RequestMapping(value = URL_VEHICULES + "/ajouter", method = POST)
	public @ResponseBody String ajouterVehicule() {
		stockageService.ajouterVehicule();
		return "ok";
	}

	/**
	 * Supprime en AJAX le véhicule d'identifiant donné en paramètre
	 * 
	 * @name supprimerVehicule
	 * @description Supprime en AJAX le véhicule d'identifiant donné en paramètre
	 * @param id L'identifiant du véhicule à supprimer
	 * @return "ok" si tout s'est bien passé
	 * @author Thomas [TH]
	 * @date 12 jan. 2015
	 * @version 1
	 */
	@RequestMapping(value = URL_VEHICULES + "/supprimer", method = POST)
	public @ResponseBody String supprimerVehicule(@RequestParam int id) {
		stockageService.supprimerVehicule(id);
		return "ok";
	}
	
	/* GENERATION DU DIAGRAMME DE LIGNE */

	/**
	 * Affiche la JSP du diagramme de ligne
	 * 
	 * @name afficherDiagrammeLigne
	 * @description Affiche la JSP du diagramme de ligne
	 * @return La vue de la JSP du diagramme de ligne
	 * @author Thomas [TH]
	 * @date 15 jan. 2015
	 * @version 1
	 */
	@RequestMapping(value = URL_DIAGRAMME_LIGNE, method = GET)
	public ModelAndView afficherDiagrammeLigne() {
		ModelAndView view = new ModelAndView(URL_MODULE + SLASH + JSP_DIAGRAMME_LIGNE);
		view.addObject(URL_MODULE_CLE, URL_MODULE);
		view.addObject(URL_PAGE_CLE, URL_DIAGRAMME_LIGNE);
		view.addObject("lignes", stockageService.getLignes());
		return view;
	}
	
	/**
	 * Retourne en AJAX les données nécessaires pour afficher le diagramme de la ligne sélectionnée
	 * 
	 * @name getData
	 * @description Retourne en AJAX les données nécessaires pour afficher le diagramme de la ligne sélectionnée
	 * @param idLigne L'identifiant de la ligne
	 * @return Les données nécessaires pour afficher le diagramme de la ligne sélectionnée
	 * @author Thomas [TH]
	 * @date 15 jan. 2015
	 * @version 1
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = URL_DIAGRAMME_LIGNE + "/get", method = POST)
	public @ResponseBody String getData(@RequestParam int idLigne) {
		
		List<List<Arret>> arrets = stockageService.getArretsDiagramme(idLigne);
		List<List<Action>> actions = stockageService.getActionsPourLaLigne(idLigne);
		JSONArray array = new JSONArray();
		
		JSONArray arrayActions = new JSONArray();
		for (List<Action> actionsVoie : actions) {
			JSONArray arrayVoie = new JSONArray();
			for (Action a : actionsVoie) {
				JSONObject object = new JSONObject();
				object.put("'id'", a.getId());
				object.put("'voieId'", "'" + a.getVoieId() + "'");
				object.put("'time'", "'" + a.getTime() + "'");
				object.put("'vehiculeId'", "'" + a.getVehiculeId() + "'");
				object.put("'typeAction'", "'" + a.getTypeaction() + "'");
				object.put("'parametre'", "'" + a.getParametre() + "'");
				arrayVoie.add(object);
			}
			arrayActions.add(arrayVoie);
		}
		array.add(arrayActions);
		
		JSONArray arrayArrets = new JSONArray();
		for (List<Arret> arretsVoie : arrets) {
			JSONArray arrayVoie = new JSONArray();
			for (Arret a : arretsVoie) {
				JSONObject object = new JSONObject();
				object.put("'id'", a.getId());
				object.put("'nom'", "'" + a.getNom() + "'");
				arrayVoie.add(object);
			}
			arrayArrets.add(arrayVoie);
		}
		array.add(arrayArrets);

		JSONObject object = new JSONObject();
		Properties prop = new Properties();
		InputStream input;
		try {
			input = new FileInputStream(NOM_FICHIER);
			prop.load(input);
			
			String str = prop.getProperty(TEMPS_DEBUT_JOURNEE);
			if(str == null) {
				str = "00:00:00";
			}
			object.put("'" + TEMPS_DEBUT_JOURNEE + "'", "'" + str + "'");
			
			str = prop.getProperty(TEMPS_FIN_JOURNEE);
			if(str == null) {
				str = "00:00:00";
			}
			object.put("'" + TEMPS_FIN_JOURNEE + "'", "'" + str + "'");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		array.add(object);
		
		String validJSONString = array.toString().replace("'", "\"")
				.replace("=", ":");
		return validJSONString;
	}
	
}
