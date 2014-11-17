package alize.nau.service;

import java.io.File;

import org.jdom2.Element;
import org.jooq.DSLContext;

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

	public void importerIntervalles(Element racine);
	
	public void importerArrets(Element racine);
	
	public void importerDepots(Element racine);
	
	public void importerLignes(Element racine);

	public void importerTransitions(Element racine);

	/* GETTERS & SETTERS */
	
	/**
	 * Remplace la valeur de dsl
	 * @name setDsl
	 * @description Remplace la valeur de dsl
	 * @param dsl Le DSLContext à remplacer
	 * @author Thomas [TH]
	 * @date 16 nov. 2014
	 * @version 1
	 */
	public void setDsl(DSLContext dsl);

}
