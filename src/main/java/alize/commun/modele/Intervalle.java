package alize.commun.modele;

import java.time.LocalTime;

public class Intervalle {
	
	private String id;
	private LocalTime min;
	private LocalTime pref;
	private LocalTime max;
	
	public String getId() {
		return id;
		
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public LocalTime getMin() {
		return min;
	}
	
	public void setMin(LocalTime min) {
		this.min = min;
	}
	
	public LocalTime getPref() {
		return pref;
	}
	
	public void setPref(LocalTime pref) {
		this.pref = pref;
	}
	
	public LocalTime getMax() {
		return max;
	}
	
	public void setMax(LocalTime max) {
		this.max = max;
	}
	
}
