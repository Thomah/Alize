package alize.eole.modele;

import java.util.ArrayList;
import java.util.Date;

/**
 * @name FeuilleDeService
 * @author Cyril [CS]
 * @date 23 oct. 2014
 * @version 1
 */
public class FeuilleDeService {

	
	private String couleur;
	private Date debut;
	private Date fin;
	private ArrayList<Service> ensembleService;
	
	/**
	 * @name FeuilleDeService
	 * @author Cyril [CS]
	 * @date 3 nov. 2014
	 * @version 1
	 */
	public FeuilleDeService(String couleur, Date debut, Date fin) {
		this.couleur = couleur;
		this.debut = debut;
		this.fin = fin;
		this.ensembleService = new ArrayList<Service>();;
	}
	
	/**
	 * @name FeuilleDeService
	 * @author Cyril [CS]
	 * @date 3 nov. 2014
	 * @version 1
	 */
	public FeuilleDeService(String couleur) {
		this.couleur = couleur;
		this.ensembleService = new ArrayList<Service>();;
	}
	
	public void ajouterService(Service service){
		this.ensembleService.add(service);
	}

	
	public String toString(){
		String s = new String();
		s+="Feuille de service : " + couleur +"\n**************************\n";
		for(Service service : this.ensembleService){
			s+= service.toString() + "\n";
		}
		return s;
	}
	
	
}
