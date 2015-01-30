package alize.commun.modele;

import static alize.commun.modele.Tables.ASSOCIATIONCONDUCTEURSERVICE;
import static alize.commun.modele.Tables.CONDUCTEUR;
import static alize.commun.modele.Tables.SERVICE;

import java.util.ArrayList;
import java.util.List;

import org.jooq.Record;

import alize.commun.modele.tables.pojos.Conducteur;

public class Service extends alize.commun.modele.tables.pojos.Service {

	private static final long serialVersionUID = 8519498338270939172L;
	
	private Conducteur conducteur;
	private List<Vacation> vacations;
	
	public Service() {
		super();
		this.vacations = new ArrayList<Vacation>();
		this.conducteur = new Conducteur();
	}
	
	public Service(Record record) {
		this();
		setId(record.getValue(SERVICE.ID));
		setFeuilledeserviceId(record.getValue(SERVICE.FEUILLEDESERVICE_ID));
		conducteur.setId(record.getValue(ASSOCIATIONCONDUCTEURSERVICE.CONDUCTEUR_ID));
		conducteur.setNom(record.getValue(CONDUCTEUR.NOM));
		conducteur.setTelephone(record.getValue(CONDUCTEUR.TELEPHONE));
	}

	public Conducteur getConducteur() {
		return conducteur;
	}

	public void setConducteur(Conducteur conducteur) {
		this.conducteur = conducteur;
	}

	public List<Vacation> getVacations() {
		return vacations;
	}

	public void setVacations(List<Vacation> vacations) {
		this.vacations = vacations;
	}
	
}
