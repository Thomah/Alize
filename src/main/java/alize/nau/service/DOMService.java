package alize.nau.service;

import java.io.File;

public interface DOMService {
	
	/* IMPORTATION */
	
	/**
	 * Importe le fichier XML représentant un réseau de transport respectant la
	 * grammaire XSD en base de données
	 * 
	 * @name importerReseau
	 * @description Importe le fichier XML représentant un réseau de transport
	 *              respectant la grammaire XSD en base de données
	 * @param fichier
	 *            Le fichier à importer
	 * @author Thomas [TH]
	 * @date 14 nov. 2014
	 * @version 1
	 */
	public void importerReseau(File fichier);
	
	/* EXPORTATION */

	/**
	 * Génère le fichier XML correspondant au réseau
	 * 
	 * @name exporterReseau
	 * @description Génère le fichier XML correspondant au réseau
	 * @return L'URL vers le fichier généré
	 * @author Thomas [TH]
	 * @date 6 jan. 2015
	 * @version 1
	 */
	public String exporterReseau();

}
