package alize.commun.service;

import static alize.commun.modele.Tables.*;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jooq.DSLContext;
import org.jooq.Record2;
import org.jooq.Record4;
import org.jooq.Record6;
import org.jooq.Record7;
import org.jooq.Result;
import org.jooq.exception.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;

import alize.commun.modele.*;
import alize.commun.modele.tables.pojos.Conducteur;
import alize.commun.modele.tables.pojos.Feuilledeservice;
import alize.commun.modele.tables.pojos.Intervalle;
import alize.commun.modele.tables.pojos.Depot;
import alize.commun.modele.tables.pojos.Ligne;
import alize.commun.modele.tables.pojos.Periodicite;
import alize.commun.modele.tables.pojos.Service;
import alize.commun.modele.tables.pojos.Terminus;
import alize.commun.modele.tables.pojos.Vacation;
import alize.commun.modele.tables.pojos.Vehicule;
import alize.commun.modele.tables.records.ArretRecord;
import alize.commun.modele.tables.records.AssociationconducteurserviceRecord;
import alize.commun.modele.tables.records.ConducteurRecord;
import alize.commun.modele.tables.records.DepotRecord;
import alize.commun.modele.tables.records.FeuilledeservicePeriodiciteRecord;
import alize.commun.modele.tables.records.FeuilledeserviceRecord;
import alize.commun.modele.tables.records.IntervalleRecord;
import alize.commun.modele.tables.records.LigneRecord;
import alize.commun.modele.tables.records.LigneVoieRecord;
import alize.commun.modele.tables.records.PeriodiciteRecord;
import alize.commun.modele.tables.records.ServiceRecord;
import alize.commun.modele.tables.records.TerminusRecord;
import alize.commun.modele.tables.records.TransitionRecord;
import alize.commun.modele.tables.records.VacationRecord;
import alize.commun.modele.tables.records.VehiculeRecord;
import alize.commun.modele.tables.records.VoieTransitionRecord;
import alize.commun.modele.tables.records.VoieRecord;
import alize.commun.util.ListArret;

public class StockageServiceImpl implements StockageService {

	public static final SimpleDateFormat PERIODE_FORMAT = new SimpleDateFormat("hh:mm:ss");
	
	@Autowired
	private DSLContext dsl;
	
	/* GESTION DES LIGNES */
	
	@Override
	public List<Ligne> getLignes() {
		
		Ligne ligne;
		List<Ligne> lignes = new ArrayList<Ligne>();
		
		Result<LigneRecord> results = dsl.fetch(LIGNE);
		for (LigneRecord l : results) {
			ligne = new Ligne();
			ligne.setId(l.getId());
			ligne.setTypevehicule(l.getTypevehicule());
			lignes.add(ligne);
		}
		
		return lignes;
	}
	
	@Override
	public void updateLigne(int id, String colonne, Object valeur) {
		LigneRecord ligneRecord = dsl.fetchOne(LIGNE, LIGNE.ID.equal(id));
		
		if(colonne.compareTo("typeVehicule") == 0) {
			ligneRecord.setTypevehicule(valeur.toString());
		}
		
		ligneRecord.store();
	}

	@Override
	public void ajouterLigne() {
		LigneRecord ligneRecord = dsl.newRecord(LIGNE);
		ligneRecord.setId(null);
		ligneRecord.setTypevehicule("");
		ligneRecord.store();
	}

	@Override
	public void supprimerLigne(int id) {
		dsl.delete(LIGNE)
		.where(LIGNE.ID.equal(id))
		.execute();
	}

	/* ATTRIBUTION DES VOIES AUX LIGNES */	
	
	@Override
	public Map<Voie, String> getVoiesNonAttribuees(int idLigne) {
		Map<Voie, String> voies = new HashMap<Voie, String>();
		Voie voie;
		String terminus;
		
		Result<Record4<Integer, String, String, String>> results =
				dsl.selectDistinct(VOIE.ID, VOIE.DIRECTION, ARRET.as("arretdepart").NOM, ARRET.as("arretarrivee").NOM)
				.from(VOIE, LIGNE_VOIE, ARRET.as("arretdepart"), ARRET.as("arretarrivee"))
				.where(
						LIGNE_VOIE.VOIE_ID.equal(VOIE.ID)
						.or(VOIE.ID.notIn(
							dsl.select(LIGNE_VOIE.VOIE_ID)
							.from(LIGNE_VOIE)
							)
						))
				.and(VOIE.TERMINUSDEPART_ID.equal(ARRET.as("arretdepart").ID))
				.and(VOIE.TERMINUSARRIVEE_ID.equal(ARRET.as("arretarrivee").ID))
				.and(VOIE.ID.notIn(dsl.select(LIGNE_VOIE.VOIE_ID)
						.from(LIGNE_VOIE)
						.where(LIGNE_VOIE.LIGNE_ID.equal(idLigne)))
						)
				.fetch();
		
		for(Record4<Integer, String, String, String> v : results) {
			voie = new Voie();
			voie.setId(v.getValue(VOIE.ID));
			voie.setDirection(v.getValue(VOIE.DIRECTION));
			terminus = v.getValue(ARRET.as("arretdepart").NOM) + " -> " + v.getValue(ARRET.as("arretarrivee").NOM);
			voies.put(voie, terminus);
		}
		
		return voies;
	}

	@Override
	public Map<Voie, String> getVoiesAttribuees(int idLigne) {
		Map<Voie, String> voies = new HashMap<Voie, String>();
		Voie voie;
		String terminus;
		
		Result<Record4<Integer, String, String, String>> results =
				dsl.select(VOIE.ID, VOIE.DIRECTION, ARRET.as("arretdepart").NOM, ARRET.as("arretarrivee").NOM)
				.from(VOIE, LIGNE_VOIE, ARRET.as("arretdepart"), ARRET.as("arretarrivee"))
				.where(LIGNE_VOIE.VOIE_ID.equal(VOIE.ID))
				.and(VOIE.TERMINUSDEPART_ID.equal(ARRET.as("arretdepart").ID))
				.and(VOIE.TERMINUSARRIVEE_ID.equal(ARRET.as("arretarrivee").ID))
				.and(LIGNE_VOIE.LIGNE_ID.equal(idLigne))
				.fetch();
		
		for(Record4<Integer, String, String, String> v : results) {
			voie = new Voie();
			voie.setId(v.getValue(VOIE.ID));
			voie.setDirection(v.getValue(VOIE.DIRECTION));
			terminus = v.getValue(ARRET.as("arretdepart").NOM) + " -> " + v.getValue(ARRET.as("arretarrivee").NOM);
			voies.put(voie, terminus);
		}
		
		return voies;
	}
	

	public void ajouterLigneVoie(int idVoie, int idLigne) {
		LigneVoieRecord ligneVoieRecord = dsl.newRecord(LIGNE_VOIE);
		ligneVoieRecord.setId(null);
		ligneVoieRecord.setLigneId(idLigne);
		ligneVoieRecord.setVoieId(idVoie);
		ligneVoieRecord.store();
	}

	public void supprimerLigneVoie(int idVoie, int idLigne) {
		dsl.delete(LIGNE_VOIE)
		.where(LIGNE_VOIE.VOIE_ID.equal(idVoie))
		.and(LIGNE_VOIE.LIGNE_ID.equal(idLigne))
		.execute();
	}
	
	/* GESTION DES VOIES */
	
	@Override
	public List<Voie> getVoies() {
		
		Voie voie;
		List<Voie> voies = new ArrayList<Voie>();
		
		Result<VoieRecord> results = dsl.fetch(VOIE);
		for (VoieRecord v : results) {
			voie = new Voie();
			voie.setId(v.getId());
			voie.setDirection(v.getDirection());
			voie.setTerminusarriveeId(v.getTerminusarriveeId());
			voie.setTerminusdepartId(v.getTerminusdepartId());
			voies.add(voie);
		}
		
		return voies;
	}
	
	@Override
	public Voie getVoie(int id) {
		
		Voie voie;
		List<Voie> voies = new ArrayList<Voie>();
		Result<Record6<Integer,String, Integer, Integer, Integer, Integer>> results = dsl.select(VOIE.ID, VOIE.DIRECTION, VOIE.TERMINUSDEPART_ID, VOIE.TERMINUSARRIVEE_ID, LIGNE_VOIE.VOIE_ID, LIGNE_VOIE.LIGNE_ID)
									.from(VOIE)
									.where(VOIE.ID.equal(id))
									.fetch();
		Record6<Integer,String, Integer, Integer, Integer, Integer> v = results.get(0);
			voie = new Voie();
			voie.setId(v.value1());
			voie.setDirection(v.value2());
			voie.setTerminusdepartId(v.value3());
			voie.setTerminusarriveeId(v.value4());
		return voie;
	}
	
	public List<Voie> getVoiesPourLaLigne(int idLigne)
	{
		Voie voie;
		List<Voie> voies = new ArrayList<Voie>();
		
		Result<Record6<Integer,String, Integer, Integer, Integer, Integer>> results = 
				dsl.select(VOIE.ID, VOIE.DIRECTION, VOIE.TERMINUSDEPART_ID, VOIE.TERMINUSARRIVEE_ID, LIGNE_VOIE.VOIE_ID, LIGNE_VOIE.LIGNE_ID)
				.from(VOIE)
				.join(LIGNE_VOIE)
				.on(VOIE.ID.equal(LIGNE_VOIE.VOIE_ID))
				.where(LIGNE_VOIE.LIGNE_ID.equal(idLigne))
				.fetch();
		
		for (Record6<Integer,String, Integer, Integer, Integer, Integer> v : results) {
			voie = new Voie();
			voie.setId(v.getValue(VOIE.ID));
			voie.setDirection(v.getValue(VOIE.DIRECTION));
			voie.setTerminusarriveeId(v.getValue(VOIE.TERMINUSARRIVEE_ID));
			voie.setTerminusdepartId(v.getValue(VOIE.TERMINUSDEPART_ID));
			voies.add(voie);
		}
		
		return voies;
	}
	
	@Override
	public Voie getVoie(int idVoie) {
		Voie voie = new Voie();
		VoieRecord voieRecord = dsl.fetchOne(VOIE, VOIE.ID.equal(idVoie));
		
		voie.setId(idVoie);
		voie.setDirection(voieRecord.getDirection());
		voie.setTerminusdepartId(voieRecord.getTerminusdepartId());
		voie.setTerminusarriveeId(voieRecord.getTerminusarriveeId());
		
		return voie;
	}
	
	@Override
	public void updateVoie(int id, String colonne, Object valeur) {
		VoieRecord voieRecord = dsl.fetchOne(VOIE, VOIE.ID.equal(id));
		
		if(colonne.compareTo("direction") == 0) {
			voieRecord.setDirection(valeur.toString());
		} else if(colonne.compareTo("terminusDepart_id") == 0) {
			voieRecord.setTerminusdepartId(Integer.valueOf(valeur.toString()));
		} else if(colonne.compareTo("terminusArrivee_id") == 0) {
			voieRecord.setTerminusarriveeId(Integer.valueOf(valeur.toString()));
		}
		
		voieRecord.store();
	}

	@Override
	public void ajouterVoie() {
		VoieRecord voieRecord = dsl.newRecord(VOIE);
		voieRecord.setId(null);
		voieRecord.setDirection("");
		voieRecord.store();
	}

	@Override
	public void supprimerVoie(int id) {
		dsl.delete(VOIE)
		.where(VOIE.ID.equal(id))
		.execute();
	}
	

	/* ATTRIBUTION DES TRANSITIONS AUX VOIES */

	@Override
	public Map<Transition, String> getTransitionsNonAttribuees(int idVoie) {
		Map<Transition, String> transitions = new HashMap<Transition, String>();
		Transition transition;
		String status = "";
		
		Result<Record4<Integer, Time, String, String>> results =
				dsl.selectDistinct(TRANSITION.ID, TRANSITION.DUREE, ARRET.as("arretprecedent").NOM, ARRET.as("arretsuivant").NOM)
				.from(TRANSITION, ARRET.as("arretprecedent"), ARRET.as("arretsuivant"))
				.where(TRANSITION.ARRETPRECEDENT_ID.equal(ARRET.as("arretprecedent").ID))
				.and(TRANSITION.ARRETSUIVANT_ID.equal(ARRET.as("arretsuivant").ID))
				.and(TRANSITION.ID.notIn(
						dsl.select(VOIE_TRANSITION.TRANSITION_ID)
						.from(VOIE_TRANSITION)
						.where(VOIE_TRANSITION.VOIE_ID.equal(idVoie))
						))
				.fetch();
		
		for(Record4<Integer, Time, String, String> r : results) {
			transition = new Transition();
			transition.setId(r.getValue(TRANSITION.ID));
			transition.setDuree(r.getValue(TRANSITION.DUREE));
			
			status = r.getValue(ARRET.as("arretprecedent").NOM) + "-> " + r.getValue(ARRET.as("arretsuivant").NOM);
			
			transitions.put(transition, status);
		}
		
		return transitions;
	}

	@Override
	public List<Transition> getTransitionsAttribuees(int idVoie) {
		List<Transition> transitions = new ArrayList<Transition>();
		Transition transition;
		
		Result<Record6<Integer, Time, Integer, Integer, String, String>> results =
				dsl.selectDistinct(TRANSITION.ID, TRANSITION.DUREE, TRANSITION.ARRETPRECEDENT_ID, TRANSITION.ARRETSUIVANT_ID, ARRET.as("arretprecedent").NOM, ARRET.as("arretsuivant").NOM)
				.from(TRANSITION, VOIE_TRANSITION, ARRET.as("arretprecedent"), ARRET.as("arretsuivant"))
				.where(TRANSITION.ARRETPRECEDENT_ID.equal(ARRET.as("arretprecedent").ID))
				.and(TRANSITION.ARRETSUIVANT_ID.equal(ARRET.as("arretsuivant").ID))
				.and(VOIE_TRANSITION.VOIE_ID.equal(idVoie))
				.and(VOIE_TRANSITION.TRANSITION_ID.equal(TRANSITION.ID))
				.fetch();
		
		for(Record6<Integer, Time, Integer, Integer, String, String> r : results) {
			transition = new Transition();
			transition.setId(r.getValue(TRANSITION.ID));
			transition.setDuree(r.getValue(TRANSITION.DUREE));
			transition.setArretprecedentId(r.getValue(TRANSITION.ARRETPRECEDENT_ID));
			transition.setArretsuivantId(r.getValue(TRANSITION.ARRETSUIVANT_ID));
			
			transition.getArretPrecedent().setId(r.getValue(TRANSITION.ARRETPRECEDENT_ID));
			transition.getArretPrecedent().setNom(r.getValue(ARRET.as("arretprecedent").NOM));
			
			transition.getArretSuivant().setId(r.getValue(TRANSITION.ARRETSUIVANT_ID));
			transition.getArretSuivant().setNom(r.getValue(ARRET.as("arretsuivant").NOM));
			
			transitions.add(transition);
		}
		
		return transitions;
	}

	@Override
	public void ajouterVoieTransition(int idVoie, int idTransition) {
		VoieTransitionRecord r = dsl.newRecord(VOIE_TRANSITION);
		r.setId(null);
		r.setVoieId(idVoie);
		r.setTransitionId(idTransition);
		r.store();
	}

	@Override
	public void supprimerVoieTransition(int idVoie, int idTransition) {
		dsl.delete(VOIE_TRANSITION)
		.where(VOIE_TRANSITION.VOIE_ID.equal(idVoie))
		.and(VOIE_TRANSITION.TRANSITION_ID.equal(idTransition))
		.execute();
	}
	
	/* GESTION DES ARRETS */

	@Override
	public List<Arret> getArrets() {

		Arret arret;
		List<Arret> arrets = new ArrayList<Arret>();
		
		Result<ArretRecord> results = dsl.fetch(ARRET);
		for (ArretRecord a : results) {
			arret = new Arret();
			arret.setId(a.getId());
			arret.setEstcommercial(a.getEstcommercial());
			arret.setEstentreedepot(a.getEstentreedepot());
			arret.setEstsortiedepot(a.getEstsortiedepot());
			arret.setTempsimmobilisationId(a.getTempsimmobilisationId());
			arret.setEstlieuechangeconducteur(a.getEstlieuechangeconducteur());
			arret.setNom(a.getNom());
			arret.setTempsimmobilisationId(a.getTempsimmobilisationId());
			arrets.add(arret);
		}
		
		return arrets;
	}
	
	@Override

	public Arret getArret(int id) {
		Arret arret = new Arret();
		Result<Record6<Integer, String, Byte, Byte, Byte, Byte>> results = 
				dsl.select(ARRET.ID, ARRET.NOM, ARRET.ESTCOMMERCIAL, ARRET.ESTENTREEDEPOT, ARRET.ESTLIEUECHANGECONDUCTEUR, ARRET.ESTSORTIEDEPOT)
				.from(ARRET)
				.where(ARRET.ID.equal(id))
				.fetch();
		Record6<Integer, String, Byte, Byte,  Byte, Byte> result = results.get(0);
		arret.setId(result.getValue(ARRET.ID));
		arret.setNom(result.getValue(ARRET.NOM));
		arret.setEstcommercial(result.getValue(ARRET.ESTCOMMERCIAL));
		arret.setEstentreedepot(result.getValue(ARRET.ESTENTREEDEPOT));
		arret.setEstlieuechangeconducteur(result.getValue(ARRET.ESTLIEUECHANGECONDUCTEUR));
		arret.setEstsortiedepot(result.getValue(ARRET.ESTSORTIEDEPOT));
		return arret;
	}
	
	public List<Arret> getArretsEchangesConducteurs() {
		Arret arret;
		List<Arret> arrets = new ArrayList<Arret>();
		
		Result<Record2<Integer, String>> results =
				dsl.selectDistinct(ARRET.ID, ARRET.NOM)
				.from(ARRET)
				.where(ARRET.ESTLIEUECHANGECONDUCTEUR.equal((byte) 1))
				.fetch();
		
		for (Record2<Integer, String> a : results) {
			arret = new Arret();
			arret.setId(a.getValue(ARRET.ID));
			arret.setNom(a.getValue(ARRET.NOM));
			arrets.add(arret);
		}
		
		return arrets;
	}

	@Override
	public List<Depot> getDepots() {
		
		Depot depot;
		List<Depot> depots = new ArrayList<Depot>();
		
		Result<DepotRecord> results = dsl.fetch(DEPOT);
		for (DepotRecord d : results) {
			depot = new Depot();
			depot.setId(d.getId());
			depot.setArretId(d.getArretId());
			depots.add(depot);
			
		}
		return depots;
	}

	@Override
	public List<Terminus> getTerminus() {
		
		Terminus terminus;
		List<Terminus> listeTerminus = new ArrayList<Terminus>();
		
		Result<TerminusRecord> results = dsl.fetch(TERMINUS);
		for (TerminusRecord t : results) {
			terminus = new Terminus();
			terminus.setId(t.getId());
			terminus.setArretId(t.getArretId());
			listeTerminus.add(terminus);
		}
		
		return listeTerminus;
	}
	@Override
	public List<Arret> getArretsPourLaVoie(int idVoie) {

		Voie v = getVoie(idVoie);
		List<Transition> transitions = getTransitionsAttribuees(idVoie);
		List<Arret> arrets = new ArrayList<Arret>();
		
		boolean trouve = false;
		int k = 0;
		int nTransitionsTotal = transitions.size();

		// Recherche du terminus de départ
		while(k < nTransitionsTotal && !trouve) {
			if(transitions.get(k).getArretprecedentId() == v.getTerminusdepartId()) {
				trouve = true;
			}
			k++;
		}
		
		if(trouve) {
			arrets.add(transitions.get(k-1).getArretPrecedent());
			arrets.add(transitions.get(k-1).getArretSuivant());
			transitions.remove(k-1);
			nTransitionsTotal--;
		
			int kArrets = 1;
			while(nTransitionsTotal > 0) {
				
				trouve = false;
				k = 0;

				// Recherche de l'arret suivant
				while(!trouve && k < nTransitionsTotal) {
					if(transitions.get(k).getArretprecedentId() == arrets.get(kArrets).getId()) {
						trouve = true;
					}
					k++;
				}
				
				if(trouve) {
					arrets.add(transitions.get(k-1).getArretSuivant());
					transitions.remove(k-1);
					nTransitionsTotal--;
					kArrets++;
				}
				
			}
		}
		
		return arrets;
	}

	@Override
	public void supprimerArret(int id) {
		dsl.delete(ARRET)
		.where(ARRET.ID.equal(id))
		.execute();	
	}
	
	@Override
	public Map<Integer, String> getTerminusVoie(int idVoie) {
		Map<Integer, String> terminus = new LinkedHashMap<Integer, String>();
		Collection<Integer> idArrets = new HashSet<Integer>();
		
		List<Arret> arretsVoie = getArretsPourLaVoie(idVoie);
		for(Arret a : arretsVoie) {
			idArrets.add(a.getId());
		}
		
		Result<Record2<Integer, String>> results =
				dsl.select(TERMINUS.ARRET_ID, ARRET.NOM)
				.from(TERMINUS)
				.join(ARRET).on(TERMINUS.ARRET_ID.equal(ARRET.ID))
				.where(TERMINUS.ARRET_ID.in(idArrets))
				.fetch();
		
		for(Record2<Integer, String> t : results) {
			terminus.put(t.getValue(TERMINUS.ARRET_ID), t.getValue(ARRET.NOM));
		}
		
		return terminus;
	}
	
	@Override
	public void updateArret(int id, String colonne, Object valeur) {
		ArretRecord arret = dsl.fetchOne(ARRET, ARRET.ID.equal(id));
		
		String s = (String) valeur;
		if(colonne.compareTo("nom") == 0) {
			arret.setNom(valeur.toString());
		} else if(colonne.compareTo("estCommercial") == 0) {
			arret.setEstcommercial(new Byte(s));
		} else if(colonne.compareTo("estEntree") == 0) {
			arret.setEstentreedepot(new Byte(s)); 
		}else if(colonne.compareTo("estSortie") == 0) {
			arret.setEstsortiedepot(new Byte(s)); 
		}else if(colonne.compareTo("estLieuEchangeConducteur") == 0) {
			arret.setEstlieuechangeconducteur(new Byte(s));
		}else if(colonne.compareTo("estTerminus") == 0) {
			if(s.compareTo("1")==0){
				ajouterTerminus(id);
			}else{
				supprimerTerminus(id);
			}
		}else if(colonne.compareTo("estDepot") == 0) {
			if(s.compareTo("1")==0){
				ajouterDepot(id);
			}else{
				supprimerDepot(id);
			}
		}
		arret.store();
	}
	

	@Override
	public void ajouterArret() {
		try {
			SimpleDateFormat formater = new SimpleDateFormat("hh:mm:ss");
			Date date;
			
			date = formater.parse("00:00:00");
			
			java.sql.Time time = new Time(date.getTime());
			
			IntervalleRecord intervalleRecord = dsl.newRecord(INTERVALLE);
			intervalleRecord.setId(null);
			intervalleRecord.setMin(time);
			intervalleRecord.setPref(time);
			intervalleRecord.setMax(time);
			intervalleRecord.store();
			
			ArretRecord arretRecord = dsl.newRecord(ARRET);
			arretRecord.setId(null);
			arretRecord.setEstcommercial(new Byte("0"));
			arretRecord.setEstentreedepot(new Byte("0"));
			arretRecord.setEstsortiedepot(new Byte("0"));
			arretRecord.setEstlieuechangeconducteur(new Byte("0"));
			arretRecord.setNom("");
			arretRecord.setTempsimmobilisationId(intervalleRecord.getId());
			arretRecord.store();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	
	@Override
	public void updateTempsImmobilisationArret(int id, String colonne, Object valeur) {
		IntervalleRecord intervalle = dsl.fetchOne(INTERVALLE, INTERVALLE.ID.equal(id));
		
		SimpleDateFormat formater = new SimpleDateFormat("hh:mm:ss");
		Date date;
		try {
			date = formater.parse((String)valeur);
			java.sql.Time time = new Time(date.getTime());
			if(colonne.compareTo("tpsMIN_"+id) == 0) {
				intervalle.setMin(time); 
			} else if(colonne.compareTo("tpsPREF_" + id) == 0) {
				intervalle.setPref(time); 
			} else if(colonne.compareTo("tpsMAX_" + id) == 0) {
				intervalle.setMax(time); 
			}
			intervalle.store();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	/* GESTION DES TEMPS D IMMOBILISATION*/
	
	@Override
	public List<Intervalle> getTempsImmobilisation() {

		Intervalle intervalle;
		List<Intervalle> tempsImmobilisation = new ArrayList<Intervalle>();
		
		Result<IntervalleRecord> results = dsl.fetch(INTERVALLE);
		for (IntervalleRecord i : results) {
			intervalle = new Intervalle();
			intervalle.setId(i.getId());
			intervalle.setMax(i.getMax());
			intervalle.setPref(i.getPref());
			intervalle.setMin(i.getMin());
			tempsImmobilisation.add(intervalle);
		}
		return tempsImmobilisation;
	}
	
	
	
	@Override
	public Intervalle getTempsImmobilisation(int idTempsImmobilisation) {

		Intervalle intervalle = new Intervalle();
		
		Result<Record4<Integer, Time, Time, Time>> results =	dsl.select(INTERVALLE.ID, INTERVALLE.MAX, INTERVALLE.PREF, INTERVALLE.MIN)
									.from(INTERVALLE)
									.where(INTERVALLE.ID.equal(idTempsImmobilisation))
									.fetch();
		Record4<Integer, Time, Time, Time> i = results.get(0);
		intervalle.setId(i.getValue(INTERVALLE.ID));
		intervalle.setMax(i.getValue(INTERVALLE.MAX));
		intervalle.setPref(i.getValue(INTERVALLE.PREF));
		intervalle.setMin(i.getValue(INTERVALLE.MIN));
		return intervalle;
	}
	
	/* GESTION DES TRANSITIONS */

	@Override
	public List<Transition> getTransitions() {
		Transition transition;
		List<Transition> transitions = new ArrayList<Transition>();
		
		Result<TransitionRecord> results = dsl.fetch(TRANSITION);
		for (TransitionRecord t : results) {
			transition = new Transition();
			transition.setId(t.getId());
			transition.setDuree(t.getDuree());
			transition.setArretprecedentId(t.getArretprecedentId());
			transition.setArretsuivantId(t.getArretsuivantId());
			transitions.add(transition);
		}
		
		return transitions;
	}

	
	@Override
	public Transition getTransition(int idArretPrecedent) {
		TransitionRecord transitionRecord = dsl.fetchOne(TRANSITION, TRANSITION.ARRETPRECEDENT_ID.equal(idArretPrecedent));
		
		Transition transition = new Transition();
		transition.setId(transitionRecord.getId());
		transition.setDuree(transitionRecord.getDuree());
		transition.setArretprecedentId(transitionRecord.getArretprecedentId());
		transition.setArretsuivantId(transitionRecord.getArretsuivantId());
		
		return transition;
	}
	
	@Override
	public void updateTransition(int id, String colname, String newvalue) {
		TransitionRecord transitionRecord = dsl.fetchOne(TRANSITION, TRANSITION.ID.equal(id));
		
		if(colname.compareTo("duree") == 0) {
			Time valeur;
			try {
				valeur = new Time(PERIODE_FORMAT.parse(newvalue).getTime());
				transitionRecord.setDuree(valeur);
			} catch (ParseException e) {
			}
		} else if(colname.compareTo("arretPrecedent_id") == 0) {
			transitionRecord.setArretprecedentId(Integer.valueOf(newvalue));
		} else if(colname.compareTo("arretSuivant_id") == 0) {
			transitionRecord.setArretsuivantId(Integer.valueOf(newvalue));
		}
		
		transitionRecord.store();
	}

	@Override
	public void ajouterTransition() {
		TransitionRecord transitionRecord = dsl.newRecord(TRANSITION);
		transitionRecord.setId(null);
		transitionRecord.store();
	}

	@Override
	public void supprimerTransition(int id) {
		dsl.delete(TRANSITION)
		.where(TRANSITION.ID.equal(id))
		.execute();
	}
	
	/* GESTION DES PERIODICITES */
	
	@Override
	public List<Periodicite> getPeriodicites() {

		Periodicite periodicite;
		List<Periodicite> periodicites = new ArrayList<Periodicite>();
		
		Result<PeriodiciteRecord> results = dsl.fetch(PERIODICITE);
		for (PeriodiciteRecord p : results) {
			periodicite = new Periodicite();
			periodicite.setId(p.getId());
			periodicite.setDebut(p.getDebut());
			periodicite.setFin(p.getFin());
			periodicite.setPeriode(p.getPeriode());
			periodicite.setIdArret(p.getIdArret());
			periodicite.setIdVoie(p.getIdArret());
			periodicites.add(periodicite);
		}
		
		return periodicites;
	}

	@Override
	public List<Periodicite> getPeriodicites(int idVoie, int idArret) {

		Periodicite periodicite;
		List<Periodicite> periodicites = new ArrayList<Periodicite>();
		
		Result<Record6<Integer, Time, Time, Time, Integer, Integer>> results = dsl.select(PERIODICITE.ID, PERIODICITE.DEBUT, PERIODICITE.FIN, PERIODICITE.PERIODE, PERIODICITE.ID_VOIE, PERIODICITE.ID_ARRET)
				.from(PERIODICITE)
				.where(PERIODICITE.ID_VOIE.equal(idVoie))
				.and(PERIODICITE.ID_ARRET.equal(idArret))
				.fetch();
		
		
		for (Record6<Integer, Time, Time, Time, Integer, Integer> p : results) {
			periodicite = new Periodicite();
			periodicite.setId(p.getValue(PERIODICITE.ID));
			periodicite.setDebut(p.getValue(PERIODICITE.DEBUT));
			periodicite.setFin(p.getValue(PERIODICITE.FIN));
			periodicite.setPeriode(p.getValue(PERIODICITE.PERIODE));
			periodicite.setIdVoie(p.getValue(PERIODICITE.ID_VOIE));
			periodicite.setIdArret(p.getValue(PERIODICITE.ID_ARRET));
			periodicites.add(periodicite);
		}
		
		return periodicites;
	}

	@Override
	public void ajouterPeriodicite(int idVoie, int idArret) {
		PeriodiciteRecord periodiciteRecord = dsl.newRecord(PERIODICITE);
		periodiciteRecord.setId(null);
		periodiciteRecord.setIdVoie(idVoie);
		periodiciteRecord.setIdArret(idArret);
		periodiciteRecord.store();
	}

	@Override
	public void supprimerPeriodicite(int id) {
		dsl.delete(PERIODICITE)
		.where(PERIODICITE.ID.equal(id))
		.execute();
	}
	@Override
	public void updatePeriodicite(int id, String colonne, Time valeur) {

		PeriodiciteRecord periodiciteRecord = dsl.fetchOne(PERIODICITE, PERIODICITE.ID.equal(id));
		
		if(colonne.compareTo("debut") == 0) {
			periodiciteRecord.setDebut(valeur);
		} else if(colonne.compareTo("fin") == 0) {
			periodiciteRecord.setFin(valeur);
		} else if(colonne.compareTo("periode") == 0) {
			periodiciteRecord.setPeriode(valeur);
		}

		periodiciteRecord.store();
	}
	
	/* GESTION DES INTERVALLES */
	
	@Override
	public List<Intervalle> getIntervalles() {

		Intervalle intervalle;
		List<Intervalle> intervalles = new ArrayList<Intervalle>();
		
		Result<IntervalleRecord> results = dsl.fetch(INTERVALLE);
		for (IntervalleRecord i : results) {
			intervalle = new Intervalle();
			intervalle.setId(i.getId());
			intervalle.setMin(i.getMin());
			intervalle.setPref(i.getPref());
			intervalle.setMax(i.getMax());
			intervalles.add(intervalle);
		}
		return intervalles;
	}
	
	/* GESTION DES TERMINUS */
	
	@Override
	public Terminus getTerminus(int id) {
		Terminus t = new Terminus();
		Result<Record2<Integer, Integer>> results = dsl.select(TERMINUS.ID, TERMINUS.ARRET_ID)
				.from(TERMINUS)
				.where(TERMINUS.ARRET_ID.equal(id))
				.fetch();
		Record2<Integer, Integer> result = results.get(0);
		t.setId(result.value1());
		t.setArretId(result.value2());
		
		return t;
	}
	
	
	@Override
	public void ajouterTerminus(int idArret) {
		TerminusRecord terminusRecord = dsl.newRecord(TERMINUS);
		terminusRecord.setId(null);
		terminusRecord.setArretId(idArret);
		terminusRecord.store();
	}
	
	@Override
	public boolean getEstTerminus(int idArret) {	
		Result<Record2<Integer, Integer>> results = dsl.select(TERMINUS.ID, TERMINUS.ARRET_ID)
				.from(TERMINUS)
				.where(TERMINUS.ARRET_ID.equal(idArret))
				.fetch();
		return !results.isEmpty();
	}
	
	@Override
	public void supprimerTerminus(int id) {
		dsl.delete(TERMINUS)
		.where(TERMINUS.ARRET_ID.equal(id))
		.execute();
	}
	
	/* GESTION DES DEPOTS */
	
	@Override
	public boolean getEstDepot(int idArret) {	
		Result<Record2<Integer, Integer>> results = dsl.select(DEPOT.ID, DEPOT.ARRET_ID)
				.from(DEPOT)
				.where(DEPOT.ARRET_ID.equal(idArret))
				.fetch();
		return !results.isEmpty();
	}
	
	@Override
	public void ajouterDepot(int idArret) {
		DepotRecord depotRecord = dsl.newRecord(DEPOT);
		depotRecord.setId(null);
		depotRecord.setArretId(idArret);
		depotRecord.store();
	}
	
	@Override
	public void supprimerDepot(int id) {
		dsl.delete(DEPOT)
		.where(DEPOT.ARRET_ID.equal(id))
		.execute();
	}
	
	/* GESTION DES FEUILLES DE SERVICE */

	@Override
	public List<Feuilledeservice> getFDS() {
		Feuilledeservice fds;
		List<Feuilledeservice> fdss = new ArrayList<Feuilledeservice>();
		
		Result<FeuilledeserviceRecord> results = dsl.fetch(FEUILLEDESERVICE);
		
		for (FeuilledeserviceRecord f : results) {
			fds = new Feuilledeservice();
			fds.setId(f.getValue(FEUILLEDESERVICE.ID));
			fds.setCouleur(f.getValue(FEUILLEDESERVICE.COULEUR));
			fds.setDebutsaison(f.getValue(FEUILLEDESERVICE.DEBUTSAISON));
			fds.setFinsaison(f.getValue(FEUILLEDESERVICE.FINSAISON));
			fdss.add(fds);
		}
		
		return fdss;
	}

	@Override
	public void updateFDS(int id, String colname, String newvalue) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		FeuilledeserviceRecord f = dsl.fetchOne(FEUILLEDESERVICE, FEUILLEDESERVICE.ID.equal(id));

		try {
			if(colname.compareTo("couleur") == 0) {
				f.setCouleur(newvalue);
			} else if(colname.compareTo("debutSaison") == 0) {
		        Date parsed = format.parse(newvalue);
		        java.sql.Date sql = new java.sql.Date(parsed.getTime());
				f.setDebutsaison(sql);
			} else if(colname.compareTo("finSaison") == 0) {
		        Date parsed;
					parsed = format.parse(newvalue);
		        java.sql.Date sql = new java.sql.Date(parsed.getTime());
				f.setFinsaison(sql);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		f.store();
	}

	@Override
	public void ajouterFDS() {
		java.sql.Date sqlDate = new java.sql.Date(new Date().getTime());
		
		FeuilledeserviceRecord fdsRecord = dsl.newRecord(FEUILLEDESERVICE);
		fdsRecord.setId(null);
		fdsRecord.setDebutsaison(sqlDate);
		fdsRecord.setFinsaison(sqlDate);
		fdsRecord.store();
	}

	@Override
	public void supprimerFDS(int id) {
		dsl.delete(FEUILLEDESERVICE)
		.where(FEUILLEDESERVICE.ID.equal(id))
		.execute();
	}

	/* ATTRIBUTION DES PERIODICITES AUX FEUILLES DE SERVICE */

	@Override
	public List<Periodicite> getPeriodicitesNonAttribuees(int idFDS) {
		List<Periodicite> periodicites = new ArrayList<Periodicite>();
		Periodicite periodicite;
		
		Result<Record6<Integer, Integer, Integer, Time, Time, Time>> results =
				dsl.selectDistinct(PERIODICITE.ID, PERIODICITE.ID_VOIE, PERIODICITE.ID_ARRET, PERIODICITE.DEBUT, PERIODICITE.FIN, PERIODICITE.PERIODE)
				.from(PERIODICITE)
				.where(PERIODICITE.ID.notIn(dsl.select(FEUILLEDESERVICE_PERIODICITE.PERIODICITE_ID)
						.from(FEUILLEDESERVICE_PERIODICITE)
						.where(FEUILLEDESERVICE_PERIODICITE.FEUILLEDESERVICE_ID.equal(idFDS)))
						)
				.fetch();
		
		for(Record6<Integer, Integer, Integer, Time, Time, Time> v : results) {
			periodicite = new Periodicite();
			periodicite.setId(v.getValue(PERIODICITE.ID));
			periodicite.setIdVoie(v.getValue(PERIODICITE.ID_VOIE));
			periodicite.setIdArret(v.getValue(PERIODICITE.ID_ARRET));
			periodicite.setDebut(v.getValue(PERIODICITE.DEBUT));
			periodicite.setFin(v.getValue(PERIODICITE.FIN));
			periodicite.setPeriode(v.getValue(PERIODICITE.PERIODE));
			periodicites.add(periodicite);
		}
		
		return periodicites;
	}

	@Override
	public List<Periodicite> getPeriodicitesAttribuees(int idFDS) {
		List<Periodicite> periodicites = new ArrayList<Periodicite>();
		Periodicite periodicite;
		
		Result<Record6<Integer, Integer, Integer, Time, Time, Time>> results =
				dsl.select(PERIODICITE.ID, PERIODICITE.ID_VOIE, PERIODICITE.ID_ARRET, PERIODICITE.DEBUT, PERIODICITE.FIN, PERIODICITE.PERIODE)
				.from(PERIODICITE, FEUILLEDESERVICE_PERIODICITE)
				.where(FEUILLEDESERVICE_PERIODICITE.PERIODICITE_ID.equal(PERIODICITE.ID))
				.and(FEUILLEDESERVICE_PERIODICITE.FEUILLEDESERVICE_ID.equal(idFDS))
				.fetch();

		for(Record6<Integer, Integer, Integer, Time, Time, Time> v : results) {
			periodicite = new Periodicite();
			periodicite.setId(v.getValue(PERIODICITE.ID));
			periodicite.setIdVoie(v.getValue(PERIODICITE.ID_VOIE));
			periodicite.setIdArret(v.getValue(PERIODICITE.ID_ARRET));
			periodicite.setDebut(v.getValue(PERIODICITE.DEBUT));
			periodicite.setFin(v.getValue(PERIODICITE.FIN));
			periodicite.setPeriode(v.getValue(PERIODICITE.PERIODE));
			periodicites.add(periodicite);
		}
		
		return periodicites;
	}

	@Override
	public void ajouterFDSPeriodicite(int idFDS, int idPeriodicite) {
		FeuilledeservicePeriodiciteRecord fdsPeriodicite = dsl.newRecord(FEUILLEDESERVICE_PERIODICITE);
		fdsPeriodicite.setId(null);
		fdsPeriodicite.setFeuilledeserviceId(idFDS);
		fdsPeriodicite.setPeriodiciteId(idPeriodicite);
		fdsPeriodicite.store();
	}

	@Override
	public void supprimerFDSPeriodicite(int idFDS, int idPeriodicite) {
		dsl.delete(FEUILLEDESERVICE_PERIODICITE)
		.where(FEUILLEDESERVICE_PERIODICITE.FEUILLEDESERVICE_ID.equal(idFDS))
		.and(FEUILLEDESERVICE_PERIODICITE.PERIODICITE_ID.equal(idPeriodicite))
		.execute();
	}

	/* GESTION DES SERVICES */

	@Override
	public List<Service> getServices() {
		Service service;
		List<Service> services = new ArrayList<Service>();
		
		Result<ServiceRecord> results = dsl.fetch(SERVICE);
		
		for (ServiceRecord s : results) {
			service = new Service();
			service.setId(s.getValue(SERVICE.ID));
			service.setFeuilledeserviceId(s.getValue(SERVICE.FEUILLEDESERVICE_ID));
			services.add(service);
		}
		
		return services;
	}

	@Override
	public Map<Service, Integer> getServices(String date) {
		Map<Service, Integer> services = new LinkedHashMap<Service, Integer>();
		Service service;

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyy HH:mm:ss");
		java.sql.Date dateSQL = null;
		try {
			dateSQL = new java.sql.Date(format.parse(date).getTime());
		} catch (DataAccessException | ParseException e) {
			e.printStackTrace();
		}
		
		Result<Record2<Integer, Integer>> results = dsl.selectDistinct(SERVICE.ID, ASSOCIATIONCONDUCTEURSERVICE.CONDUCTEUR_ID)
				.from(SERVICE)
				.leftOuterJoin(ASSOCIATIONCONDUCTEURSERVICE)
				.on(
						SERVICE.ID.equal(ASSOCIATIONCONDUCTEURSERVICE.SERVICE_ID)
						.and(ASSOCIATIONCONDUCTEURSERVICE.DATE.equal(dateSQL))
				)
				.fetch();
		
		for (Record2<Integer, Integer> s : results) {
			service = new Service();
			service.setId(s.getValue(SERVICE.ID));
			
			Integer conducteurId = s.getValue(ASSOCIATIONCONDUCTEURSERVICE.CONDUCTEUR_ID);
			if(conducteurId == null) {
				conducteurId = 0;
			}
			
			services.put(service, conducteurId);
		}
		
		return services;
	}
	
	@Override
	public void updateService(int id, String colname, String newvalue) {
		ServiceRecord s = dsl.fetchOne(SERVICE, SERVICE.ID.equal(id));

		if(colname.compareTo("fds") == 0) {
			Integer idFeuilleDeService = Integer.valueOf(newvalue);
			if(idFeuilleDeService == 0) {
				s.setFeuilledeserviceId(null);
			} else {
				s.setFeuilledeserviceId(idFeuilleDeService);
			}
		}
		
		s.store();
	}

	@Override
	public void ajouterService() {
		ServiceRecord serviceRecord = dsl.newRecord(SERVICE);
		serviceRecord.setId(null);
		serviceRecord.setFeuilledeserviceId(null);
		serviceRecord.store();
	}

	@Override
	public void supprimerService(int id) {
		dsl.delete(SERVICE)
		.where(SERVICE.ID.equal(id))
		.execute();
	}
	
	/* GESTION DES VACATIONS */

	@Override
	public List<Vacation> getVacations(int idService, int idVehicule) {
		Vacation vacation;
		List<Vacation> vacations = new ArrayList<Vacation>();
		Result<Record7<Integer, Time, Time, Integer, Integer, Integer, Integer>> results = null;
		
		if(idService != 0) {
			results = dsl.selectDistinct(VACATION.ID, VACATION.HEUREDEBUT, VACATION.HEUREFIN, VACATION.ARRETECHANGECONDUCTEURDEBUT_ID, VACATION.ARRETECHANGECONDUCTEURFIN_ID, VACATION.VEHICULE_ID, VACATION.SERVICE_ID)
					.from(VACATION)
					.where(VACATION.SERVICE_ID.equal(idService))
					.fetch();
		} else if(idVehicule != 0) {
			results = dsl.selectDistinct(VACATION.ID, VACATION.HEUREDEBUT, VACATION.HEUREFIN, VACATION.ARRETECHANGECONDUCTEURDEBUT_ID, VACATION.ARRETECHANGECONDUCTEURFIN_ID, VACATION.VEHICULE_ID, VACATION.SERVICE_ID)
					.from(VACATION)
					.where(VACATION.VEHICULE_ID.equal(idVehicule))
					.fetch();
		}
		
		if(results != null) {
			for (Record7<Integer, Time, Time, Integer, Integer, Integer, Integer> v : results) {
				vacation = new Vacation();
				vacation.setId(v.getValue(VACATION.ID));
				vacation.setHeuredebut(v.getValue(VACATION.HEUREDEBUT));
				vacation.setHeurefin(v.getValue(VACATION.HEUREFIN));
				vacation.setArretechangeconducteurdebutId(v.getValue(VACATION.ARRETECHANGECONDUCTEURDEBUT_ID));
				vacation.setArretechangeconducteurfinId(v.getValue(VACATION.ARRETECHANGECONDUCTEURFIN_ID));
				vacation.setVehiculeId(v.getValue(VACATION.VEHICULE_ID));
				vacation.setServiceId(v.getValue(VACATION.SERVICE_ID));
				vacations.add(vacation);
			}
		}
		
		return vacations;
	}

	@Override
	public void updateVacation(int id, String colname, String newvalue) {
		VacationRecord v = dsl.fetchOne(VACATION, VACATION.ID.equal(id));

		if(colname.compareTo("heureDebut") == 0) {
			SimpleDateFormat formater = new SimpleDateFormat("hh:mm:ss");
			Date date;
			try {
				date = formater.parse(newvalue);
				v.setHeuredebut(new java.sql.Time(date.getTime()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else if(colname.compareTo("heureFin") == 0) {
			SimpleDateFormat formater = new SimpleDateFormat("hh:mm:ss");
			Date date;
			try {
				date = formater.parse(newvalue);
				v.setHeurefin(new java.sql.Time(date.getTime()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else if(colname.compareTo("arretEchangeConducteurDebut") == 0) {
			Integer idArretEchangeConducteurDebut = Integer.valueOf(newvalue);
			if(idArretEchangeConducteurDebut == 0) {
				v.setArretechangeconducteurdebutId(null);
			} else {
				v.setArretechangeconducteurdebutId(idArretEchangeConducteurDebut);
			}
		} else if(colname.compareTo("arretEchangeConducteurFin") == 0) {
			Integer idArretEchangeConducteurFin = Integer.valueOf(newvalue);
			if(idArretEchangeConducteurFin == 0) {
				v.setArretechangeconducteurfinId(null);
			} else {
				v.setArretechangeconducteurfinId(idArretEchangeConducteurFin);
			}
		} else if(colname.compareTo("vehicule") == 0) {
			Integer idVehicule = Integer.valueOf(newvalue);
			if(idVehicule == 0) {
				v.setVehiculeId(null);
			} else {
				v.setVehiculeId(idVehicule);
			}
		} else if(colname.compareTo("service") == 0) {
			Integer idService = Integer.valueOf(newvalue);
			if(idService == 0) {
				v.setServiceId(null);
			} else {
				v.setServiceId(idService);
			}
		}
		
		v.store();
	}

	@Override
	public void ajouterVacation(int idService, int idVehicule) {
		VacationRecord vacationRecord = dsl.newRecord(VACATION);
		vacationRecord.setId(null);
		if(idService == 0)
			vacationRecord.setServiceId(null);
		else
			vacationRecord.setServiceId(idService);
		if(idVehicule == 0)
			vacationRecord.setVehiculeId(null);
		else
			vacationRecord.setVehiculeId(idVehicule);
		vacationRecord.store();
	}

	@Override
	public void supprimerVacation(int id) {
		dsl.delete(VACATION)
		.where(VACATION.ID.equal(id))
		.execute();
	}
	
	/* GESTION DES VEHICULES */

	@Override
	public List<Vehicule> getVehicules() {
		Vehicule vehicule;
		List<Vehicule> vehicules = new ArrayList<Vehicule>();
		
		Result<VehiculeRecord> results = dsl.fetch(VEHICULE);
		
		for (VehiculeRecord v : results) {
			vehicule = new Vehicule();
			vehicule.setId(v.getValue(VEHICULE.ID));
			vehicule.setTypevehicule(v.getValue(VEHICULE.TYPEVEHICULE));
			vehicules.add(vehicule);
		}
		
		return vehicules;
	}

	@Override
	public void updateVehicule(int id, String colname, String newvalue) {
		VehiculeRecord v = dsl.fetchOne(VEHICULE, VEHICULE.ID.equal(id));

		if(colname.compareTo("typeVehicule") == 0) {
			v.setTypevehicule(newvalue);
		}
		
		v.store();
	}

	@Override
	public void ajouterVehicule() {
		VehiculeRecord vehiculeRecord = dsl.newRecord(VEHICULE);
		vehiculeRecord.setId(null);
		vehiculeRecord.store();
	}

	@Override
	public void supprimerVehicule(int id) {
		dsl.delete(VEHICULE)
		.where(VEHICULE.ID.equal(id))
		.execute();
	}

	/* GESTION DES ASSOCIATIONS SERVICES - CONDUCTEURS */
	
	@Override
	public void updateServiceConducteur(int idService, String date, String colname, String newvalue) {
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyy HH:mm:ss");
		java.sql.Date dateSQL = null;
		try {
			dateSQL = new java.sql.Date(format.parse(date).getTime());
		} catch (DataAccessException | ParseException e) {
			e.printStackTrace();
		}

		AssociationconducteurserviceRecord cs = dsl.fetchOne(ASSOCIATIONCONDUCTEURSERVICE, 
				ASSOCIATIONCONDUCTEURSERVICE.SERVICE_ID.equal(idService)
				.and(ASSOCIATIONCONDUCTEURSERVICE.DATE.equal(dateSQL)));
		
		
		if(newvalue.matches("[0-9]+")) {
			int newvalueInt = Integer.valueOf(newvalue);
			if(colname.compareTo("conducteur") == 0) {
				if(newvalueInt == 0) {
					supprimerServiceConducteur(idService, dateSQL);
				} else {
					if(cs != null) {
						cs.setConducteurId(newvalueInt);
					} else {
						ajouterServiceConducteur(idService, dateSQL, newvalueInt);
					}
				}
			}
		}
		
		if(cs != null) {
			cs.store();
		}
	}
	

	@Override
	public void ajouterServiceConducteur(int idService, java.sql.Date dateSQL, int idConducteur) {
		AssociationconducteurserviceRecord cs = dsl.newRecord(ASSOCIATIONCONDUCTEURSERVICE);
		cs.setId(null);
		cs.setDate(dateSQL);
		cs.setServiceId(idService);
		cs.setConducteurId(idConducteur);
		cs.store();
	}

	@Override
	public void supprimerServiceConducteur(int idService, java.sql.Date dateSQL) {
		dsl.delete(ASSOCIATIONCONDUCTEURSERVICE)
		.where(ASSOCIATIONCONDUCTEURSERVICE.SERVICE_ID.equal(idService)
				.and(ASSOCIATIONCONDUCTEURSERVICE.DATE.equal(dateSQL)))
		.execute();
	}

	/* GESTION DES CONDUCTEURS */

	@Override
	public List<Conducteur> getConducteurs() {
		Conducteur conducteur;
		List<Conducteur> conducteurs = new ArrayList<Conducteur>();
		
		Result<ConducteurRecord> results = dsl.fetch(CONDUCTEUR);
		
		for (ConducteurRecord c : results) {
			conducteur = new Conducteur();
			conducteur.setId(c.getValue(CONDUCTEUR.ID));
			conducteur.setNom(c.getValue(CONDUCTEUR.NOM));
			conducteur.setTelephone(c.getValue(CONDUCTEUR.TELEPHONE));
			conducteurs.add(conducteur);
		}
		
		return conducteurs;
	}

	/* GESTION DES DIAGRAMMES DE LIGNE */
	
	@Override
	public ListArret getArretsDiagramme(int idLigne) {

		List<Voie> voies = getVoiesPourLaLigne(idLigne);
		ListArret arrets = new ListArret();
		List<Arret> arretsVoie;
		Arret arret;
		boolean doitEtreAjoute = true;
		int dernierIndexInsere = 0;
		
		for(Voie v : voies) {
			arretsVoie = getArretsPourLaVoie(v.getId());
			for(Arret a : arretsVoie) {
				doitEtreAjoute = !arrets.contains(a);
				if(doitEtreAjoute) {
					arrets.add(dernierIndexInsere, a);
					dernierIndexInsere++;
				}
			}
		}
		
		return arrets;
	}


}
