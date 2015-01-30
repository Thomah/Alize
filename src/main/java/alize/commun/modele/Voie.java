package alize.commun.modele;

import java.util.ArrayList;
import java.util.List;

import alize.commun.modele.tables.pojos.Terminus;

public class Voie extends alize.commun.modele.tables.pojos.Voie{
	
	private static final long serialVersionUID = -2969820998352200335L;
	
	private Terminus terminusDepart;
	private Terminus terminusArrivee;
	private List<Transition> transitions;
	
	public Voie() {
		super();
		terminusDepart = new Terminus();
		terminusArrivee = new Terminus();
		transitions = new ArrayList<Transition>();
	}
	
	public boolean contientTransition(int id){
		for(Transition t : transitions){
			if(t.getId()==id){
				return true;
			}
		}
		return false;
	}
	
	public Terminus getTerminusDepart() {
		return terminusDepart;
	}
	
	public void setTerminusDepart(Terminus terminusDepart) {
		this.terminusDepart = terminusDepart;
	}
	
	public Terminus getTerminusArrivee() {
		return terminusArrivee;
	}
	
	public void setTerminusArrivee(Terminus terminusArrivee) {
		this.terminusArrivee = terminusArrivee;
	}
	
	public List<Transition> getTransitions() {
		return transitions;
	}
	
	public void setTransitions(List<Transition> transitions) {
		this.transitions = transitions;
	}
	
	
}