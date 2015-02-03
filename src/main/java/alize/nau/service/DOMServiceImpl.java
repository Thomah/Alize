package alize.nau.service;

import static alize.commun.Constantes.RACINE;
import static alize.commun.modele.tables.Arret.*;
import static alize.commun.modele.tables.Depot.*;
import static alize.commun.modele.tables.Intervalle.*;
import static alize.commun.modele.tables.Lieu.*;
import static alize.commun.modele.tables.Ligne.*;
import static alize.commun.modele.tables.LigneVoie.*;
import static alize.commun.modele.tables.Reseau.*;
import static alize.commun.modele.tables.Voie.*;
import static alize.commun.modele.tables.VoieTransition.*;
import static alize.commun.modele.tables.Terminus.*;
import static alize.commun.modele.tables.Transition.*;
import static alize.commun.modele.tables.Zonedecroisement.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;

import alize.commun.modele.*;
import alize.commun.modele.tables.pojos.Depot;
import alize.commun.modele.tables.pojos.Ligne;
import alize.commun.modele.tables.pojos.Terminus;
import alize.commun.modele.tables.pojos.Zonedecroisement;
import alize.commun.modele.tables.records.ArretRecord;
import alize.commun.modele.tables.records.DepotRecord;
import alize.commun.modele.tables.records.IntervalleRecord;
import alize.commun.modele.tables.records.LieuRecord;
import alize.commun.modele.tables.records.LigneRecord;
import alize.commun.modele.tables.records.LigneVoieRecord;
import alize.commun.modele.tables.records.ReseauRecord;
import alize.commun.modele.tables.records.TerminusRecord;
import alize.commun.modele.tables.records.TransitionRecord;
import alize.commun.modele.tables.records.VoieTransitionRecord;
import alize.commun.modele.tables.records.VoieRecord;
import alize.commun.modele.tables.records.ZonedecroisementRecord;
import alize.commun.service.StockageService;

public class DOMServiceImpl implements DOMService {
	
    @Autowired
    private ServletContext servletContext;
    
	@Autowired
	private DSLContext dsl;

	@Autowired
	private StockageService stockageService;
	
	/* IMPORTATION */
	
	@Override
	public void importerReseau(File fichier) {

		SAXBuilder sxb = new SAXBuilder();
		Element racine = null;

		try {
			// Ouverture du document sauvegardé
			Document document = sxb.build(fichier);

			// On initialise un nouvel élément racine
			racine = document.getRootElement();
			
			ReseauRecord reseau = dsl.newRecord(RESEAU);
			reseau.setId(null);
			reseau.setNom(null);
			reseau.store();
			
			// Importation des intervalles
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
			
			// Importation des arrets
			listElements = racine.getChild("arrets").getChildren();
			i = listElements.iterator();
			LieuRecord lieu = dsl.newRecord(LIEU);
			ArretRecord arret = dsl.newRecord(ARRET);

			while (i.hasNext()) {
				courant = (Element) i.next();
				lieu.setId(Integer.valueOf(courant.getAttributeValue("id")));
				lieu.store();
				
				arret.setId(Integer.valueOf(courant.getAttributeValue("id")));
				arret.setNom(courant.getAttributeValue("nom"));
				arret.setEstcommercial(Byte.valueOf(courant.getAttributeValue("estCommercial")));
				arret.setEstentreedepot(Byte.valueOf(courant.getAttributeValue("estEntreeDepot")));
				arret.setEstsortiedepot(Byte.valueOf(courant.getAttributeValue("estSortieDepot")));
				arret.setEstlieuechangeconducteur(Byte.valueOf(courant.getAttributeValue("estLieuEchangeConducteur")));
				arret.setTempsimmobilisationId(Integer.valueOf(courant.getAttributeValue("tempsImmobilisation")));
				
				Integer intValue = Integer.valueOf(courant.getAttributeValue("estEnFaceDe"));
				if(intValue != 0) {
					arret.setEstenfacede(intValue);
				} else {
					arret.setEstenfacede(null);
				}
				arret.store();
			}
			
			// Importation des dépôts
			listElements = racine.getChild("depots").getChildren();
			i = listElements.iterator();
			DepotRecord depot = dsl.newRecord(DEPOT);

			while (i.hasNext()) {
				courant = (Element) i.next();
				depot.setId(Integer.valueOf(courant.getAttributeValue("ref")));
				depot.store();
			}
			
			// Importation des terminus
			listElements = racine.getChild("terminus").getChildren();
			i = listElements.iterator();
			TerminusRecord terminus = dsl.newRecord(TERMINUS);

			while (i.hasNext()) {
				courant = (Element) i.next();
				terminus.setId(Integer.valueOf(courant.getAttributeValue("ref")));
				terminus.store();
			}
			
			// Importation des zones de croisement
			listElements = racine.getChild("zonesdecroisement").getChildren();
			i = listElements.iterator();
			ZonedecroisementRecord zonedecroisement = dsl.newRecord(ZONEDECROISEMENT);

			while (i.hasNext()) {
				courant = (Element) i.next();
				zonedecroisement.setId(Integer.valueOf(courant.getAttributeValue("id")));
				zonedecroisement.setNom(courant.getAttributeValue("nom"));
				zonedecroisement.store();
			}
			
			// Importation des transitions
			listElements = racine.getChild("transitions").getChildren();
			i = listElements.iterator();
			lieu = dsl.newRecord(LIEU);
			TransitionRecord transition = dsl.newRecord(TRANSITION);
			
			SimpleDateFormat formater = new SimpleDateFormat("hh:mm:ss");

			while (i.hasNext()) {
				courant = (Element) i.next();
				lieu.setId(Integer.valueOf(courant.getAttributeValue("id")));
				lieu.store();
				
				transition.setId(Integer.valueOf(courant.getAttributeValue("id")));
				
				try {
					Date dureeDate = formater.parse(courant.getAttributeValue("duree"));
					java.sql.Time dureeSQLTime = new java.sql.Time(dureeDate.getTime());
					transition.setDuree(dureeSQLTime);

				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				Integer id = Integer.valueOf(courant.getChild("arretPrecedent").getChild("Arret").getAttributeValue("ref"));
				if(id == 0) {
					transition.setArretprecedentId(null);
				} else {
					transition.setArretprecedentId(id);
				}
				
				id = Integer.valueOf(courant.getChild("arretSuivant").getChild("Arret").getAttributeValue("ref"));
				if(id == 0) {
					transition.setArretsuivantId(null);
				} else {
					transition.setArretsuivantId(id);
				}

				id = Integer.valueOf(courant.getChild("zoneDeCroisement").getChild("Zonedecroisement").getAttributeValue("ref"));
				if(id == 0) {
					transition.setZonedecroisementId(null);
				} else {
					transition.setZonedecroisementId(id);
				}
				
				transition.store();
			}
			
			// Importation des lignes
			listElements = racine.getChild("lignes").getChildren();
			List<Element> listElementsVoies;
			List<Element> listElementsArrets;
			i = listElements.iterator();
			Iterator<Element> i2, i3;
			Element enfant;
			LigneRecord ligne = dsl.newRecord(LIGNE);
			int terminusDepartArretId, terminusArriveeArretId;

			while (i.hasNext()) {
				courant = (Element) i.next();
				ligne.setId(Integer.valueOf(courant.getAttributeValue("id")));
				ligne.store();
				
				listElementsVoies = courant.getChild("voies").getChildren();
				i2 = listElementsVoies.iterator();
				while (i2.hasNext()) {
					courant = (Element) i2.next();
					
					terminusDepartArretId = Integer.valueOf(courant.getChild("terminusDepart").getChild("Terminus").getAttributeValue("ref"));
					terminus = (TerminusRecord) dsl.select().from(TERMINUS).where(TERMINUS.ID.equal(terminusDepartArretId)).fetchOne();
					if(terminus == null) {
						terminus = dsl.newRecord(TERMINUS);
						terminus.setId(terminusDepartArretId);
						terminus.store();
					}
					
					terminusArriveeArretId = Integer.valueOf(courant.getChild("terminusArrivee").getChild("Terminus").getAttributeValue("ref"));
					terminus = (TerminusRecord) dsl.select().from(TERMINUS).where(TERMINUS.ID.equal(terminusArriveeArretId)).fetchOne();
					if(terminus == null) {
						terminus = dsl.newRecord(TERMINUS);
						terminus.setId(terminusArriveeArretId);
						terminus.store();
					}

					VoieRecord voie = dsl.newRecord(VOIE);
					voie.setId(Integer.valueOf(courant.getAttributeValue("id")));
					voie.setDirection(courant.getAttributeValue("direction"));
					voie.setTerminusdepartId(terminusDepartArretId);
					voie.setTerminusarriveeId(terminusArriveeArretId);
					voie.store();

					LigneVoieRecord ligneVoie = dsl.newRecord(LIGNE_VOIE);
					ligneVoie.setId(null);
					ligneVoie.setLigneId(ligne.getId());
					ligneVoie.setVoieId(voie.getId());
					ligneVoie.store();
					
					listElementsArrets = courant.getChild("transitions").getChildren();
					i3 = listElementsArrets.iterator();
					while(i3.hasNext()) {
						enfant = (Element) i3.next();
						VoieTransitionRecord voieTransition = dsl.newRecord(VOIE_TRANSITION);
						voieTransition.setId(null);
						voieTransition.setVoieId(voie.getId());
						voieTransition.setTransitionId(Integer.valueOf(enfant.getAttributeValue("ref")));
						voieTransition.store();
					}
				}
			}

		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		
	}
	
	/* EXPORTATION */

	public String exporterReseau() {
		
		// Création de la racine
		Element reseau = new Element("Reseau");
		Document doc = new Document(reseau);
		
		// Ajout des intervalles
		List<Intervalle> listeIntervalles = stockageService.getIntervalles();
		Element intervalles = new Element("intervalles");
		Element intervalle;
		for(Intervalle i : listeIntervalles) {
			intervalle = new Element("Intervalle");
			intervalle.setAttribute(new Attribute("id", i.getId().toString()));
			intervalle.addContent(new Element("min").setText(i.getMin().toString()));
			intervalle.addContent(new Element("pref").setText(i.getPref().toString()));
			intervalle.addContent(new Element("max").setText(i.getMax().toString()));
			intervalles.addContent(intervalle);
		}
		doc.getRootElement().addContent(intervalles);
		
		// Ajout des arrets
		List<Arret> listeArrets = stockageService.getArrets();
		Element arrets = new Element("arrets");
		Element arret;
		for(Arret a : listeArrets) {
			arret = new Element("Arret");
			arret.setAttribute(new Attribute("id", a.getId().toString()));
			arret.setAttribute(new Attribute("nom", a.getNom().toString()));
			arret.setAttribute(new Attribute("estCommercial", a.getEstcommercial().toString()));
			arret.setAttribute(new Attribute("estLieuEchangeConducteur", a.getEstlieuechangeconducteur().toString()));
			arret.setAttribute(new Attribute("estEntreeDepot", a.getEstentreedepot().toString()));
			arret.setAttribute(new Attribute("estSortieDepot", a.getEstsortiedepot().toString()));
			
			if(a.getEstenfacede() != null) {
				arret.setAttribute(new Attribute("estEnFaceDe", a.getEstenfacede().toString()));
			} else {
				arret.setAttribute(new Attribute("estEnFaceDe", "0"));
			}
			
			if(a.getTempsimmobilisationId() != null) {
				arret.setAttribute(new Attribute("tempsImmobilisation", a.getTempsimmobilisationId().toString()));
			} else {
				arret.setAttribute(new Attribute("tempsImmobilisation", "0"));
			}
			
			arrets.addContent(arret);
		}
		doc.getRootElement().addContent(arrets);
		
		// Ajout des dépôts
		List<alize.commun.modele.Depot> listeDepots = stockageService.getDepots();
		Element depots = new Element("depots");
		Element depot;
		for(Depot d : listeDepots) {
			depot = new Element("Depot");
			depot.setAttribute(new Attribute("ref", d.getId().toString()));
			depots.addContent(depot);
		}
		doc.getRootElement().addContent(depots);
		
		// Ajout des terminus
		List<alize.commun.modele.Terminus> listeTerminus = stockageService.getTerminus();
		Element terminus = new Element("terminus");
		Element term;
		for(Terminus t : listeTerminus) {
			term = new Element("Terminus");
			term.setAttribute(new Attribute("ref",t.getId().toString()));
			terminus.addContent(term);
		}
		doc.getRootElement().addContent(terminus);
		
		// Ajout des zones de croisement
		List<ZoneDeCroisement> listeZonesDeCroisement = stockageService.getZonesDeCroisement();
		Element zonesDeCroisement = new Element("zonesdecroisement");
		Element zoneDeCroisement;
		for(Zonedecroisement zdc : listeZonesDeCroisement) {
			zoneDeCroisement = new Element("Zonedecroisement");
			zoneDeCroisement.setAttribute("id", zdc.getId().toString());
			zoneDeCroisement.setAttribute("nom", zdc.getNom());
			zonesDeCroisement.addContent(zoneDeCroisement);
		}
		doc.getRootElement().addContent(zonesDeCroisement);
		
		// Ajout des transitions
		List<Transition> listeTransitions = stockageService.getTransitions();
		Element transitions = new Element("transitions");
		Element transition;
		for(Transition t : listeTransitions) {
			transition = new Element("Transition");
			transition.setAttribute(new Attribute("id", t.getId().toString()));
			transition.setAttribute(new Attribute("duree", t.getDuree().toString()));

			// Ajout de l'arret précédent
			Element arretPrecedent = new Element("arretPrecedent");
			Element arretP = new Element("Arret");
			if(t.getArretprecedentId() != null) {
				arretP.setAttribute(new Attribute("ref", t.getArretprecedentId().toString()));
			} else {
				arretP.setAttribute(new Attribute("ref", "0"));
			}
			arretPrecedent.addContent(arretP);
			transition.addContent(arretPrecedent);
			
			// Ajout du terminus de départ
			Element arretSuivant = new Element("arretSuivant");
			Element arretS = new Element("Arret");
			if(t.getArretprecedentId() != null) {
				arretS.setAttribute(new Attribute("ref", t.getArretsuivantId().toString()));
			} else {
				arretS.setAttribute(new Attribute("ref", "0"));
			}
			arretSuivant.addContent(arretS);
			transition.addContent(arretSuivant);
			
			// Ajout de la zone de croisement
			zoneDeCroisement = new Element("zoneDeCroisement");
			Element zdc = new Element("Zonedecroisement");
			if(t.getZonedecroisementId() != null) {
				zdc.setAttribute(new Attribute("ref", t.getZonedecroisementId().toString()));
			} else {
				zdc.setAttribute(new Attribute("ref", "0"));
			}
			zoneDeCroisement.addContent(zdc);
			transition.addContent(zoneDeCroisement);
			
			transitions.addContent(transition);
		}
		doc.getRootElement().addContent(transitions);
		
		// Ajout des lignes
		List<Ligne> listeLignes = stockageService.getLignes();
		Element lignes = new Element("lignes");
		Element ligne;
		for(Ligne l : listeLignes) {
			ligne = new Element("Ligne");
			ligne.setAttribute(new Attribute("id",l.getId().toString()));
			
			// Ajout des voies
			List<Voie> listeVoies = stockageService.getVoiesPourLaLigne(l.getId());
			Element voies = new Element("voies");
			Element voie;
			for(Voie v : listeVoies) {
				voie = new Element("Voie");
				voie.setAttribute(new Attribute("id",v.getId().toString()));
				voie.setAttribute(new Attribute("direction",v.getDirection().toString()));
				
				// Ajout des arrets
				List<Transition> listeVoieTransitions = stockageService.getTransitionsAttribuees(v.getId());
				Element voieTransitions = new Element("transitions");
				Element voieTransition;
				for(Transition t : listeVoieTransitions) {
					voieTransition = new Element("Transition");
					voieTransition.setAttribute(new Attribute("ref", t.getId().toString()));
					voieTransitions.addContent(voieTransition);
				}
				voie.addContent(voieTransitions);

				// Ajout du terminus de départ
				Element terminusDepart = new Element("terminusDepart");
				Element terminusD = new Element("Terminus");
				terminusD.setAttribute(new Attribute("ref", v.getTerminusdepartId().toString()));
				terminusDepart.addContent(terminusD);
				voie.addContent(terminusDepart);

				// Ajout du terminus de départ
				Element terminusArrivee = new Element("terminusArrivee");
				Element terminusA = new Element("Terminus");
				terminusA.setAttribute(new Attribute("ref", v.getTerminusdepartId().toString()));
				terminusArrivee.addContent(terminusA);
				voie.addContent(terminusArrivee);
				
				voies.addContent(voie);
			}
			ligne.addContent(voies);
			
			lignes.addContent(ligne);
		}
		doc.getRootElement().addContent(lignes);
		
		// Génération du fichier
		XMLOutputter xmlOutput = new XMLOutputter();
		xmlOutput.setFormat(Format.getPrettyFormat());
		String chemin = RACINE + servletContext.getContextPath().substring(1);
		
		File f = new File(chemin + File.separator + "resources" + File.separator + "output.xml");
		
		try {
			f.getParentFile().mkdirs();
			f.createNewFile();
			xmlOutput.output(doc, new FileWriter(f));
		} catch (IOException e) {
			e.printStackTrace();
		}
        
		return "resources/output.xml";
	}

}
