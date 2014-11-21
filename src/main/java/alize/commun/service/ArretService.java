package alize.commun.service;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;


import org.springframework.test.context.ContextConfiguration;



@ContextConfiguration(locations = {"/spring-config.xml"})
public class ArretService {

	private DSLContext context;
	public ArretService(DSLContext context) {
		this.context=context;
	}

	public Boolean ajouterArret(String id, String nom, Boolean estCommercial, Boolean estEntreeSortieDepot, Boolean estOccupe, int tempsImmobilisation, Boolean estLieuEchangeConducteur){
		//Assert.assertNotNull(context);
		
		try{
//			alize.commun.modele.tables.Arret a = new alize.commun.modele.tables.Arret();
//			ArretRecord aRec = context.newRecord(a);
//			aRec.setId(id);
//			aRec.setNom(nom);
//			aRec.setEstcommercial((byte) (estCommercial?1:0));
//			aRec.setEstentreesortiedepot((byte) (estEntreeSortieDepot?1:0));
//			aRec.setEstoccupe((byte) (estOccupe?1:0));
//			aRec.setTempsimmobilisation(100);
//			aRec.setEstlieuechangeconducteur((byte) (estLieuEchangeConducteur?1:0));
//			aRec.store();
			return true;
		}catch(Exception e){
			return false;
		}
	}
}
