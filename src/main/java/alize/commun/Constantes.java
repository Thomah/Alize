package alize.commun;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * @name Communes
 * @author Cyril [CS]
 * @date 23 oct. 2014
 * @version 1
 */
public class Constantes {
	
	public static final NumberFormat formatter = new DecimalFormat("00");

	public static final String URL_MODULE_CLE = "URL_MODULE";
	
	public static final String SLASH = "/";
	
	public static final String RACINE = System.getProperty("catalina.home") + File.separator + "webapps" + File.separator;
	
}