package alize.commun.service;

import static alize.commun.modele.Tables.*;

import java.util.ArrayList;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Record6;
import org.jooq.Record8;
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


}
