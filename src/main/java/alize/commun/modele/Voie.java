package alize.commun.modele;

import alize.commun.util.ListArret;

public class Voie extends alize.commun.modele.tables.pojos.Voie{
	
	private static final long serialVersionUID = -2969820998352200335L;
	
	private ListArret arrets;
	
	public Voie(){
		setArrets(new ListArret());
	}

	public ListArret getArrets() {
		return arrets;
	}

	public void setArrets(ListArret arrets) {
		this.arrets = arrets;
	}
	
	
}