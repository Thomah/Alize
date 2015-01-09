package alize.commun.service;

import static alize.commun.modele.Tables.*;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jooq.DSLContext;
import org.jooq.Record2;
import org.jooq.Record4;
import org.jooq.Record6;
import org.jooq.Record8;
import org.jooq.Record9;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;

import alize.commun.modele.tables.pojos.Intervalle;
import alize.commun.modele.tables.pojos.Arret;
import alize.commun.modele.tables.pojos.Depot;
import alize.commun.modele.tables.pojos.Intervalle;
import alize.commun.modele.tables.pojos.Ligne;
import alize.commun.modele.tables.pojos.Periodicite;
import alize.commun.modele.tables.pojos.Terminus;
import alize.commun.modele.tables.pojos.Transition;
import alize.commun.modele.tables.pojos.Voie;
import alize.commun.modele.tables.records.ArretRecord;
import alize.commun.modele.tables.records.DepotRecord;
import alize.commun.modele.tables.records.IntervalleRecord;
import alize.commun.modele.tables.records.LigneRecord;
import alize.commun.modele.tables.records.LigneVoieRecord;
import alize.commun.modele.tables.records.PeriodiciteRecord;
import alize.commun.modele.tables.records.TerminusRecord;
import alize.commun.modele.tables.records.TransitionRecord;
import alize.commun.modele.tables.records.VoieArretRecord;
import alize.commun.modele.tables.records.VoieRecord;

public class StockageServiceImpl implements StockageService {

	public static final SimpleDateFormat PERIODE_FORMAT = new SimpleDateFormat(
			"hh:mm:ss");
	
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
	

	/* ATTRIBUTION DES ARRETS AUX VOIES */

	@Override
	public Map<Arret, String> getArretsNonAttribues(int idVoie) {
		Map<Arret, String> arrets = new HashMap<Arret, String>();
		Arret arret;
		String status = "";
		
		Result<Record8<Integer, String, Byte, Byte, Byte, Byte, Integer, Integer>> results =
				dsl.selectDistinct(ARRET.ID, ARRET.NOM, ARRET.ESTCOMMERCIAL, ARRET.ESTENTREEDEPOT, ARRET.ESTSORTIEDEPOT, ARRET.ESTLIEUECHANGECONDUCTEUR, TERMINUS.ID, DEPOT.ID)
				.from(ARRET)
				.leftOuterJoin(TERMINUS).on(ARRET.ID.equal(TERMINUS.ARRET_ID))
				.leftOuterJoin(DEPOT).on(ARRET.ID.equal(DEPOT.ARRET_ID))
				.where(ARRET.ID.notIn(
						dsl.select(VOIE_ARRET.ARRET_ID)
						.from(VOIE_ARRET)
						.where(VOIE_ARRET.VOIE_ID.equal(idVoie))
						))
				.fetch();
		
		for(Record8<Integer, String, Byte, Byte, Byte, Byte, Integer, Integer> a : results) {
			arret = new Arret();
			arret.setId(a.getValue(ARRET.ID));
			arret.setNom(a.getValue(ARRET.NOM));
			arret.setEstcommercial(a.getValue(ARRET.ESTCOMMERCIAL));
			arret.setEstentreedepot(a.getValue(ARRET.ESTENTREEDEPOT));
			arret.setEstsortiedepot(a.getValue(ARRET.ESTSORTIEDEPOT));
			arret.setEstlieuechangeconducteur(a.getValue(ARRET.ESTLIEUECHANGECONDUCTEUR));
			
			status = "";
			if(a.getValue(TERMINUS.ID) != null && a.getValue(TERMINUS.ID).toString().compareTo("null") != 0) {
				status = "T";
			}
			if(a.getValue(DEPOT.ID) != null && a.getValue(DEPOT.ID).toString().compareTo("null") != 0) {
				status = "D";
			}
			
			arrets.put(arret, status);
		}
		
		return arrets;
	}

	@Override
	public Map<Arret, String> getArretsAttribues(int idVoie) {
		Map<Arret, String> arrets = new HashMap<Arret, String>();
		Arret arret;
		String status = "";
		
		Result<Record8<Integer, String, Byte, Byte, Byte, Byte, Integer, Integer>> results =
				dsl.selectDistinct(ARRET.ID, ARRET.NOM, ARRET.ESTCOMMERCIAL, ARRET.ESTENTREEDEPOT, ARRET.ESTSORTIEDEPOT, ARRET.ESTLIEUECHANGECONDUCTEUR, TERMINUS.ID, DEPOT.ID)
				.from(ARRET)
				.join(VOIE_ARRET).on(ARRET.ID.equal(VOIE_ARRET.ARRET_ID))
				.leftOuterJoin(TERMINUS).on(ARRET.ID.equal(TERMINUS.ARRET_ID))
				.leftOuterJoin(DEPOT).on(ARRET.ID.equal(DEPOT.ARRET_ID))
				.where(VOIE_ARRET.VOIE_ID.equal(idVoie))
				.fetch();
		
		for(Record8<Integer, String, Byte, Byte, Byte, Byte, Integer, Integer> a : results) {
			arret = new Arret();
			arret.setId(a.getValue(ARRET.ID));
			arret.setNom(a.getValue(ARRET.NOM));
			arret.setEstcommercial(a.getValue(ARRET.ESTCOMMERCIAL));
			arret.setEstentreedepot(a.getValue(ARRET.ESTENTREEDEPOT));
			arret.setEstsortiedepot(a.getValue(ARRET.ESTSORTIEDEPOT));
			arret.setEstlieuechangeconducteur(a.getValue(ARRET.ESTLIEUECHANGECONDUCTEUR));

			status = "";
			if(a.getValue(TERMINUS.ID) != null && a.getValue(TERMINUS.ID).toString().compareTo("null") != 0) {
				status = "T";
			}
			if(a.getValue(DEPOT.ID) != null && a.getValue(DEPOT.ID).toString().compareTo("null") != 0) {
				status = "D";
			}
			
			arrets.put(arret, status);
		}
		
		return arrets;
	}

	@Override
	public void ajouterVoieArret(int idVoie, int idArret) {
		VoieArretRecord voieArretRecord = dsl.newRecord(VOIE_ARRET);
		voieArretRecord.setId(null);
		voieArretRecord.setVoieId(idVoie);
		voieArretRecord.setArretId(idArret);
		voieArretRecord.store();
	}

	@Override
	public void supprimerVoieArret(int idVoie, int idArret) {
		dsl.delete(VOIE_ARRET)
		.where(VOIE_ARRET.VOIE_ID.equal(idVoie))
		.and(VOIE_ARRET.ARRET_ID.equal(idArret))
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
			arret.setEstoccupe(a.getEstoccupe());
			arret.setNom(a.getNom());
			arret.setTempsimmobilisationId(a.getTempsimmobilisationId());
			arrets.add(arret);
		}
		
		return arrets;
	}
	
	@Override
	public Arret getArret(int id) {
		Arret arret = new Arret();
		Result<Record9<Integer, String, Byte, Byte, Byte, Byte, Byte, Integer, Integer>> results = 
				dsl.select(ARRET.ID, ARRET.NOM, ARRET.ESTCOMMERCIAL, ARRET.ESTENTREEDEPOT, ARRET.ESTLIEUECHANGECONDUCTEUR, ARRET.ESTOCCUPE, ARRET.ESTSORTIEDEPOT, VOIE_ARRET.ARRET_ID, VOIE_ARRET.VOIE_ID)
				.from(ARRET)
				.where(ARRET.ID.equal(id))
				.fetch();
		Record9<Integer, String, Byte, Byte, Byte, Byte, Byte, Integer, Integer> result = results.get(0);
		arret.setId(result.getValue(ARRET.ID));
		arret.setNom(result.getValue(ARRET.NOM));
		arret.setEstcommercial(result.getValue(ARRET.ESTCOMMERCIAL));
		arret.setEstentreedepot(result.getValue(ARRET.ESTENTREEDEPOT));
		arret.setEstlieuechangeconducteur(result.getValue(ARRET.ESTLIEUECHANGECONDUCTEUR));
		arret.setEstoccupe(result.getValue(ARRET.ESTOCCUPE));
		arret.setEstsortiedepot(result.getValue(ARRET.ESTSORTIEDEPOT));
		return arret;
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
		
		Arret arret;
		List<Arret> arrets = new ArrayList<Arret>();
		
		Result<Record9<Integer, String, Byte, Byte, Byte, Byte, Byte, Integer, Integer>> results = 
				dsl.select(ARRET.ID, ARRET.NOM, ARRET.ESTCOMMERCIAL, ARRET.ESTENTREEDEPOT, ARRET.ESTLIEUECHANGECONDUCTEUR, ARRET.ESTOCCUPE, ARRET.ESTSORTIEDEPOT, VOIE_ARRET.ARRET_ID, VOIE_ARRET.VOIE_ID)
				.from(ARRET)
				.join(VOIE_ARRET)
				.on(ARRET.ID.equal(VOIE_ARRET.ARRET_ID))
				.where(VOIE_ARRET.VOIE_ID.equal(idVoie))
				.fetch();
		
		for (Record9<Integer, String, Byte, Byte, Byte, Byte, Byte, Integer, Integer> a : results) {
			arret = new Arret();
			arret.setId(a.getValue(ARRET.ID));
			arret.setNom(a.getValue(ARRET.NOM));
			arret.setEstcommercial(a.getValue(ARRET.ESTCOMMERCIAL));
			arret.setEstentreedepot(a.getValue(ARRET.ESTENTREEDEPOT));
			arret.setEstlieuechangeconducteur(a.getValue(ARRET.ESTLIEUECHANGECONDUCTEUR));
			arret.setEstoccupe(a.getValue(ARRET.ESTOCCUPE));
			arret.setEstsortiedepot(a.getValue(ARRET.ESTSORTIEDEPOT));
			arrets.add(arret);
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
		
		Map<Integer, String> terminus = new HashMap<Integer, String>();
		
		Result<Record2<Integer, String>> results =
				dsl.select(TERMINUS.ARRET_ID, ARRET.NOM)
				.from(TERMINUS)
				.join(ARRET).on(TERMINUS.ARRET_ID.equal(ARRET.ID))
				.join(VOIE_ARRET).on(ARRET.ID.equal(VOIE_ARRET.ARRET_ID))
				.where(VOIE_ARRET.VOIE_ID.equal(idVoie))
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
			arretRecord.setEstoccupe(new Byte("0"));
			arretRecord.store();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
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
	

	/* INTERVALLES */
	
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
	
	/* TERMINUS */
	
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
	
	/* DEPOTS */
	
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
}
