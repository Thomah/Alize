package alize.nau.controlleur;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/nau")
public class NauControlleur {

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView dashboard(ModelMap model) {
		return new ModelAndView("dashboard.jsp");
	}

}
