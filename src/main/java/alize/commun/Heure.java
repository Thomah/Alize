package alize.commun;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @name Heure
 * @author Cyril [CS]
 * @date 3 nov. 2014
 * @version 1
 */
public class Heure {
	private int h;
	private int m; 
	private int s;
	
	
	public Heure(int h, int m, int s){
		this.s = s;
		this.m = m;
		this.h = h;
		
		this.baseSexaDecimale();
	}
	
	public Heure(Time t){
		LocalTime lt= t.toLocalTime();
		this.s = lt.getSecond();
		this.m = lt.getMinute();
		this.h = lt.getHour();
	}
	
	public void ajouterHeure(Heure h){
		this.setS(this.getS()+h.getS());
		this.setM(this.getM()+h.getM());
		this.setH(this.getH()+h.getH());
	}
	
	public int comparerHeure(Heure h2){
		double nbSec1 = this.getS() + this.getM()*60 + this.getH() * 3600;
		double nbSec2 = h2.getS() + h2.getM()*60 + h2.getH() * 3600;
		
		if(nbSec1==nbSec2){
			return 0;
		}else{
			if(nbSec1<nbSec2){
				System.out.println(nbSec1  + " < " + nbSec2 + "(" + this + " < " + h2 + ")");
				return -1;
			}else{

				System.out.println(nbSec1  + " > " + nbSec2 + "(" + this + " > " + h2 + ")");
				return 1;
			}
		}
	}
	
	
	
	private void baseSexaDecimale(){
		int h = getH();
		int m = getM();
		int s = getS();
		
		this.setS(s%60);
		m+=s/60;
		this.setM(m%60);
		h+=m/60;
		this.setH(h);
		
	}
	
	
	public String toString(){
		return (this.getH()<10 ? "0" : "") + this.getH() + ":" + (this.getM()<10 ? "0" : "") + this.getM() + ":" +  (this.getS()<10 ? "0" : "") + this.getS();
	}



	public int getH() {
		return h;
	}



	public void setH(int h) {
		this.h = h;
	}



	public int getM() {
		return m;
	}



	public void setM(int m) {
		this.m = m;
	}



	public int getS() {
		return s;
	}



	public void setS(int s) {
		this.s = s;
	}
	
	
}
