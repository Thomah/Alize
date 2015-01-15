package alize.nau.controlleur;

import static alize.commun.Constantes.*;
import static alize.nau.commun.Constantes.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;
import alize.commun.modele.tables.pojos.Arret;
import alize.commun.modele.tables.pojos.Intervalle;
import alize.commun.modele.tables.pojos.Ligne;
import alize.commun.modele.tables.pojos.Transition;
import alize.commun.modele.tables.pojos.Voie;
import alize.nau.service.DOMService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import alize.commun.service.StockageService;

import org.jooq.DSLContext;
import org.jooq.tools.json.JSONArray;
import org.jooq.tools.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NauControlleur {

	/**
	 * Size of a byte buffer to read/write file
	 */
	private static final int BUFFER_SIZE = 4096;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private DOMService domService;

	@Autowired
	private DSLContext dsl;

	@Autowired
	private StockageService stockageService;

	@RequestMapping(value = URL_INDEX, method = GET)
	public ModelAndView afficherDashboard() {
		ModelAndView view = new ModelAndView(URL_MODULE + SLASH + JSP_INDEX);
		view.addObject(URL_MODULE_CLE, URL_MODULE);
		view.addObject(URL_PAGE_CLE, URL_INDEX);
		return view;
	}

	/* GESTION DES LIGNES */

	/**
	 * Affiche la JSP de gestion des lignes
	 * 
	 * @name afficherLignes
	 * @description Affiche la JSP de gestion des lignes
	 * @return La vue de la JSP de gestion des lignes
	 * @author Thomas [TH]
	 * @date 2 jan. 2015
	 * @version 1
	 */
	@RequestMapping(value = URL_LIGNES, method = GET)
	public ModelAndView afficherLignes() {
		ModelAndView view = new ModelAndView(URL_MODULE + SLASH + JSP_LIGNES);
		view.addObject(URL_MODULE_CLE, URL_MODULE);
		view.addObject(URL_PAGE_CLE, URL_LIGNES);
		return view;
	}

	/**
	 * Retourne en AJAX la liste des lignes au format JSON
	 * 
	 * @name getListeLignes
	 * @description Retourne en AJAX la liste des lignes au format JSON
	 * @return La liste des lignes au format JSON
	 * @author Thomas [TH]
	 * @date 2 jan. 2015
	 * @version 1
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = URL_LIGNES + "/get", method = POST)
	public @ResponseBody String getListeLignes() {
		List<Ligne> lignes = stockageService.getLignes();
		JSONArray array = new JSONArray();
		for (Ligne l : lignes) {
			JSONObject object = new JSONObject();
			object.put("'id'", l.getId());
			object.put("'typeVehicule'", "'" + l.getTypevehicule() + "'");
			array.add(object);
		}
		String validJSONString = array.toString().replace("'", "\"")
				.replace("=", ":");
		return validJSONString;
	}

	/**
	 * Met à jour en AJAX la voie sélectionnée
	 * 
	 * @name updateLigne
	 * @description Met à jour en AJAX la voie sélectionnée
	 * @param id
	 *            L'identifiant de la ligne à mettre à jour
	 * @param newvalue
	 *            La nouvelle valeur saisie
	 * @param colname
	 *            La colonne mise à jour
	 * @param coltype
	 *            Le type de la valeur mise à jour
	 * @return "ok" si tout s'est bien passé
	 * @author Thomas [TH]
	 * @date 2 jan. 2015
	 * @version 1
	 */
	@RequestMapping(value = URL_LIGNES + "/update", method = POST)
	public @ResponseBody String updateLigne(@RequestParam int id,
			@RequestParam String newvalue, @RequestParam String colname,
			@RequestParam String coltype) {
		stockageService.updateLigne(id, colname, newvalue);
		return "ok";
	}

	/**
	 * Créer en AJAX une nouvelle ligne
	 * 
	 * @name ajouterLigne
	 * @description Créer en AJAX une nouvelle ligne
	 * @return "ok" si tout s'est bien passé
	 * @author Thomas [TH]
	 * @date 2 jan. 2015
	 * @version 1
	 */
	@RequestMapping(value = URL_LIGNES + "/ajouter", method = POST)
	public @ResponseBody String ajouterLigne() {
		stockageService.ajouterLigne();
		return "ok";
	}

	/**
	 * Supprime en AJAX la ligne d'identifiant donné en paramètre
	 * 
	 * @name supprimerLigne
	 * @description Supprime en AJAX la ligne d'identifiant donné en paramètre
	 * @param id
	 *            L'identifiant de la ligne à supprimer
	 * @return "ok" si tout s'est bien passé
	 * @author Thomas [TH]
	 * @date 2 jan. 2015
	 * @version 1
	 */
	@RequestMapping(value = URL_LIGNES + "/supprimer", method = POST)
	public @ResponseBody String supprimerLigne(@RequestParam int id) {
		stockageService.supprimerLigne(id);
		return "ok";
	}

	/* ATTRIBUTION DES VOIES AUX LIGNES */

	/**
	 * Affiche la JSP de gestion des attributions lignes / voies
	 * 
	 * @name afficherLignesVoies
	 * @description Affiche la JSP de gestion des attributions lignes / voies
	 * @return La vue de la JSP de gestion des attributions lignes / voies
	 * @author Thomas [TH]
	 * @date 4 jan. 2015
	 * @version 1
	 */
	@RequestMapping(value = URL_LIGNES_VOIES, method = GET)
	public ModelAndView afficherLignesVoies() {
		ModelAndView view = new ModelAndView(URL_MODULE + SLASH
				+ JSP_LIGNES_VOIES);
		view.addObject(URL_MODULE_CLE, URL_MODULE);
		view.addObject(URL_PAGE_CLE, URL_LIGNES_VOIES);
		return view;
	}

	/**
	 * Retourne en AJAX la liste des voies non attribuées au format JSON
	 * 
	 * @name getListeVoiesNonAttribuees
	 * @description Retourne en AJAX la liste des voies non attribuées au format
	 *              JSON
	 * @param idLigne
	 *            L'identifiant de la ligne concernée
	 * @return La liste des voies non attribuées au format JSON
	 * @author Thomas [TH]
	 * @date 4 jan. 2015
	 * @version 1
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = URL_LIGNES_VOIES + "/get/nonattribuees", method = POST)
	public @ResponseBody String getListeVoiesNonAttribuees(
			@RequestParam int idLigne) {
		Map<Voie, String> voies = stockageService
				.getVoiesNonAttribuees(idLigne);
		JSONArray array = new JSONArray();
		for (Entry<Voie, String> t : voies.entrySet()) {
			JSONObject object = new JSONObject();
			Voie v = t.getKey();
			object.put("'id'", v.getId());
			object.put("'direction'", "'" + v.getDirection() + "'");
			object.put("'terminus'", "'" + t.getValue() + "'");
			array.add(object);
		}
		String validJSONString = array.toString().replace("'", "\"")
				.replace("=", ":");
		return validJSONString;
	}

	/**
	 * Retourne en AJAX la liste des voies attribuées au format JSON
	 * 
	 * @name getListeVoiesAttribuees
	 * @description Retourne en AJAX la liste des voies attribuées au format
	 *              JSON
	 * @param idLigne
	 *            L'identifiant de la ligne concernée
	 * @return La liste des voies attribuées au format JSON
	 * @author Thomas [TH]
	 * @date 4 jan. 2015
	 * @version 1
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = URL_LIGNES_VOIES + "/get/attribuees", method = POST)
	public @ResponseBody String getListeVoiesAttribuees(
			@RequestParam int idLigne) {
		Map<Voie, String> voies = stockageService.getVoiesAttribuees(idLigne);
		JSONArray array = new JSONArray();
		for (Entry<Voie, String> t : voies.entrySet()) {
			JSONObject object = new JSONObject();
			Voie v = t.getKey();
			object.put("'id'", v.getId());
			object.put("'direction'", "'" + v.getDirection() + "'");
			object.put("'terminus'", "'" + t.getValue() + "'");
			array.add(object);
		}
		String validJSONString = array.toString().replace("'", "\"")
				.replace("=", ":");
		return validJSONString;
	}

	/**
	 * Créer en AJAX une nouvelle association ligne / voie
	 * 
	 * @name ajouterLigneVoie
	 * @description Créer en AJAX une nouvelle association ligne / voie
	 * @param id
	 *            L'identifiant de la voie concernée
	 * @param idLigne
	 *            L'identifiant de la ligne concernée
	 * @return "ok" si tout s'est bien passé
	 * @author Thomas [TH]
	 * @date 4 jan. 2015
	 * @version 1
	 */
	@RequestMapping(value = URL_LIGNES_VOIES + "/ajouter", method = POST)
	public @ResponseBody String ajouterLigneVoie(@RequestParam int id,
			@RequestParam int idLigne) {
		stockageService.ajouterLigneVoie(id, idLigne);
		return "ok";
	}

	/**
	 * Supprime en AJAX l'association ligne / voie donnée en paramètre
	 * 
	 * @name supprimerLigne
	 * @description Supprime en AJAX l'association ligne / voie donnée en
	 *              paramètre
	 * @param id
	 *            L'identifiant de la voie concernée
	 * @param idLigne
	 *            L'identifiant de la ligne concernée
	 * @return "ok" si tout s'est bien passé
	 * @author Thomas [TH]
	 * @date 4 jan. 2015
	 * @version 1
	 */
	@RequestMapping(value = URL_LIGNES_VOIES + "/supprimer", method = POST)
	public @ResponseBody String supprimerLigneVoie(@RequestParam int id,
			@RequestParam int idLigne) {
		stockageService.supprimerLigneVoie(id, idLigne);
		return "ok";
	}

	/* GESTION DES VOIES */

	/**
	 * Affiche la JSP de gestion des voies
	 * 
	 * @name afficherLignes
	 * @description Affiche la JSP de gestion des voies
	 * @return La vue de la JSP de gestion des voies
	 * @author Thomas [TH]
	 * @date 2 jan. 2015
	 * @version 1
	 */
	@RequestMapping(value = URL_VOIES, method = GET)
	public ModelAndView afficherVoies() {
		ModelAndView view = new ModelAndView(URL_MODULE + SLASH + JSP_VOIES);
		view.addObject(URL_MODULE_CLE, URL_MODULE);
		view.addObject(URL_PAGE_CLE, URL_VOIES);
		return view;
	}

	/**
	 * Retourne en AJAX la liste des voies au format JSON
	 * 
	 * @name getListeVoies
	 * @description Retourne en AJAX la liste des voies au format JSON
	 * @return La liste des voies au format JSON
	 * @author Thomas [TH]
	 * @date 2 jan. 2015
	 * @version 1
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = URL_VOIES + "/get", method = POST)
	public @ResponseBody String getListeVoies() {
		List<Voie> voies = stockageService.getVoies();
		JSONArray array = new JSONArray();
		for (Voie v : voies) {
			JSONObject object = new JSONObject();
			object.put("'id'", v.getId());
			object.put("'direction'", "'" + v.getDirection() + "'");
			object.put("'terminusDepart_id'", "'" + v.getTerminusdepartId()
					+ "'");
			object.put("'terminusArrivee_id'", "'" + v.getTerminusarriveeId()
					+ "'");
			array.add(object);
		}
		String validJSONString = array.toString().replace("'", "\"")
				.replace("=", ":");
		return validJSONString;
	}

	/**
	 * Retourne en AJAX la liste des terminus parmi les arrêts de la voie au
	 * format JSON
	 * 
	 * @name getListeTerminusVoie
	 * @description Retourne en AJAX la liste des terminus parmi les arrêts de
	 *              la voie au format JSON
	 * @param idVoie
	 *            La voie sélectionnée
	 * @return La liste des terminus parmi les arrêts de la voie au format JSON
	 * @author Thomas [TH]
	 * @date 2 jan. 2015
	 * @version 1
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = URL_VOIES + "/getTerminus", method = POST)
	public @ResponseBody String getListeTerminusVoie(@RequestParam int idVoie) {
		Map<Integer, String> terminus = stockageService.getTerminusVoie(idVoie);
		JSONArray array = new JSONArray();
		for (Entry<Integer, String> t : terminus.entrySet()) {
			JSONObject object = new JSONObject();
			object.put("'id'", t.getKey());
			object.put("'nom'", "'" + t.getValue() + "'");
			array.add(object);
		}
		String validJSONString = array.toString().replace("'", "\"")
				.replace("=", ":");
		return validJSONString;
	}

	/**
	 * Met à jour en AJAX la voie sélectionnée
	 * 
	 * @name updateVoie
	 * @description Met à jour en AJAX la voie sélectionnée
	 * @param id
	 *            L'identifiant de la voie à mettre à jour
	 * @param newvalue
	 *            La nouvelle valeur saisie
	 * @param colname
	 *            La colonne mise à jour
	 * @return "ok" si tout s'est bien passé
	 * @author Thomas [TH]
	 * @date 2 jan. 2015
	 * @version 1
	 */
	@RequestMapping(value = URL_VOIES + "/update", method = POST)
	public @ResponseBody String updateVoie(@RequestParam int id,
			@RequestParam String newvalue, @RequestParam String colname) {
		stockageService.updateVoie(id, colname, newvalue);
		return "ok";
	}

	/**
	 * Créer en AJAX une nouvelle voie
	 * 
	 * @name ajouterVoie
	 * @description Créer en AJAX une nouvelle voie
	 * @return "ok" si tout s'est bien passé
	 * @author Thomas [TH]
	 * @date 2 jan. 2015
	 * @version 1
	 */
	@RequestMapping(value = URL_VOIES + "/ajouter", method = POST)
	public @ResponseBody String ajouterVoie() {
		stockageService.ajouterVoie();
		return "ok";
	}

	/**
	 * Supprime en AJAX la voie d'identifiant donné en paramètre
	 * 
	 * @name supprimerVoie
	 * @description Supprime en AJAX la voie d'identifiant donné en paramètre
	 * @param id
	 *            L'identifiant de la voie à supprimer
	 * @return "ok" si tout s'est bien passé
	 * @author Thomas [TH]
	 * @date 2 jan. 2015
	 * @version 1
	 */
	@RequestMapping(value = URL_VOIES + "/supprimer", method = POST)
	public @ResponseBody String supprimerVoie(@RequestParam int id) {
		stockageService.supprimerVoie(id);
		return "ok";
	}

	/* ATTRIBUTION DES ARRETS AUX VOIES */

	/**
	 * Affiche la JSP de gestion des attributions voies / arrets
	 * 
	 * @name afficherVoiesArrets
	 * @description Affiche la JSP de gestion des attributions voies / arrets
	 * @return La vue de la JSP de gestion des attributions voies / arrets
	 * @author Thomas [TH]
	 * @date 5 jan. 2015
	 * @version 1
	 */
	@RequestMapping(value = URL_VOIES_ARRETS, method = GET)
	public ModelAndView afficherVoiesArrets() {
		ModelAndView view = new ModelAndView(URL_MODULE + SLASH
				+ JSP_VOIES_ARRETS);
		view.addObject(URL_MODULE_CLE, URL_MODULE);
		view.addObject(URL_PAGE_CLE, URL_VOIES_ARRETS);
		return view;
	}

	/**
	 * Retourne en AJAX la liste des arrets non attribués au format JSON
	 * 
	 * @name getListeArretsNonAttribues
	 * @description Retourne en AJAX la liste des arrets non attribués au format
	 *              JSON
	 * @param idVoie
	 *            L'identifiant de la voie concernée
	 * @return La liste des voies non attribuées au format JSON
	 * @author Thomas [TH]
	 * @date 5 jan. 2015
	 * @version 1
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = URL_VOIES_ARRETS + "/get/nonattribues", method = POST)
	public @ResponseBody String getListeArretsNonAttribues(
			@RequestParam int idVoie) {
		Map<Arret, String> arrets = stockageService
				.getArretsNonAttribues(idVoie);
		Arret a;
		JSONArray array = new JSONArray();

		for (Entry<Arret, String> arret : arrets.entrySet()) {
			JSONObject object = new JSONObject();
			a = arret.getKey();
			object.put("'id'", a.getId());
			object.put("'statut'", "'" + arret.getValue() + "'");
			object.put("'nom'", "'" + a.getNom() + "'");
			object.put("'estCommercial'", "'" + a.getEstcommercial() + "'");
			object.put("'estEntreeDepot'", "'" + a.getEstentreedepot() + "'");
			object.put("'estSortieDepot'", "'" + a.getEstsortiedepot() + "'");
			object.put("'estLieuEchangeConducteur'",
					"'" + a.getEstsortiedepot() + "'");
			array.add(object);
		}
		String validJSONString = array.toString().replace("'", "\"")
				.replace("=", ":");
		return validJSONString;
	}

	/**
	 * Retourne en AJAX la liste des arrets attribuées au format JSON
	 * 
	 * @name getListeArretsAttribues
	 * @description Retourne en AJAX la liste des arrets attribués au format
	 *              JSON
	 * @param idVoie
	 *            L'identifiant de la voie concernée
	 * @return La liste des voies attribuées au format JSON
	 * @author Thomas [TH]
	 * @date 5 jan. 2015
	 * @version 1
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = URL_VOIES_ARRETS + "/get/attribues", method = POST)
	public @ResponseBody String getListeArretsAttribues(@RequestParam int idVoie) {
		Map<Arret, String> arrets = stockageService.getArretsAttribues(idVoie);
		Arret a;
		JSONArray array = new JSONArray();

		for (Entry<Arret, String> arret : arrets.entrySet()) {
			JSONObject object = new JSONObject();
			a = arret.getKey();
			object.put("'id'", a.getId());
			object.put("'statut'", "'" + arret.getValue() + "'");
			object.put("'nom'", "'" + a.getNom() + "'");
			object.put("'estCommercial'", "'" + a.getEstcommercial() + "'");
			object.put("'estEntreeDepot'", "'" + a.getEstentreedepot() + "'");
			object.put("'estSortieDepot'", "'" + a.getEstsortiedepot() + "'");
			object.put("'estLieuEchangeConducteur'",
					"'" + a.getEstsortiedepot() + "'");
			array.add(object);
		}
		String validJSONString = array.toString().replace("'", "\"")
				.replace("=", ":");
		return validJSONString;
	}

	/**
	 * Créer en AJAX une nouvelle association voie / arret
	 * 
	 * @name ajouterVoieArret
	 * @description Créer en AJAX une nouvelle association voie / arret
	 * @param idVoie
	 *            L'identifiant de la voie concernée
	 * @param idArret
	 *            L'identifiant de l'arrêt concernée
	 * @return "ok" si tout s'est bien passé
	 * @author Thomas [TH]
	 * @date 5 jan. 2015
	 * @version 1
	 */
	@RequestMapping(value = URL_VOIES_ARRETS + "/ajouter", method = POST)
	public @ResponseBody String ajouterVoieArret(@RequestParam int idVoie,
			@RequestParam int idArret) {
		stockageService.ajouterVoieArret(idVoie, idArret);
		return "ok";
	}

	/**
	 * Supprime en AJAX l'association voie / arret donnée en paramètre
	 * 
	 * @name supprimerVoieArret
	 * @description Supprime en AJAX l'association voie / arret donnée en
	 *              paramètre
	 * @param idVoie
	 *            L'identifiant de la voie concernée
	 * @param idArret
	 *            L'identifiant de l'arrêt concernée
	 * @return "ok" si tout s'est bien passé
	 * @author Thomas [TH]
	 * @date 5 jan. 2015
	 * @version 1
	 */
	@RequestMapping(value = URL_VOIES_ARRETS + "/supprimer", method = POST)
	public @ResponseBody String supprimerVoieArret(@RequestParam int idVoie,
			@RequestParam int idArret) {
		stockageService.supprimerVoieArret(idVoie, idArret);
		return "ok";
	}

	/* ARRETS */

	/**
	 * Affiche la JSP de gestion des arrets
	 * 
	 * @name afficherArrets
	 * @description Affiche la JSP de gestion des arrets
	 * @return La vue de la JSP de gestion des arrets
	 * @author Cyril [CS]
	 * @date 5 jan. 2015
	 * @version 1
	 */
	@RequestMapping(value = URL_ARRETS, method = GET)
	public ModelAndView afficherArrets() {
		ModelAndView view = new ModelAndView(URL_MODULE + SLASH + JSP_ARRETS);
		view.addObject(URL_MODULE_CLE, URL_MODULE);
		view.addObject(URL_PAGE_CLE, URL_ARRETS);
		return view;
	}
	
	/**
	 * Retourne en AJAX la liste des arrets au format JSON
	 * 
	 * @name getListeArrets
	 * @description Retourne en AJAX la liste des arrets au format JSON
	 * @return La liste des arrets au format JSON
	 * @author Cyril [CS]
	 * @date 5 jan. 2015
	 * @version 1
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = URL_ARRETS + "/get", method = POST)
	public @ResponseBody String getListeArrets() {
		List<Arret> arrets = stockageService.getArrets();
		Intervalle intervalle;
		JSONArray array = new JSONArray();
		for (Arret a : arrets) {
			JSONObject object = new JSONObject();
			object.put("'id'", a.getId());
			object.put("'nom'", "'" + a.getNom() + "'");
			object.put("'estCommercial'", "'" + a.getEstcommercial() + "'");
			intervalle = stockageService.getTempsImmobilisation(a.getTempsimmobilisationId());
			object.put("'tempsImmobilisationID'", "'" + intervalle.getId() + "'");
			object.put("'tempsImmobilisationMIN'", "'" + intervalle.getMin() + "'");
			object.put("'tempsImmobilisationPREF'", "'" + intervalle.getPref() + "'");
			object.put("'tempsImmobilisationMAX'", "'" + intervalle.getMax() + "'");
			object.put("'estEntree'", "'" + a.getEstentreedepot() + "'");
			object.put("'estSortie'", "'" + a.getEstsortiedepot() + "'");
			object.put("'estLieuEchangeConducteur'", "'" + a.getEstlieuechangeconducteur() + "'");
			object.put("'estTerminus'", "'" + stockageService.getEstTerminus(a.getId()) + "'");
			object.put("'estDepot'", "'" + stockageService.getEstDepot(a.getId()) + "'");
			array.add(object);
		}
		String validJSONString = array.toString().replace("'", "\"")
				.replace("=", ":");
		return validJSONString;
	}
	/**
	 * Retourne en AJAX la liste des arrets où les échanges conducteur sont possibles au format JSON
	 * 
	 * @name getListeArretsEchangesConducteurs
	 * @description Retourne en AJAX la liste des arrets où les échanges conducteur sont possibles au format JSON
	 * @return La liste des arrets où les échanges conducteur sont possibles au format JSON
	 * @author Thomas [TH]
	 * @date 11 jan. 2015
	 * @version 1
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = URL_ARRETS + "/getEchangesConducteurs", method = POST)
	public @ResponseBody String getListeArretsEchangesConducteurs() {
		List<Arret> arrets = stockageService.getArretsEchangesConducteurs();
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
	 * Met à jour en AJAX le temps d'immobilisation sélectionné
	 * 
	 * @name updateArret
	 * @description Met à jour en AJAX le temps d'immobilisation sélectionné
	 * @param id L'identifiant de l'intervalle à mettre à jour
	 * @param newvalue La nouvelle valeur saisie
	 * @param colname La colonne mise à jour
	 * @return "ok" si tout s'est bien passé
	 * @author Cyril [CS]
	 * @date 5 jan. 2015
	 * @version 1
	 */
	@RequestMapping(value = URL_ARRETS + "/updateTempsImmobilisation", method = POST)
	public @ResponseBody String updateTempsImmobilisation(@RequestParam int id, @RequestParam String newvalue, @RequestParam String colname) {
		stockageService.updateTempsImmobilisationArret(id, colname, newvalue);
		return "ok" ;
	}
	
	/**
	 * Met à jour en AJAX l'arrêt sélectionné
	 * 
	 * @name updateArret
	 * @description Met à jour en AJAX l'arrêt sélectionné
	 * @param id L'identifiant de l'intervalle à mettre à jour
	 * @param newvalue La nouvelle valeur saisie
	 * @param colname La colonne mise à jour
	 * @return "ok" si tout s'est bien passé
	 * @author Cyril [CS]
	 * @date 5 jan. 2015
	 * @version 1
	 */
	@RequestMapping(value = URL_ARRETS + "/updateArret", method = POST)
	public @ResponseBody String updateArret(@RequestParam int id, @RequestParam String newvalue, @RequestParam String colname) {
		stockageService.updateArret(id, colname, newvalue);
		return "ok" ;
	}

	/**
	 * Créer en AJAX un nouvel arret
	 * 
	 * @name ajouter Arret
	 * @description Créer en AJAX un nouvel arret
	 * @return "ok" si tout s'est bien passé
	 * @author Cyril [CS]
	 * @date 5 jan. 2015
	 * @version 1
	 */
	@RequestMapping(value = URL_ARRETS + "/ajouter", method = POST)
	public @ResponseBody String ajouterArret() {
		stockageService.ajouterArret();
		return "ok";
	}

	/**
	 * Supprime en AJAX l'arrêt d'identifiant donné en paramètre
	 * 
	 * @name supprimerArret
	 * @description Supprime en AJAX l'arrêt d'identifiant donné en paramètre
	 * @param id L'identifiant de l'arrê à supprimer
	 * @return "ok" si tout s'est bien passé
	 * @author Cyril [CS]
	 * @date 5 jan. 2015
	 * @version 1
	 */
	@RequestMapping(value = URL_ARRETS + "/supprimer", method = POST)
	public @ResponseBody String supprimerArret(@RequestParam int id) {
		stockageService.supprimerArret(id);
		return "ok";
	}


	/* TRANSITIONS */

	/**
	 * Affiche la JSP de gestion des transitions
	 * 
	 * @name afficherTransitions
	 * @description Affiche la JSP de gestion des transitions
	 * @return La vue de la JSP de gestion des transitions
	 * @author Thomas [TH]
	 * @date 3 jan. 2015
	 * @version 1
	 */
	@RequestMapping(value = URL_TRANSITIONS, method = GET)
	public ModelAndView afficherTransitions() {
		ModelAndView view = new ModelAndView(URL_MODULE + SLASH
				+ JSP_TRANSITIONS);
		view.addObject(URL_MODULE_CLE, URL_MODULE);
		view.addObject(URL_PAGE_CLE, URL_TRANSITIONS);
		return view;
	}

	/**
	 * Retourne en AJAX la liste des transitions au format JSON
	 * 
	 * @name getListeTransitions
	 * @description Retourne en AJAX la liste des transitions au format JSON
	 * @return La liste des transitions au format JSON
	 * @author Thomas [TH]
	 * @date 3 jan. 2015
	 * @version 1
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = URL_TRANSITIONS + "/get", method = POST)
	public @ResponseBody String getListeTransitions() {
		List<Transition> transitions = stockageService.getTransitions();
		JSONArray array = new JSONArray();
		for (Transition t : transitions) {
			JSONObject object = new JSONObject();
			object.put("'id'", t.getId());
			object.put("'duree'", "'" + t.getDuree() + "'");
			object.put("'arretPrecedent_id'", "'" + t.getArretprecedentId()
					+ "'");
			object.put("'arretSuivant_id'", "'" + t.getArretsuivantId() + "'");
			array.add(object);
		}
		String validJSONString = array.toString().replace("'", "\"")
				.replace("=", ":");
		return validJSONString;
	}

	/**
	 * Met à jour en AJAX la transition sélectionnée
	 * 
	 * @name updateTransition
	 * @description Met à jour en AJAX la transition sélectionnée
	 * @param id
	 *            L'identifiant de la transition à mettre à jour
	 * @param newvalue
	 *            La nouvelle valeur saisie
	 * @param colname
	 *            La colonne mise à jour
	 * @return "ok" si tout s'est bien passé
	 * @author Thomas [TH]
	 * @date 3 jan. 2015
	 * @version 1
	 */
	@RequestMapping(value = URL_TRANSITIONS + "/update", method = POST)
	public @ResponseBody String updateTransition(@RequestParam int id,
			@RequestParam String newvalue, @RequestParam String colname) {
		stockageService.updateTransition(id, colname, newvalue);
		return "ok";
	}

	/**
	 * Créer en AJAX une nouvelle transition
	 * 
	 * @name ajouterTransition
	 * @description Créer en AJAX une nouvelle transition
	 * @return "ok" si tout s'est bien passé
	 * @author Thomas [TH]
	 * @date 3 jan. 2015
	 * @version 1
	 */
	@RequestMapping(value = URL_TRANSITIONS + "/ajouter", method = POST)
	public @ResponseBody String ajouterTransition() {
		stockageService.ajouterTransition();
		return "ok";
	}

	/**
	 * Supprime en AJAX la transition d'identifiant donné en paramètre
	 * 
	 * @name supprimerTransition
	 * @description Supprime en AJAX la transition d'identifiant donné en
	 *              paramètre
	 * @param id
	 *            L'identifiant de la transition à supprimer
	 * @return "ok" si tout s'est bien passé
	 * @author Thomas [TH]
	 * @date 3 jan. 2015
	 * @version 1
	 */
	@RequestMapping(value = URL_TRANSITIONS + "/supprimer", method = POST)
	public @ResponseBody String supprimerTransition(@RequestParam int id) {
		stockageService.supprimerTransition(id);
		return "ok";
	}

	/* IMPORTER ET EXPORTER */

	@RequestMapping(value = URL_IMPORTER_EXPORTER, method = GET)
	public ModelAndView afficherImporter() {
		ModelAndView view = new ModelAndView(URL_MODULE + SLASH
				+ JSP_IMPORTER_EXPORTER);
		view.addObject(URL_MODULE_CLE, URL_MODULE);
		view.addObject(URL_PAGE_CLE, URL_IMPORTER_EXPORTER);
		return view;
	}

	@RequestMapping(value = URL_IMPORTER_EXPORTER, method = POST)
	public ModelAndView traiterImporter(
			@RequestParam("fichierImporte") MultipartFile fichier) {
		String chemin = RACINE + servletContext.getContextPath().substring(1);
		File fichierSauve = new File(chemin + File.separator
				+ "fichierImporte.xml");
		try {
			FileUtils.writeByteArrayToFile(fichierSauve, fichier.getBytes());

		} catch (IOException e) {
			e.printStackTrace();
		}

		domService.importerReseau(fichierSauve);

		return afficherImporter();
	}

	/**
	 * Génère le fichier XML correspondant au réseau et le télécharge
	 * 
	 * @name traiterExporter
	 * @description Génère le fichier XML correspondant au réseau et le
	 *              télécharge
	 * @param request
	 *            La requete HTTP envoyé
	 * @param response
	 *            La réponse renvoyé au client
	 * @author Thomas [TH]
	 * @date 6 jan. 2015
	 * @version 1
	 */
	@RequestMapping(value = URL_IMPORTER_EXPORTER + "/exporter", method = GET)
	public void traiterExporter(HttpServletRequest request,
			HttpServletResponse response) {

		// Récupération du chemin vers le fichier
		String cheminApp = RACINE
				+ servletContext.getContextPath().substring(1);
		String cheminFichier = domService.exporterReseau();
		String cheminComplet = cheminApp + File.separator + cheminFichier;

		// Création d'un fichier téléchargeable
		File fichier = new File(cheminComplet);
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(fichier);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// Récupération du type MIME du fichier
		String mimeType = servletContext.getMimeType(cheminComplet);
		if (mimeType == null) {
			// Si le type MIME n'est pas trouvé, on le met à binary par défaut
			mimeType = "application/octet-stream";
		}

		// Ajout des attributs à la réponse
		response.setContentType(mimeType);
		response.setContentLength((int) fichier.length());

		// Ajout des headers à la réponse
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"",
				fichier.getName());
		response.setHeader(headerKey, headerValue);

		// Récupération du output stream de la réponse
		OutputStream outStream;
		try {
			outStream = response.getOutputStream();

			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead = -1;

			// Ecriture de bytes récupérés depuis le input stream dans le output
			// stream
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}

			inputStream.close();
			outStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
