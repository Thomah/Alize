package alize.eole.modele;

import java.util.ArrayList;

/**
 * @name Service
 * @author Cyril [CS]
 * @date 23 oct. 2014
 * @version 1
 */
public class Service {
	
	private int numero;
	private int nbPeriodeDeConduite;
	private ArrayList<PeriodeDeConduite> ensemblePeriodesDeConduite;
	//private long tempsTotalDeTravail;

	/**
	 * @name Service
	 * @author Cyril [CS]
	 * @date 23 oct. 2014
	 * @version 1
	 */
	public Service(int numero) {
		this.numero = numero;
		this.nbPeriodeDeConduite=0;
		this.ensemblePeriodesDeConduite=new ArrayList<PeriodeDeConduite>();
	}
	
	

	/***********************************************************************************************************************
	 * Méthodes
	 */ 
	
	public void ajouterPeriodeDeConduite(PeriodeDeConduite pdc){
		nbPeriodeDeConduite++;
		ensemblePeriodesDeConduite.add(pdc);
	}
	
	public String toString(){
		String s = new String();
		s+=numero + " :\n";
		for(PeriodeDeConduite pdc: ensemblePeriodesDeConduite){
			s+=pdc.toString()+"\n";
		}
		return s;
	}


	/***********************************************************************************************************************
	 * Getters & Setters
	 */ 
	
	/**
	 * @name getNumero
	 * @return le numero
	 * @author Cyril [CS]
	 * @date 23 oct. 2014
	 * @version 1
	 */
	public int getNumero() {
		return numero;
	}

	/**
	 * @name setNumero
	 * @param numero le numero à mettre à jour
	 * @author Cyril [CS]
	 * @date 23 oct. 2014
	 * @version 1
	 */
	public void setNumero(int numero) {
		this.numero = numero;
	}

	/**
	 * @name getNbTroncon
	 * @return le nbTroncon
	 * @author Cyril [CS]
	 * @date 23 oct. 2014
	 * @version 1
	 */
	public int getNbPeriodeDeConduite() {
		return nbPeriodeDeConduite;
	}

	/**
	 * @name setNbTroncon
	 * @param nbTroncon le nbTroncon à mettre à jour
	 * @author Cyril [CS]
	 * @date 23 oct. 2014
	 * @version 1
	 */
	public void setNbPeriodeDeConduite(int nbTroncon) {
		this.nbPeriodeDeConduite = nbTroncon;
	}

	/**
	 * @name getEnsemblePeriodesDeConduite
	 * @return le ensemblePeriodesDeConduite
	 * @author Cyril [CS]
	 * @date 23 oct. 2014
	 * @version 1
	 */
	public ArrayList<PeriodeDeConduite> getEnsemblePeriodesDeConduite() {
		return ensemblePeriodesDeConduite;
	}

	/**
	 * @name setEnsemblePeriodesDeConduite
	 * @param ensemblePeriodesDeConduite le ensemblePeriodesDeConduite à mettre à jour
	 * @author Cyril [CS]
	 * @date 23 oct. 2014
	 * @version 1
	 */
	public void setEnsemblePeriodesDeConduite(
			ArrayList<PeriodeDeConduite> ensemblePeriodesDeConduite) {
		this.ensemblePeriodesDeConduite = ensemblePeriodesDeConduite;
	}
	
	
	

}
