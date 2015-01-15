package alize.nau.commun;

import static alize.commun.Constantes.SLASH;

public class Constantes {
	
	public static final String NOM_MODULE = "Nau";
	
	/* URL */
	
	public static final String URL_MODULE = "nau";
	
	public static final String URL_INDEX = SLASH + URL_MODULE;
	
	public static final String URL_LIGNES = URL_INDEX + SLASH + "lignes";

	public static final String URL_LIGNES_VOIES = URL_INDEX + SLASH + "lignesvoies";
	
	public static final String URL_VOIES = URL_INDEX + SLASH + "voies";

	public static final String URL_VOIES_TRANSITIONS = URL_INDEX + SLASH + "voiestransitions";

	public static final String URL_ARRETS = URL_INDEX + SLASH + "arrets";
	
	public static final String URL_TRANSITIONS = URL_INDEX + SLASH + "transitions";

	public static final String URL_IMPORTER_EXPORTER = URL_INDEX + SLASH + "importexport";

	/* JSP */
	
	public static final String JSP_INDEX = "index";
	
	public static final String JSP_LIGNES = "lignes";

	public static final String JSP_LIGNES_VOIES = "lignesvoies";
	
	public static final String JSP_VOIES = "voies";

	public static final String JSP_VOIES_TRANSITIONS = "voiestransitions";
	
	public static final String JSP_ARRETS = "arrets";

	public static final String JSP_TRANSITIONS = "transitions";
	
	public static final String JSP_IMPORTER_EXPORTER = "importexport";
	
	/* CLE */
	
	public static final String TABLEAU_ARRET_CLE = "tableauArretCle";
	
	public static final String TERMINUS_CLE = "terminusCle";
	
	public static final String ARRET_VALEUR = "arretValeur";
	
	
}
