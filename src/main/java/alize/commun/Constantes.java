package alize.commun;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * @author Cyril [CS]
 * @version 1
 */
public class Constantes {
	
	public static final NumberFormat formatter = new DecimalFormat("00");

	public static final String URL_MODULE_CLE = "URL_MODULE";
	
	public static final String URL_PAGE_CLE = "URL_PAGE";
	
	public static final String SLASH = "/";
	
	public static final String RACINE = System.getProperty("catalina.home") + File.separator + "webapps" + File.separator;
	
}