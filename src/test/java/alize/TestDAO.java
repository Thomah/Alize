package alize;

import java.util.List;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;



import alize.commun.modele.tables.Arret;
import alize.commun.modele.tables.daos.ArretDao;


@Test
@ContextConfiguration(locations = {"/spring-config.xml"})
public class TestDAO extends AbstractTestNGSpringContextTests  {
	
	@Autowired
	private DSLContext context;
	
	@Test
	public void testFetch() {
		Assert.assertNotNull(context);
		
//		IntervalleDao intervalleDao = new IntervalleDao(context.configuration());
//		List<Intervalle> intervallesById = intervalleDao.fetchById(0);
		
		Arret a = new Arret();
		
//		ArretRecord arret1 = context.newRecord(a);
//		arret1.setId("FMB");
//		arret1.setNom("Foch/Maison-Bleu");
//		arret1.setEstcommercial((byte)1);
//		arret1.setEstentreesortiedepot((byte)0);
//		arret1.setEstoccupe((byte)0);
//		arret1.setTempsimmobilisation(12);
//		arret1.setEstlieuechangeconducteur((byte)1);
//		arret1.store();
//		
//		ArretDao arretDao = new ArretDao(context.configuration());
//		List<alize.commun.modele.tables.pojos.Arret> arretByEstCommercial = arretDao.fetchByEstcommercial((byte)(1));
//	
//		System.out.println("\n\n\n***************************************\n\n\n"  + arretByEstCommercial.toString());
//		Assert.assertEquals(arretByEstCommercial.size(), 1);
//		arret1.delete();
//		System.out.println("\n\n\n***************************************\n\n\n"  + arretByEstCommercial.toString());
		
	}
	
}
