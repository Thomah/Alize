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
	@RequestMapping(value=URL_CONTRAINTES + "/selectLigne", method=POST)
	public @ResponseBody String getListeVoies(@RequestParam int idLigne) {
		List<Voie> voies = stockageService.getVoiesPourLaLigne(idLigne);
		JSONArray array = new JSONArray();
		for(Voie v : voies) {
			JSONObject object = new JSONObject();
			object.put("'id'", v.getId());
			object.put("'direction'", "'" + v.getDirection() + "'");
			object.put("'idTerminusDepart'", v.getTerminusdepartId());
			object.put("'idTerminusArrivee'", v.getTerminusarriveeId());
			array.add(object);
		}
		String validJSONString = array.toString().replace("'", "\"").replace("=", ":");
		return validJSONString;
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
	@RequestMapping(value=URL_CONTRAINTES + "/selectVoie", method=POST)
	public @ResponseBody String getListeArrets(@RequestParam int idVoie) {
		List<Arret> arrets = stockageService.getArretsPourLaVoie(idVoie);
		JSONArray array = new JSONArray();
		for(Arret a : arrets) {
			JSONObject object = new JSONObject();
			object.put("'id'", a.getId());
			object.put("'nom'", "'" + a.getNom() + "'");
			object.put("'estCommercial'", a.getEstcommercial());
			object.put("'estEntreeDepot'", a.getEstentreedepot());
			object.put("'estSortieDepot'", a.getEstsortiedepot());
			object.put("'estOccupe'", a.getEstoccupe());
			object.put("'estLieuEchangeConducteur'", a.getEstlieuechangeconducteur());
			array.add(object);
		}
		String validJSONString = array.toString().replace("'", "\"").replace("=", ":");
		return validJSONString;
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
