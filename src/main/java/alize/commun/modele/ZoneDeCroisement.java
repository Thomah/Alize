package alize.commun.modele;

import java.util.List;

public class ZoneDeCroisement  extends alize.commun.modele.tables.pojos.Zonedecroisement {

	
	private static final long serialVersionUID = 5987321387124889354L;
	
	private List<Transition> listeTransitionsConcernees;

	public boolean isOccupee(){
		for(Transition t : listeTransitionsConcernees){
			if(t.getLieu().estoccupe()){
				return true;
			}
		}
		return false;
	}

	public List<Transition> getListeTransitionsConcernees() {
		return listeTransitionsConcernees;
	}

	public void setListeTransitionsConcernees(
			List<Transition> listeTransitionsConcernees) {
		this.listeTransitionsConcernees = listeTransitionsConcernees;
	}
	
	
	
}
