package alize.commun.util;

import java.util.ArrayList;

import alize.commun.modele.Arret;

public class ListArret extends ArrayList<Arret> {

	private static final long serialVersionUID = 3603237437123806147L;
	
	public boolean contains(Arret a) {
		int k = 0;
		boolean contains = false;
		while(k < this.size() && !contains) {
			contains = this.get(k).getId() == a.getId() || this.get(k).getEstenfacede() == a.getId();
		}
		return contains;
	}

}
