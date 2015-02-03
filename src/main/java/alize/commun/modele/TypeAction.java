package alize.commun.modele;

public enum TypeAction {
	
	ARRIVER_ARRET(1),
	QUITTER_ARRET(1),
	DEVENIR_COMMERCIAL(4),
	DEVENIR_NON_COMMERCIAL(4),
	CHANGER_CONDUCTEUR(4),
	CHANGER_VOIE(1);
	
	private int poids;
	
	private TypeAction(int poids){
		this.poids=poids;
	}

	public int getPoids() {
		return poids;
	}

	public void setPoids(int poids) {
		this.poids = poids;
	}
	
}
