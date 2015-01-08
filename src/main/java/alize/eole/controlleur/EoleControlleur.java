package alize.eole.controlleur;

import static alize.commun.Constantes.*;
import static alize.eole.constante.Communes.*;
import static alize.eole.constante.Contraintes.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.jooq.tools.json.JSONArray;
import org.jooq.tools.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import alize.commun.modele.tables.pojos.Arret;
import alize.commun.modele.tables.pojos.Periodicite;
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

			// Ajout des propriétés dans les attributs de la classe
			view.addObject(NB_VEHICULES_MAX_LABEL,
					prop.getProperty(NB_VEHICULES_MAX_LABEL));
			view.addObject(TEMPS_TRAVAIL_MAX_LABEL,
					prop.getProperty(TEMPS_TRAVAIL_MAX_LABEL));
			view.addObject(TEMPS_CONDUITE_MAX_LABEL,
					prop.getProperty(TEMPS_CONDUITE_MAX_LABEL));
			view.addObject(TEMPS_PAUSE_MIN_LABEL,
					prop.getProperty(TEMPS_PAUSE_MIN_LABEL));
			view.addObject(TEMPS_PAUSE_MAX_LABEL,
					prop.getProperty(TEMPS_PAUSE_MAX_LABEL));

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
		view.addObject("voies", stockageService.getVoies());
		view.addObject("arrets", stockageService.getArrets());
		view.addObject("periodicites", stockageService.getPeriodicites());

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
	 * 
	 * @param idVoie
	 *            L'identifiant de la voie souhaitée
	 * @return La liste des arrets au format JSON
	 * @author Thomas
	 * @date 21/11/2014
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
			object.put("'estCommercial'", a.getEstcommercial());
			object.put("'estEntreeDepot'", a.getEstentreedepot());
			object.put("'estSortieDepot'", a.getEstsortiedepot());
			object.put("'estOccupe'", a.getEstoccupe());
			object.put("'estLieuEchangeConducteur'",
					a.getEstlieuechangeconducteur());
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

	/**
	 * Traite les données définies dans la page des contraintes
	 * 
	 * @param model
	 *            Les données associées à la page
	 * @return La page associée
	 * @author Thomas
	 * @date 30/11/2014
	 */
	@RequestMapping(value = URL_CONTRAINTES, method = POST)
	public ModelAndView traiterContraintes(ModelMap model,
			HttpServletRequest request) {

		Properties properties = new Properties();

		Object obj = request.getParameter(NB_VEHICULES_MAX_LABEL);
		if (obj != null) {
			properties.setProperty(NB_VEHICULES_MAX_LABEL, obj.toString());
		}

		obj = request.getParameter(TEMPS_TRAVAIL_MAX_LABEL);
		if (obj != null) {
			properties.setProperty(TEMPS_TRAVAIL_MAX_LABEL, obj.toString());
		}

		obj = request.getParameter(TEMPS_CONDUITE_MAX_LABEL);
		if (obj != null) {
			properties.setProperty(TEMPS_CONDUITE_MAX_LABEL, obj.toString());
		}

		obj = request.getParameter(TEMPS_PAUSE_MIN_LABEL);
		if (obj != null) {
			properties.setProperty(TEMPS_PAUSE_MIN_LABEL, obj.toString());
		}

		obj = request.getParameter(TEMPS_PAUSE_MAX_LABEL);
		if (obj != null) {
			properties.setProperty(TEMPS_PAUSE_MAX_LABEL, obj.toString());
		}

		File file = new File(NOM_FICHIER);
		try {
			FileOutputStream fileOut = new FileOutputStream(file);
			properties.store(fileOut, "Contraintes");
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return afficherContraintes(model);
	}

}
