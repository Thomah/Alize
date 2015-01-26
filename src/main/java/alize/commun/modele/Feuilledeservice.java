package alize.commun.modele;

import java.util.ArrayList;
import java.util.List;

import alize.commun.modele.tables.records.FeuilledeserviceRecord;

public class Feuilledeservice extends alize.commun.modele.tables.pojos.Feuilledeservice {

	private static final long serialVersionUID = -1187189522423789150L;
	
	private List<Service> services;
	
	public Feuilledeservice() {
		super();
		services = new ArrayList<Service>();
	}
	
	public Feuilledeservice(FeuilledeserviceRecord record) {
		this();
		if(record != null) {
			setId(record.getId());
			setCouleur(record.getCouleur());
			setDebutsaison(record.getDebutsaison());
			setFinsaison(record.getFinsaison());
		}
	}

	public List<Service> getServices() {
		return services;
	}

	public void setServices(List<Service> services) {
		this.services = services;
	}

}
