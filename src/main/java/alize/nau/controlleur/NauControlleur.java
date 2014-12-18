package alize.nau.controlleur;

import static alize.commun.Constantes.*;
import java.lang.Object;

import alize.commun.controlleur.AliZeControlleur;
import static alize.nau.commun.Constantes.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;
import alize.commun.modele.tables.daos.ArretDao;
import alize.commun.modele.tables.pojos.Arret;
import alize.commun.modele.tables.pojos.Terminus;
import alize.commun.modele.tables.records.ArretRecord;
import alize.commun.modele.tables.records.IntervalleRecord;
import alize.commun.modele.tables.records.TerminusRecord;
import alize.nau.service.DOMService;

import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;

import alize.commun.modele.Keys;
import alize.commun.modele.Tables;

import org.jooq.DSLContext;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	@RequestMapping(value = URL_INDEX, method = GET)
	public ModelAndView afficherDashboard(ModelMap model) {
		ModelAndView view = new ModelAndView(URL_MODULE + SLASH + JSP_INDEX);
		view.addObject(URL_MODULE_CLE, URL_MODULE);
		return view;
	}

	@RequestMapping(value = URL_IMPORTER, method = GET)
	public ModelAndView afficherImporter(ModelMap model) {
		ModelAndView view = new ModelAndView(URL_MODULE + SLASH + JSP_IMPORTER);
		view.addObject(URL_MODULE_CLE, URL_MODULE);
		return view;
	}

	@RequestMapping(value = URL_IMPORTER, method = POST)
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
		
		ModelAndView view = new ModelAndView(URL_MODULE + SLASH + JSP_IMPORTER);
		view.addObject(URL_MODULE_CLE, URL_MODULE);
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

}
