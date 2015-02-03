package alize.commun;

import java.sql.Time;
import java.time.LocalTime;

/**
 * @author Cyril [CS]
 * @version 1
 */
public class Heure {
	private int h;
	private int m; 
	private int s;
	
	private int nbSec;
	
	
	public Heure(int h, int m, int s){
		this.s = s;
		this.m = m;
		this.h = h;
		this.baseSexaDecimale();
		this.nbSec = this.toInt();
		
	}
	
	public Heure(Heure min, Heure max){
		 heureAuHasard(min, max);
	}
	
	public Heure(Time t){
		LocalTime lt= t.toLocalTime();
		this.s = lt.getSecond();
		this.m = lt.getMinute();
		this.h = lt.getHour();
		this.nbSec = this.toInt();
	}
	
	public int toInt(){
		return this.s+this.m*60+this.h*3600;
	}
	
	public void heureAuHasard(Heure Hmin, Heure Hmax){
		int min = Hmin.getNbSec();
		int max = Hmax.getNbSec();
		
		int rand = (int) (Math.random() * (max - min)) + min;
		
		toHeure(rand);
	}
	
	public void toHeure(double d){
		int h = (int) d/3600;
		int tamp = (int) d%3600;
		int m = (int) tamp/60;
		tamp = tamp%60;
		int s = (int) tamp;
		this.setH(h);
		this.setM(m);
		this.setS(s);
	}
	
	public Heure toHeure2(double d){
		int h = (int) d/3600;
		int tamp = (int) d%3600;
		int m = (int) tamp/60;
		tamp = tamp%60;
		int s = (int) tamp;
		return new Heure(h,m,s);
	}
	
	public void ajouterHeures(Heure h2){
		int d1 = this.getNbSec();
		int d2 = h2.getNbSec();
		toHeure(d1+d2);
	}
	
	public void incrementer(){
		int d1 = this.getNbSec();
		toHeure(d1);
		
	}
	
	public Heure soustraireHeures(Heure h1, Heure h2){
		int d1 = h1.getNbSec();
		int d2 = h2.getNbSec();
		return toHeure2(d1-d2);
	}
	
	public int diviserHeures(Heure h2){
		int d1 = this.getNbSec();
		int d2 = h2.getNbSec();
		return  d1/d2;
	}
	
	public int comparerHeure(Heure h2){
		int nbSec1 = this.getNbSec();
		int nbSec2 = h2.getNbSec();
		
		if(nbSec1==nbSec2){
			return 0;
		}else{
			if(nbSec1<nbSec2){
				return -1;
			}else{
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

	public int getNbSec() {
		return nbSec;
	}

	public void setNbSec(int nbSec) {
		this.nbSec = nbSec;
	}
	
	
	
}
