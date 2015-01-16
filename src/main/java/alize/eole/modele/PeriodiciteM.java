package alize.eole.modele;

import alize.commun.Heure;
import alize.commun.modele.tables.pojos.Periodicite;

public class PeriodiciteM {
	
	private Periodicite periodicite;
	
	public PeriodiciteM(Periodicite periodicite){
		this.periodicite=periodicite;
	}

	public String toString(){
		Heure hDeb = new Heure(this.getPeriodicite().getDebut());
		Heure hFin = new Heure(this.getPeriodicite().getFin());
		return hDeb.toString() + " --> " + hFin.toString() + " ["+ this.getPeriodicite().getPeriode() +"] ( Arret :" + this.getPeriodicite().getIdArret()+")";
	}
	
	
	public Periodicite getPeriodicite() {
		return periodicite;
	}

	public void setPeriodicite(Periodicite periodicite) {
		this.periodicite = periodicite;
	}
	
	
	
	
}
