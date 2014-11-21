package alize.eole.controlleur;

import static alize.commun.Constantes.*;
import static alize.eole.commun.Constantes.*;
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
public class EoleControlleur {
	
	@Autowired
	private ServletContext servletContext;

	@RequestMapping(value = URL_INDEX, method = GET)
	public ModelAndView afficherDashboard(ModelMap model) {
		ModelAndView view = new ModelAndView(URL_MODULE + SLASH + JSP_INDEX);
		view.addObject(URL_MODULE_CLE, URL_MODULE);
		return view;
	}

	@RequestMapping(value = URL_CONTRAINTES, method = GET)
	public ModelAndView afficherImporter(ModelMap model) {
		ModelAndView view = new ModelAndView(URL_MODULE + SLASH + JSP_CONTRAINTES);
		view.addObject(URL_MODULE_CLE, URL_MODULE);
		return view;
	}

	@RequestMapping(value = URL_CONTRAINTES, method = POST)
	public ModelAndView traiterImporter(
			@RequestParam("fichierImporte") MultipartFile fichier) {
		ModelAndView view = new ModelAndView(URL_MODULE + SLASH + URL_CONTRAINTES);
		view.addObject(URL_MODULE_CLE, URL_MODULE);
		return view;
	}

}
