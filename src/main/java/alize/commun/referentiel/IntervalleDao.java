package alize.commun.referentiel;

import alize.commun.modele.Intervalle;

public interface IntervalleDao {
	
	public Intervalle ajouter(Intervalle intervalle);
	public Intervalle obtenirParId(String id);
	public void maj(Intervalle intervalle);
	public void supprimer(String id);
	
}
