package alize.orion.controlleur;

import static alize.commun.Constantes.*;
import static alize.nau.commun.Constantes.URL_LIGNES;
import static alize.orion.commun.Constantes.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jooq.tools.json.JSONArray;
import org.jooq.tools.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import alize.commun.modele.tables.pojos.Conducteur;
import alize.commun.modele.tables.pojos.Service;
import alize.commun.modele.tables.pojos.Voie;
import alize.commun.service.StockageService;

/**
 * Controlleur principal du module Eole
 * 
 * @author Thomas
 * @date 21/11/2014
 *
 */
@Controller
public class OrionControlleur {

	@Autowired
	private StockageService stockageService;
	
	/**
	 * Affiche la JSP principale
	 * 
	 * @name afficherDashboard
	 * @description Affiche la JSP principale
	 * @return La vue associée à la JSP principale
	 * @author Thomas [TH]
	 * @date 12 jan. 2015
	 * @version 1
	 */
	@RequestMapping(value = URL_INDEX, method = GET)
	public ModelAndView afficherDashboard() {
		ModelAndView view = new ModelAndView(URL_MODULE + SLASH + JSP_INDEX);
		view.addObject(URL_MODULE_CLE, URL_MODULE);
		view.addObject(URL_PAGE_CLE, URL_INDEX);
		return view;
	}
	
	/* GESTION DES ASSOCIATIONS CONDUCTEURS - SERVICES */
	
	/**
	 * Retourne en AJAX la liste des services au format JSON
	 * 
	 * @name getServices
	 * @description Retourne en AJAX la liste des services au format JSON
	 * @return La liste des services au format JSON
	 * @author Thomas [TH]
	 * @date 12 jan. 2015
	 * @version 1
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = URL_SERVICES + "/get", method = POST)
	public @ResponseBody String getServices(@RequestParam String date) {
		Map<Service, Integer> services = stockageService.getServices(date);
		JSONArray array = new JSONArray();
		for (Entry<Service, Integer> s : services.entrySet()) {
			Service service = s.getKey();
			JSONObject object = new JSONObject();
			object.put("'id'", service.getId());
			object.put("'conducteur'", s.getValue());
			array.add(object);
		}
		String validJSONString = array.toString().replace("'", "\"")
				.replace("=", ":");
		return validJSONString;
	}

	/**
	 * Met à jour en AJAX les associations conducteurs - services sélectionné
	 * 
	 * @name updateService
	 * @description Met à jour en AJAX les associations conducteurs - services sélectionné
	 * @param idService L'identifiant du service à mettre à jour
	 * @param date La date concernée
	 * @param newvalue La nouvelle valeur saisie
	 * @param colname La colonne mise à jour
	 * @return "ok" si tout s'est bien passé
	 * @author Thomas [TH]
	 * @date 12 jan. 2015
	 * @version 1
	 */
	@RequestMapping(value = URL_SERVICES + "/update", method = POST)
	public @ResponseBody String updateService(@RequestParam int idService, @RequestParam String date,
			@RequestParam String newvalue, @RequestParam String colname) {
		stockageService.updateServiceConducteur(idService, date, colname, newvalue);
		return "ok";
	}
	
	/* GESTION DES CONDUCTEURS */
	
	/**
	 * Affiche la JSP de gestion des conducteurs
	 * 
	 * @name afficherLignes
	 * @description Affiche la JSP de gestion des conducteurs
	 * @return La vue de la JSP de gestion des conducteurs
	 * @author Thomas [TH]
	 * @date 19 jan. 2015
	 * @version 1
	 */
	@RequestMapping(value = URL_CONDUCTEURS, method = GET)
	public ModelAndView afficherConducteurs() {
		ModelAndView view = new ModelAndView(URL_MODULE + SLASH + JSP_CONDUCTEURS);
		view.addObject(URL_MODULE_CLE, URL_MODULE);
		view.addObject(URL_PAGE_CLE, URL_CONDUCTEURS);
		return view;
	}
	
	/**
	 * Retourne en AJAX la liste des conducteurs au format JSON
	 * 
	 * @name getConducteurs
	 * @description Retourne en AJAX la liste des conducteurs au format JSON
	 * @return La liste des conducteurs au format JSON
	 * @author Thomas [TH]
	 * @date 12 jan. 2015
	 * @version 1
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = URL_CONDUCTEURS + "/get", method = POST)
	public @ResponseBody String getConducteurs() {
		List<Conducteur> conducteurs = stockageService.getConducteurs();
		JSONArray array = new JSONArray();
		for (Conducteur c : conducteurs) {
			JSONObject object = new JSONObject();
			object.put("'id'", c.getId());
			object.put("'nom'", "'" + c.getNom() + "'");
			object.put("'telephone'", "'" + c.getTelephone() + "'");
			array.add(object);
		}
		String validJSONString = array.toString().replace("'", "\"")
				.replace("=", ":");
		return validJSONString;
	}
	
	/**
	 * Met à jour en AJAX le conducteur sélectionné
	 * 
	 * @name updateConducteur
	 * @description Met à jour en AJAX le conducteur sélectionné
	 * @param id L'identifiant du conducteur à mettre à jour
	 * @param newvalue La nouvelle valeur saisie
	 * @param colname La colonne mise à jour
	 * @return "ok" si tout s'est bien passé
	 * @author Thomas [TH]
	 * @date 2 jan. 2015
	 * @version 1
	 */
	@RequestMapping(value = URL_CONDUCTEURS + "/update", method = POST)
	public @ResponseBody String updateConducteur(@RequestParam int id, @RequestParam String newvalue, @RequestParam String colname) {
		stockageService.updateConducteur(id, colname, newvalue);
		return "ok";
	}

	/**
	 * Créer en AJAX un nouveau conducteur
	 * 
	 * @name ajouterConducteur
	 * @description Créer en AJAX un nouveau conducteur
	 * @return "ok" si tout s'est bien passé
	 * @author Thomas [TH]
	 * @date 19 jan. 2015
	 * @version 1
	 */
	@RequestMapping(value = URL_CONDUCTEURS + "/ajouter", method = POST)
	public @ResponseBody String ajouterConducteur() {
		stockageService.ajouterConducteur();
		return "ok";
	}

	/**
	 * Supprime en AJAX le conducteur d'identifiant donné en paramètre
	 * 
	 * @name supprimerConducteur
	 * @description Supprime en AJAX le conducteur d'identifiant donné en paramètre
	 * @param id L'identifiant du conducteur à supprimer
	 * @return "ok" si tout s'est bien passé
	 * @author Thomas [TH]
	 * @date 18 jan. 2015
	 * @version 1
	 */
	@RequestMapping(value = URL_CONDUCTEURS + "/supprimer", method = POST)
	public @ResponseBody String supprimerConducteur(@RequestParam int id) {
		stockageService.supprimerConducteur(id);
		return "ok";
	}

}
