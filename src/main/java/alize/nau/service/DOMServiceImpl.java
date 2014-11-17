package alize.nau.service;

import static alize.commun.modele.tables.Arret.*;
import static alize.commun.modele.tables.Intervalle.*;

import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;

import alize.commun.modele.tables.records.ArretRecord;
import alize.commun.modele.tables.records.IntervalleRecord;

public class DOMServiceImpl implements DOMService {

	@Autowired
	private DSLContext dsl;

	@Override
	public void importerReseau(File fichier) {

		SAXBuilder sxb = new SAXBuilder();
		Element racine = null;

		try {
			// Ouverture du document sauvegardé
			Document document = sxb.build(fichier);

			// On initialise un nouvel élément racine
			racine = document.getRootElement();

			importerIntervalles(racine);
			importerArrets(racine);

		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void importerIntervalles(Element racine) {

		List<Element> listElements = racine.getChild("intervalles").getChildren();

		// On crée un Iterator sur notre liste
		Iterator<Element> i = listElements.iterator();
		Element courant, filsCourant;
		IntervalleRecord intervalle = dsl.newRecord(INTERVALLE);

		while (i.hasNext()) {
			courant = (Element) i.next();
			intervalle.setId(null);

			SimpleDateFormat formater = null;
			formater = new SimpleDateFormat("hh:mm:ss");

			try {
				filsCourant = courant.getChild("min");
				if (filsCourant != null) {
					Date dureeDate = formater.parse(filsCourant.getText());
					java.sql.Time dureeTime = new Time(dureeDate.getTime());
					intervalle.setMin(dureeTime);
				}
				filsCourant = courant.getChild("pref");
				if (filsCourant != null) {
					Date dureeDate = formater.parse(filsCourant.getText());
					java.sql.Time dureeTime = new Time(dureeDate.getTime());
					intervalle.setPref(dureeTime);
				}
				filsCourant = courant.getChild("max");
				if (filsCourant != null) {
					Date dureeDate = formater.parse(filsCourant.getText());
					java.sql.Time dureeTime = new Time(dureeDate.getTime());
					intervalle.setMax(dureeTime);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}

			intervalle.store();
		}
	}

	@Override
	public void importerArrets(Element racine) {

		List<Element> listElements = racine.getChild("arrets").getChildren();

		// On crée un Iterator sur notre liste
		Iterator<Element> i = listElements.iterator();
		Element courant;
		ArretRecord arret = dsl.newRecord(ARRET);

		while (i.hasNext()) {
			courant = (Element) i.next();
			arret.setId(null);
			arret.setNom(courant.getAttributeValue("nom"));
			arret.setEstcommercial((byte) courant.getAttributeValue("estCommercial").compareTo("true"));
			arret.setEstentreesortiedepot((byte) courant.getAttributeValue("estEntreeSortieDepot").compareTo("true"));
			arret.setEstlieuechangeconducteur((byte) courant.getAttributeValue("estLieuEchangeConducteur").compareTo("true"));
			arret.setEstoccupe((byte)0);
			arret.setTempsimmobilisation(Integer.valueOf(courant.getAttributeValue("tempsImmobilisation")));
			arret.store();
		}
	}

	@Override
	public void setDsl(DSLContext dsl) {
		this.dsl = dsl;
	}

}
