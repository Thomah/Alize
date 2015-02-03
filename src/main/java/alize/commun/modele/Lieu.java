package alize.commun.modele;

import java.util.ArrayList;
import java.util.List;


public class Lieu  extends alize.commun.modele.tables.pojos.Lieu {

	/**
	 * 
	 */
	private static final long serialVersionUID = -474938177096962681L;
	
	//Peut être supprimer (cela revient à dire que listeVehiculesPresents est vide );

	
	private List<Vehicule> listeVehiculesPresents = new ArrayList<Vehicule>();
	private boolean arret;
	private int duree;
	
	public void ajouterUnVehicule(Vehicule v){
		this.getListeVehiculesPresents().add(v);
	}
	
	public void supprimerUnVehicule(Vehicule v){
		this.getListeVehiculesPresents().remove(v);
	}
	
	
	public boolean estoccupe() {
		return !listeVehiculesPresents.isEmpty();
	}

	public List<Vehicule> getListeVehiculesPresents() {
		return listeVehiculesPresents;
	}
	public void setListeVehiculesPresents(List<Vehicule> listeVehiculesPresents) {
		this.listeVehiculesPresents = listeVehiculesPresents;
	}

	public boolean isArret() {
		return arret;
	}

	public void setEstUnArret(boolean estUnArret) {
		this.arret = estUnArret;
	}

	public int getDuree() {
		return duree;
	}

	public void setDuree(int duree) {
		this.duree = duree;
	}


	
	
}
