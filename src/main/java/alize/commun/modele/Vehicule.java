package alize.commun.modele;



public class Vehicule extends alize.commun.modele.tables.pojos.Vehicule {

	private static final long serialVersionUID = 2592786925347824617L;

	private boolean estCommercial;
	private Lieu lieuActuel;
	private boolean estEnService;
	private int heureProchainDepart; 
	private Voie voieActuelle;
	
	
	public void changerLieu(Lieu nouveauLieu){
		this.getLieuActuel().supprimerUnVehicule(this);
		this.setLieuActuel(nouveauLieu);	
		this.getLieuActuel().ajouterUnVehicule(this);
	}
	
	public String toString(){
		return this.getId().toString();
	}

	public boolean isEstCommercial() {
		return estCommercial;
	}

	public void setEstCommercial(boolean estCommercial) {
		this.estCommercial = estCommercial;
	}

	public Lieu getLieuActuel() {
		return lieuActuel;
	}

	public void setLieuActuel(Lieu lieu) {
		this.lieuActuel = lieu;
	}

	public boolean isEstEnService() {
		return estEnService;
	}

	public void setEstEnService(boolean estEnService) {
		this.estEnService = estEnService;
	}

	public int getHeureProchainDepart() {
		return heureProchainDepart;
	}

	public void setHeureProchainDepart(int heureDeSortie) {
		this.heureProchainDepart = heureDeSortie;
	}

	public Voie getVoieActuelle() {
		return voieActuelle;
	}

	public void setVoieActuelle(Voie voieActuelle) {
		this.voieActuelle = voieActuelle;
	}	
	
	
}
