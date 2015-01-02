package alize.commun.service;

import static alize.commun.modele.Tables.*;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jooq.DSLContext;
import org.jooq.Record2;
import org.jooq.Record6;
import org.jooq.Record9;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;

import alize.commun.modele.tables.pojos.Arret;
import alize.commun.modele.tables.pojos.Ligne;
import alize.commun.modele.tables.pojos.Periodicite;
import alize.commun.modele.tables.pojos.Voie;
import alize.commun.modele.tables.records.ArretRecord;
import alize.commun.modele.tables.records.LigneRecord;
import alize.commun.modele.tables.records.PeriodiciteRecord;
import alize.commun.modele.tables.records.VoieRecord;

public class StockageServiceImpl implements StockageService {
	
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
			arret.setEstlieuechangeconducteur(a.getEstlieuechangeconducteur());
			arret.setEstoccupe(a.getEstoccupe());
			arret.setNom(a.getNom());
			arrets.add(arret);
		}
		
		return arrets;
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

}
