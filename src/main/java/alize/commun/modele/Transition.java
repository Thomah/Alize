package alize.commun.modele;

public class Transition extends alize.commun.modele.tables.pojos.Transition {
	
	private static final long serialVersionUID = 837459821102377200L;
	
	private Arret arretPrecedent;
	private Arret arretSuivant;
	
	private Voie voie;
	
	private Lieu lieu;
	
	public Transition() {
		super();
		arretPrecedent = new Arret();
		arretSuivant = new Arret();
	}
	
	public Arret getArretPrecedent() {
		return arretPrecedent;
	}
	
	public void setArretPrecedent(Arret arretPrecedent) {
		this.arretPrecedent = arretPrecedent;
	}
	
	public Arret getArretSuivant() {
		return arretSuivant;
	}
	
	public void setArretSuivant(Arret arretSuivant) {
		this.arretSuivant = arretSuivant;
	}

	public Lieu getLieu() {
		return lieu;
	}

	public void setLieu(Lieu lieu) {
		this.lieu = lieu;
	}

	public Voie getVoie() {
		return voie;
	}

	public void setVoie(Voie voie) {
		this.voie = voie;
	}
	
	
}
