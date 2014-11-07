package alize.commun;

/**
 * @name Heure
 * @author Cyril [CS]
 * @date 3 nov. 2014
 * @version 1
 */
public class Heure {
	private int h;
	private int m; 
	
	
	public Heure(int h, int m){
		this.h = h;
		this.m = m;
	}
	
	
	
	public String toString(){
		return new String(Constantes.formatter.format(h)+":"+Constantes.formatter.format(m));
	}
}
