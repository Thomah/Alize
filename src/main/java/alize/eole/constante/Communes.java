package alize.eole.constante;

import static alize.commun.Constantes.SLASH;

public class Communes {
	
	public static final String NOM_MODULE = "Eole";

	/* URL */
	
	public static final String URL_MODULE = "eole";

	public static final String URL_INDEX = SLASH + URL_MODULE;
	
	public static final String URL_CONTRAINTES = URL_INDEX + SLASH + "contraintes";
	
	public static final String URL_FDS = URL_INDEX + SLASH + "fds";

	public static final String URL_FDS_PERIODICITES = URL_INDEX + SLASH + "fdsperiodicites";

	public static final String URL_SERVICES = URL_INDEX + SLASH + "services";
	
	/* JSP */
	
	public static final String JSP_INDEX = "index";
	
	public static final String JSP_CONTRAINTES = "contraintes";

	public static final String JSP_FDS = "fds";

	public static final String JSP_FDS_PERIODICITES = "fdsperiodicites";

	public static final String JSP_SERVICES = "services";
	
}
