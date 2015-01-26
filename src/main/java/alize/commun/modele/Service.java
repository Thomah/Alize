package alize.commun.modele;

import static alize.commun.modele.Tables.SERVICE;

import java.util.ArrayList;
import java.util.List;

import org.jooq.Record;

public class Service extends alize.commun.modele.tables.pojos.Service {

	private static final long serialVersionUID = 8519498338270939172L;
	
	private List<Vacation> vacations;
	
	public Service() {
		super();
		this.vacations = new ArrayList<Vacation>();
	}
	
	public Service(Record record) {
		this();
		setId(record.getValue(SERVICE.ID));
		setFeuilledeserviceId(record.getValue(SERVICE.FEUILLEDESERVICE_ID));
	}

	public List<Vacation> getVacations() {
		return vacations;
	}

	public void setVacations(List<Vacation> vacations) {
		this.vacations = vacations;
	}
	
}
