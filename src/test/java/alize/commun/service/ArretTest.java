package alize.commun.service;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.Test;



@ContextConfiguration(locations = {"/spring-config.xml"})
public class ArretTest {

	@Autowired
	private DSLContext context;
	
  @Test
  public void ajouterArretServiceTest() {
   ArretService a1 = new ArretService(context); 
   System.out.println("---------------------->"+context);
   assert(a1.ajouterArret("CAP", "CAPUCIN", true, false, false, 12, false)==true);
   
  }
}
