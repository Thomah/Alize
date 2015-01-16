package alize.eole.modele;

import java.util.ArrayList;
import java.util.List;

public class LieuM{
	
	//Peut être supprimer (cela revient à dire que listeVehiculesPresents est vide );
	private boolean estoccupe;
	private List<VehiculeM> listeVehiculesPresents = new ArrayList<VehiculeM>();
	
	public void ajouterUnVehicule(VehiculeM v){
		this.getListeVehiculesPresents().add(v);
	}
	
	public void supprimerUnVehicule(VehiculeM v){
		this.getListeVehiculesPresents().remove(v);
	}
	
	public String toString(){
		if(this.getClass() == ArretM.class){
			return ((ArretM) this).toString();
		}else{
			return "Nom de lieu non définit";
		}
	}
	public boolean isEstoccupe() {
		return estoccupe;
	}
	public void setEstoccupe(boolean estoccupe) {
		this.estoccupe = estoccupe;
	}
	public List<VehiculeM> getListeVehiculesPresents() {
		return listeVehiculesPresents;
	}
	public void setListeVehiculesPresents(List<VehiculeM> listeVehiculesPresents) {
		this.listeVehiculesPresents = listeVehiculesPresents;
	}
	
	
	
}