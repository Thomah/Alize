package alize.nau.controlleur;

import static alize.commun.Constantes.*;
import static alize.nau.commun.Constantes.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

import alize.nau.service.DOMService;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;

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
		ModelAndView view = new ModelAndView(URL_MODULE + SLASH + JSP_AFFICHERARRETS);
		view.addObject(URL_MODULE_CLE, URL_MODULE);
		return view;
	}

}
