package alize.eole.modele;

import alize.commun.modele.tables.pojos.Arret;
import alize.commun.modele.tables.pojos.Depot;

public class DepotM extends ArretM{
	
	private Depot depot;
	
	public DepotM(Depot depot, Arret a){
		super(a);
		this.depot=depot;
	}

	public Depot getDepot() {
		return depot;
	}

	public void setDepot(Depot depot) {
		this.depot = depot;
	}
	
	
	
}