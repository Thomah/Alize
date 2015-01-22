package alize.commun.modele;

import java.util.List;

public class Arret extends alize.commun.modele.tables.pojos.Arret {

	private static final long serialVersionUID = 592990443975145164L;

	private Lieu lieu;
	private List<Transition> listeTransitionsPossibles;
	
	public String toString(){
		return this.getNom().toString();
	}

	public Lieu getLieu() {
		return lieu;
	}

	public void setLieu(Lieu lieu) {
		this.lieu = lieu;
	}

	public List<Transition> getListeTransitionsPossibles() {
		return listeTransitionsPossibles;
	}

	public void setListeTransitionsPossibles(
			List<Transition> listeTransitionsPossibles) {
		this.listeTransitionsPossibles = listeTransitionsPossibles;
	}
	
	
	
}
