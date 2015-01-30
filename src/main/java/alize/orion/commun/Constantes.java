package alize.orion.commun;

import static alize.commun.Constantes.SLASH;

public class Constantes {
	
	public static final String NOM_MODULE = "Orion";
	
	/* URL */
	
	public static final String URL_MODULE = "orion";
	
	public static final String URL_INDEX = SLASH + URL_MODULE;

	public static final String URL_SERVICES = URL_INDEX + SLASH + "services";

	public static final String URL_CONDUCTEURS = URL_INDEX + SLASH + "conducteurs";

	public static final String URL_FEUILLES_DE_SERVICES = URL_INDEX + SLASH + "feuillesdeservices";
	
	/* JSP */
	
	public static final String JSP_INDEX = "index";	

	public static final String JSP_CONDUCTEURS = "conducteurs";

	public static final String JSP_FEUILLES_DE_SERVICES = "feuillesdeservices";
	
}
