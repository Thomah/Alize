package alize.eole.modele;

import java.util.List;

import alize.commun.modele.tables.pojos.Arret;

public class ArretM extends LieuM{

	
	private alize.commun.modele.tables.pojos.Arret arret;
	
	
	public ArretM(Arret arret){
		this.arret=arret;
	
	}
	

	
	
	
	
	public String toString(){
		return this.arret.getNom().toString();
	}






	public alize.commun.modele.tables.pojos.Arret getArret() {
		return arret;
	}






	public void setArret(alize.commun.modele.tables.pojos.Arret arret) {
		this.arret = arret;
	}
	
	
	
}