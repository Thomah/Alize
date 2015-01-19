package alize.eole.modele;

import alize.commun.modele.tables.pojos.Vehicule;

public class VehiculeM{
	
	private Vehicule vehicule;
	private boolean estCommercial;
	private LieuM lieu;
	private boolean estEnService;
	
	public VehiculeM(Vehicule vehicule, LieuM lieu){
		this.vehicule = vehicule;
		this.estCommercial=false;
		this.estEnService=false;
		this.lieu= lieu;
	}
	
	public void changerLieu(LieuM nouveauLieu){
		this.getLieu().supprimerUnVehicule(this);
		this.setLieu(nouveauLieu);	
		this.getLieu().ajouterUnVehicule(this);
	}
	
	public String toString(){
		return this.getVehicule().getId().toString();
	}

	public Vehicule getVehicule() {
		return vehicule;
	}

	public void setVehicule(Vehicule vehicule) {
		this.vehicule = vehicule;
	}

	public boolean isEstCommercial() {
		return estCommercial;
	}

	public void setEstCommercial(boolean estCommercial) {
		this.estCommercial = estCommercial;
	}

	

	public LieuM getLieu() {
		return lieu;
	}

	public void setLieu(LieuM lieu) {
		this.lieu = lieu;
	}

	public boolean isEstEnService() {
		return estEnService;
	}

	public void setEstEnService(boolean estEnService) {
		this.estEnService = estEnService;
	}
	
	
	
}