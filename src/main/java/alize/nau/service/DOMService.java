package alize.nau.service;

import java.io.File;

public interface DOMService {

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

}
