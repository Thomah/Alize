package alize.nau.service;

import static alize.commun.modele.tables.Arret.*;
import static alize.commun.modele.tables.Depot.*;
import static alize.commun.modele.tables.Intervalle.*;
import static alize.commun.modele.tables.Ligne.*;
import static alize.commun.modele.tables.LigneVoie.*;
import static alize.commun.modele.tables.Voie.*;
import static alize.commun.modele.tables.VoieArret.*;
import static alize.commun.modele.tables.Transition.*;

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
import alize.commun.modele.tables.records.DepotRecord;
import alize.commun.modele.tables.records.IntervalleRecord;
import alize.commun.modele.tables.records.LigneRecord;
import alize.commun.modele.tables.records.LigneVoieRecord;
import alize.commun.modele.tables.records.TransitionRecord;
import alize.commun.modele.tables.records.VoieArretRecord;
import alize.commun.modele.tables.records.VoieRecord;

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

		Iterator<Element> i = listElements.iterator();
		Element courant, filsCourant;
		IntervalleRecord intervalle = dsl.newRecord(INTERVALLE);

		while (i.hasNext()) {
			courant = (Element) i.next();
			intervalle.setId(Integer.valueOf(courant.getAttributeValue("id")));

			SimpleDateFormat formater = new SimpleDateFormat("hh:mm:ss");

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

		Iterator<Element> i = listElements.iterator();
		Element courant;
		ArretRecord arret = dsl.newRecord(ARRET);

		while (i.hasNext()) {
			courant = (Element) i.next();
			arret.setId(Integer.valueOf(courant.getAttributeValue("id")));
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
	public void importerDepots(Element racine) {
		
		List<Element> listElements = racine.getChild("depots").getChildren();

		Iterator<Element> i = listElements.iterator();
		Element courant;
		DepotRecord depot = dsl.newRecord(DEPOT);

		while (i.hasNext()) {
			courant = (Element) i.next();
			depot.setId(null);
			depot.setArretId(Integer.valueOf(courant.getAttributeValue("ref")));
			depot.store();
		}
		
	}
	
	@Override
	public void importerLignes(Element racine) {
		
		List<Element> listElements = racine.getChild("lignes").getChildren();
		List<Element> listElementsArrets;

		Iterator<Element> i = listElements.iterator();
		Iterator<Element> i2;
		Element courant, enfant;
		LigneRecord ligne = dsl.newRecord(LIGNE);
		LigneVoieRecord ligneVoie = dsl.newRecord(LIGNE_VOIE);
		VoieRecord voie = dsl.newRecord(VOIE);
		VoieArretRecord voieArret = dsl.newRecord(VOIE_ARRET);

		while (i.hasNext()) {
			courant = (Element) i.next();
			ligne.setId(Integer.valueOf(courant.getAttributeValue("id")));
			ligne.store();
			
			ligneVoie.setId(null);
			ligneVoie.setLigneId(ligne.getId());
			
			while (i.hasNext()) {
				courant = (Element) i.next();
				voie.setId(null);
				voie.setDirection(courant.getAttributeValue("direction"));
				voie.setTerminusdepartId(Integer.valueOf(courant.getChild("terminusDepart").getChild("Terminus").getAttributeValue("ref")));
				voie.setTerminusarriveeId(Integer.valueOf(courant.getChild("terminusArrivee").getChild("Terminus").getAttributeValue("ref")));
				voie.store();

				ligneVoie.setVoieId(voie.getId());
				
				listElementsArrets = courant.getChild("arrets").getChildren();
				i2 = listElementsArrets.iterator();
				while(i2.hasNext()) {
					enfant = (Element) i2.next();
					voieArret.setId(null);
					voieArret.setArretId(Integer.valueOf(enfant.getAttributeValue("ref")));
					voieArret.setVoieId(voie.getId());
				}
			}
		}
		
	}
	
	@Override
	public void importerTransitions(Element racine) {
		
		List<Element> listElements = racine.getChild("transition").getChildren();

		Iterator<Element> i = listElements.iterator();
		Element courant;
		TransitionRecord transition = dsl.newRecord(TRANSITION);
		
		SimpleDateFormat formater = new SimpleDateFormat("hh:mm:ss");

		while (i.hasNext()) {
			courant = (Element) i.next();
			transition.setId(Integer.valueOf(courant.getAttributeValue("id")));
			
			try {
				Date dureeDate = formater.parse(courant.getAttributeValue("duree"));
				java.sql.Date dureeSQLDate = new java.sql.Date(dureeDate.getTime());
				transition.setDuree(dureeSQLDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			transition.setArretprecedentId(Integer.valueOf(courant.getChild("arretPrecedent").getChild("Arret").getAttributeValue("ref")));
			transition.setArretsuivantId(Integer.valueOf(courant.getChild("arretSuivant").getChild("Arret").getAttributeValue("ref")));
			transition.store();
		}
		
	}

	@Override
	public void setDsl(DSLContext dsl) {
		this.dsl = dsl;
	}

}
