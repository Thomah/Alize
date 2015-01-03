package alize.nau.controlleur;

import static alize.commun.Constantes.*;
import static alize.nau.commun.Constantes.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;
import alize.commun.modele.tables.pojos.Arret;
import alize.commun.modele.tables.pojos.Ligne;
import alize.commun.modele.tables.pojos.Transition;
import alize.commun.modele.tables.pojos.Voie;
import alize.commun.modele.tables.records.ArretRecord;
import alize.commun.modele.tables.records.TerminusRecord;
import alize.nau.service.DOMService;

import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletContext;

import org.jooq.Record1;
import org.jooq.Result;

import alize.commun.modele.Tables;
import alize.commun.service.StockageService;

import org.jooq.DSLContext;
import org.jooq.tools.json.JSONArray;
import org.jooq.tools.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NauControlleur {
	
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
	
	@RequestMapping(value = URL_AFFICHERARRETS, method = GET)
	public ModelAndView afficherArrets(ModelMap model) {
		List<List<String>> tableauArrets = new ArrayList<List<String>>();
		
		List<Arret> listeArret = new ArrayList<Arret>();
		Result<ArretRecord> resultArret = dsl.fetch(Tables.ARRET);
		List<Integer> listeIDTerminus = new ArrayList<Integer>();
		Result<TerminusRecord> resultTerminus = dsl.fetch(Tables.TERMINUS);
		List<String> listeTempsImmobilisationPref = new ArrayList<String>();
		
		for(ArretRecord aRec : resultArret){
			Arret a = new Arret();
			a.setNom(aRec .getNom()); 
			a.setId(aRec.getId());
			a.setEstcommercial(aRec.getEstcommercial());
			a.setEstentreedepot(aRec.getEstentreedepot());
			a.setEstlieuechangeconducteur(aRec.getEstlieuechangeconducteur());
			a.setEstsortiedepot(aRec.getEstsortiedepot());
			a.setTempsimmobilisationId(aRec.getTempsimmobilisationId());
			listeArret.add(a);
		}
		for(TerminusRecord tRec : resultTerminus){
			listeIDTerminus.add(tRec.getArretId());
		}
		
		Result<Record1<Time>> result = dsl.select(Tables.INTERVALLE.PREF)
                .from(Tables.INTERVALLE)
                .join(Tables.ARRET)
                .on(Tables.ARRET.TEMPSIMMOBILISATION_ID.equal(Tables.INTERVALLE.ID))
                .fetch();
		for(int i =0; i< result.size(); i++){
			listeTempsImmobilisationPref.add(result.get(i).value1().toString());
		}
//		for(Object rec : result.toArray()){
//			
//			listeTempsImmobilisationPref.add(rec.toString());
//		}
		
		for(int i = 0; i<listeArret.size(); i++){
			Arret a = listeArret.get(i);
			List<String> ligneArret= new ArrayList<String>();
			ligneArret.add(a.getId().toString());
			ligneArret.add(a.getNom());
			ligneArret.add(byteToBoolean(a.getEstcommercial()).toString());
			ligneArret.add(listeTempsImmobilisationPref.get(i));
			ligneArret.add(byteToBoolean(a.getEstentreedepot()).toString());
			ligneArret.add(byteToBoolean(a.getEstsortiedepot()).toString());
			ligneArret.add(byteToBoolean(a.getEstlieuechangeconducteur()).toString());
			Boolean estTerminus = listeIDTerminus.contains(a.getId());
			ligneArret.add(estTerminus.toString());
			tableauArrets.add(ligneArret);
		}
		
		
		
		ModelAndView view = new ModelAndView(URL_MODULE + SLASH + JSP_AFFICHERARRETS);
		
		view.addObject(URL_MODULE_CLE, URL_MODULE);
		view.addObject(TABLEAU_ARRET_CLE, tableauArrets);
		return view;
	}
	
	
	public void supprimerArret(String id){
		dsl.delete(Tables.ARRET)
	      .where(Tables.ARRET.ID.equal(Integer.parseInt(id)));
	}
	
	public Boolean byteToBoolean(Byte b){
		if(b==1){
			return true;
		}else{
			return false;	
		}
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
	 * @param id L'identifiant de la ligne à mettre à jour
	 * @param newvalue La nouvelle valeur saisie
	 * @param colname La colonne mise à jour
	 * @param coltype Le type de la valeur mise à jour
	 * @return "ok" si tout s'est bien passé
	 * @author Thomas [TH]
	 * @date 2 jan. 2015
	 * @version 1
	 */
	@RequestMapping(value = URL_LIGNES + "/update", method = POST)
	public @ResponseBody String updateLigne(@RequestParam int id,
			@RequestParam String newvalue, @RequestParam String colname, @RequestParam String coltype) {
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
	 * @param id L'identifiant de la ligne à supprimer
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
			object.put("'terminusDepart_id'", "'" + v.getTerminusdepartId() + "'");
			object.put("'terminusArrivee_id'", "'" + v.getTerminusarriveeId() + "'");
			array.add(object);
		}
		String validJSONString = array.toString().replace("'", "\"")
				.replace("=", ":");
		return validJSONString;
	}
	
	/**
	 * Retourne en AJAX la liste des terminus parmi les arrêts de la voie au format JSON
	 * 
	 * @name getListeTerminusVoie
	 * @description Retourne en AJAX la liste des terminus parmi les arrêts de la voie au format JSON
	 * @param idVoie La voie sélectionnée
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
	 * @param id L'identifiant de la voie à mettre à jour
	 * @param newvalue La nouvelle valeur saisie
	 * @param colname La colonne mise à jour
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
	 * @param id L'identifiant de la voie à supprimer
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
	
	/* ARRETS */

	/**
	 * Retourne en AJAX la liste des arrets au format JSON
	 * 
	 * @name getListeArrets
	 * @description Retourne en AJAX la liste des arrets au format JSON
	 * @return La liste des arrets au format JSON
	 * @author Thomas [TH]
	 * @date 3 jan. 2015
	 * @version 1
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = URL_ARRETS + "/get", method = POST)
	public @ResponseBody String getListeArrets() {
		List<Arret> arrets = stockageService.getArrets();
		JSONArray array = new JSONArray();
		for (Arret a : arrets) {
			JSONObject object = new JSONObject();
			object.put("'id'", a.getId());
			object.put("'nom'", "'" + a.getNom() + "'");
			object.put("'estCommercial'", "'" + a.getEstcommercial() + "'");
			object.put("'estEntreeDepot'", "'" + a.getEstentreedepot() + "'");
			object.put("'estSortieDepot'", "'" + a.getEstsortiedepot() + "'");
			object.put("'estLieuEchangeConducteur'", "'" + a.getEstlieuechangeconducteur() + "'");
			object.put("'tempsImmobilisation_id'", "'" + a.getTempsimmobilisationId() + "'");
			array.add(object);
		}
		String validJSONString = array.toString().replace("'", "\"")
				.replace("=", ":");
		return validJSONString;
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
		ModelAndView view = new ModelAndView(URL_MODULE + SLASH + JSP_TRANSITIONS);
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
			object.put("'arretPrecedent_id'", "'" + t.getArretprecedentId() + "'");
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
	 * @param id L'identifiant de la transition à mettre à jour
	 * @param newvalue La nouvelle valeur saisie
	 * @param colname La colonne mise à jour
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
	 * @description Supprime en AJAX la transition d'identifiant donné en paramètre
	 * @param id L'identifiant de la transition à supprimer
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
		ModelAndView view = new ModelAndView(URL_MODULE + SLASH + JSP_IMPORTER_EXPORTER);
		view.addObject(URL_MODULE_CLE, URL_MODULE);
		view.addObject(URL_PAGE_CLE, URL_IMPORTER_EXPORTER);
		return view;
	}

	@RequestMapping(value = URL_IMPORTER_EXPORTER, method = POST)
	public ModelAndView traiterImporter(
			@RequestParam("fichierImporte") MultipartFile fichier) {
		String chemin = RACINE + servletContext.getContextPath().substring(1);
		File fichierSauve = new File(chemin + File.separator + "fichierImporte.xml");
		try {
			FileUtils.writeByteArrayToFile(fichierSauve, fichier.getBytes());

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		domService.importerReseau(fichierSauve);
		
		return afficherImporter();
	}
}
