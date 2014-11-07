package alize.eole.modele;

import java.util.Date;

/**
 * @name Arret
 * @author Cyril [CS]
 * @date 23 oct. 2014
 * @version 1
 */
public class Arret {

	private String nom;
	private boolean estCommercial;
	private boolean estEntreeSortieDepot;
	private boolean estOccupe;
	private boolean estLieuEchangeConducteur;
	private Date tempsImmobilisation;
	
	

	

	/**
	 * @name Arret
	 * @author Cyril [CS]
	 * @date 23 oct. 2014
	 * @version 1
	 */
	public Arret(String nom, boolean estCommercial, boolean estEntreeSortieDepot, boolean estOccupe, boolean estLieuEchangeConducteur, Date tempsImmobilisation) {
		this.nom = nom;
		this.estCommercial = estCommercial;
		this.estEntreeSortieDepot = estEntreeSortieDepot;
		this.estOccupe = estOccupe;
		this.estLieuEchangeConducteur = estLieuEchangeConducteur;
		this.tempsImmobilisation = tempsImmobilisation;
	}
	
	

	/**
	 * @name Arret
	 * @author Cyril [CS]
	 * @date 23 oct. 2014
	 * @version 1
	 */
	public Arret(String nom) {
		this.nom = nom;
	}

	public String toString(){
		return new String(nom);
	}


	/***********************************************************************************************************************
	 * Getters & Setters
	 */
	
	
	/**
	 * @name getNom
	 * @return le nom
	 * @author Cyril [CS]
	 * @date 23 oct. 2014
	 * @version 1
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @name setNom
	 * @param nom le nom à mettre à jour
	 * @author Cyril [CS]
	 * @date 23 oct. 2014
	 * @version 1
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * @name isEstCommercial
	 * @return le estCommercial
	 * @author Cyril [CS]
	 * @date 23 oct. 2014
	 * @version 1
	 */	
	public boolean isEstCommercial() {
		return estCommercial;
	}

	/**
	 * @name setEstCommercial
	 * @param estCommercial le estCommercial à mettre à jour
	 * @author Cyril [CS]
	 * @date 23 oct. 2014
	 * @version 1
	 */
	public void setEstCommercial(boolean estCommercial) {
		this.estCommercial = estCommercial;
	}

	/**
	 * @name isEstEntreeSortieDepot
	 * @return le estEntreeSortieDepot
	 * @author Cyril [CS]
	 * @date 23 oct. 2014
	 * @version 1
	 */
	public boolean isEstEntreeSortieDepot() {
		return estEntreeSortieDepot;
	}

	/**
	 * @name setEstEntreeSortieDepot
	 * @param estEntreeSortieDepot le estEntreeSortieDepot à mettre à jour
	 * @author Cyril [CS]
	 * @date 23 oct. 2014
	 * @version 1
	 */
	public void setEstEntreeSortieDepot(boolean estEntreeSortieDepot) {
		this.estEntreeSortieDepot = estEntreeSortieDepot;
	}

	/**
	 * @name isEstOccupe
	 * @return le estOccupe
	 * @author Cyril [CS]
	 * @date 23 oct. 2014
	 * @version 1
	 */
	
	public boolean isEstOccupe() {
		return estOccupe;
	}

	/**
	 * @name setEstOccupe
	 * @param estOccupe le estOccupe à mettre à jour
	 * @author Cyril [CS]
	 * @date 23 oct. 2014
	 * @version 1
	 */
	public void setEstOccupe(boolean estOccupe) {
		this.estOccupe = estOccupe;
	}

	/**
	 * @name isEstLieuEchangeConducteur
	 * @return le estLieuEchangeConducteur
	 * @author Cyril [CS]
	 * @date 23 oct. 2014
	 * @version 1
	 */	
	public boolean isEstLieuEchangeConducteur() {
		return estLieuEchangeConducteur;
	}

	/**
	 * @name setEstLieuEchangeConducteur
	 * @param estLieuEchangeConducteur le estLieuEchangeConducteur à mettre à jour
	 * @author Cyril [CS]
	 * @date 23 oct. 2014
	 * @version 1
	 */
	public void setEstLieuEchangeConducteur(boolean estLieuEchangeConducteur) {
		this.estLieuEchangeConducteur = estLieuEchangeConducteur;
	}

	/**
	 * @name getTempsImmobilisation
	 * @return le tempsImmobilisation
	 * @author Cyril [CS]
	 * @date 23 oct. 2014
	 * @version 1
	 */
	
	public Date getTempsImmobilisation() {
		return tempsImmobilisation;
	}

	/**
	 * @name setTempsImmobilisation
	 * @param tempsImmobilisation le tempsImmobilisation à mettre à jour
	 * @author Cyril [CS]
	 * @date 23 oct. 2014
	 * @version 1
	 */
	public void setTempsImmobilisation(Date tempsImmobilisation) {
		this.tempsImmobilisation = tempsImmobilisation;
	}
	
}
