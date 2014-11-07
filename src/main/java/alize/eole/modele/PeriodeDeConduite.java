package alize.eole.modele;

import java.time.LocalDateTime;
import java.util.Date;

import alize.commun.Heure;

/**
 * @name PeriodeDeConduite
 * @author Cyril [CS]
 * @date 23 oct. 2014
 * @version 1
 */
public class PeriodeDeConduite {

	private Heure heureDeDebut; 
	private Heure heureDeFin; 
	private int numeroVehicule;
	private Arret arretEchangeConducteurDebut;
	private Arret arretEchangeConducteurFin;
	
	

	
	
	/**
	 * @name PeriodeDeConduite
	 * @author Cyril [CS]
	 * @date 23 oct. 2014
	 * @version 1
	 */
	public PeriodeDeConduite(Heure heureDeDebut, Heure heureDeFin, int numeroVehicule, Arret arretEchangeConducteurDebut,Arret arretEchangeConducteurFin) {
		this.heureDeDebut = heureDeDebut;
		this.heureDeFin = heureDeFin;
		this.numeroVehicule = numeroVehicule;
		this.arretEchangeConducteurDebut = arretEchangeConducteurDebut;
		this.arretEchangeConducteurFin = arretEchangeConducteurFin;
	}
	
	public String toString(){
		return new String("V" +numeroVehicule +"\t "+ heureDeDebut + "\t " + arretEchangeConducteurDebut + "\t | \t" + heureDeFin + " \t" + arretEchangeConducteurFin);
	}

	/***********************************************************************************************************************
	 * Getters & Setters
	 */
	

	
	/**
	 * @name getNumeroVehicule
	 * @return le numeroVehicule
	 * @author Cyril [CS]
	 * @date 23 oct. 2014
	 * @version 1
	 */
	
	public int getNumeroVehicule() {
		return numeroVehicule;
	}
	
	/**
	 * @name getHeureDeDebut
	 * @return le heureDeDebut
	 * @author Cyril [CS]
	 * @date 23 oct. 2014
	 * @version 1
	 */
	public Heure getHeureDeDebut() {
		return heureDeDebut;
	}


	/**
	 * @name setHeureDeDebut
	 * @param heureDeDebut le heureDeDebut à mettre à jour
	 * @author Cyril [CS]
	 * @date 23 oct. 2014
	 * @version 1
	 */
	public void setHeureDeDebut(Heure heureDeDebut) {
		this.heureDeDebut = heureDeDebut;
	}


	/**
	 * @name getHeureDeFin
	 * @return le heureDeFin
	 * @author Cyril [CS]
	 * @date 23 oct. 2014
	 * @version 1
	 */
	public Heure getHeureDeFin() {
		return heureDeFin;
	}


	/**
	 * @name setHeureDeFin
	 * @param heureDeFin le heureDeFin à mettre à jour
	 * @author Cyril [CS]
	 * @date 23 oct. 2014
	 * @version 1
	 */
	public void setHeureDeFin(Heure heureDeFin) {
		this.heureDeFin = heureDeFin;
	}


	/**
	 * @name setNumeroVehicule
	 * @param numeroVehicule le numeroVehicule à mettre à jour
	 * @author Cyril [CS]
	 * @date 23 oct. 2014
	 * @version 1
	 */
	public void setNumeroVehicule(int numeroVehicule) {
		this.numeroVehicule = numeroVehicule;
	}


	/**
	 * @name getArretEchangeConducteurDebut
	 * @return le arretEchangeConducteurDebut
	 * @author Cyril [CS]
	 * @date 23 oct. 2014
	 * @version 1
	 */
	public Arret getArretEchangeConducteurDebut() {
		return arretEchangeConducteurDebut;
	}


	/**
	 * @name setArretEchangeConducteurDebut
	 * @param arretEchangeConducteurDebut le arretEchangeConducteurDebut à mettre à jour
	 * @author Cyril [CS]
	 * @date 23 oct. 2014
	 * @version 1
	 */
	public void setArretEchangeConducteurDebut(Arret arretEchangeConducteurDebut) {
		this.arretEchangeConducteurDebut = arretEchangeConducteurDebut;
	}


	/**
	 * @name getArretEchangeConducteurFin
	 * @return le arretEchangeConducteurFin
	 * @author Cyril [CS]
	 * @date 23 oct. 2014
	 * @version 1
	 */
	public Arret getArretEchangeConducteurFin() {
		return arretEchangeConducteurFin;
	}


	/**
	 * @name setArretEchangeConducteurFin
	 * @param arretEchangeConducteurFin le arretEchangeConducteurFin à mettre à jour
	 * @author Cyril [CS]
	 * @date 23 oct. 2014
	 * @version 1
	 */
	public void setArretEchangeConducteurFin(Arret arretEchangeConducteurFin) {
		this.arretEchangeConducteurFin = arretEchangeConducteurFin;
	}
	
	
	
}
