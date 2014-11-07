package alize.eole.modele;

import java.util.Date;

import org.testng.annotations.Test;

import alize.commun.Heure;

public class FeuilleDeServiceTest {

  @Test
  public void ajouterService() {
	  	 PeriodeDeConduite pdc1 = new PeriodeDeConduite( new Heure(12,58), new Heure(15,02), 12, new Arret("Capucin"), new Arret("Moliere"));
		 PeriodeDeConduite pdc2 = new PeriodeDeConduite( new Heure(15,38), new Heure(18,29), 15, new Arret("Jean XXIII"), new Arret("Molière"));
		 Service s1 = new Service(512);
		 s1.ajouterPeriodeDeConduite(pdc1);
		 s1.ajouterPeriodeDeConduite(pdc2);
		 
		 PeriodeDeConduite pdc1bis = new PeriodeDeConduite( new Heure(13,1), new Heure(15,29), 12, new Arret("FMB"), new Arret("Moliere"));
		 PeriodeDeConduite pdc2bis = new PeriodeDeConduite( new Heure(20,4), new Heure(0,2), 15, new Arret("Jean Vilar"), new Arret("Jean moulin"));
		 Service s2 = new Service(512);
		 s2.ajouterPeriodeDeConduite(pdc1bis);
		 s2.ajouterPeriodeDeConduite(pdc2bis);
		 
		 
		 
		 FeuilleDeService fds = new FeuilleDeService("Jaune");
		 fds.ajouterService(s1);
		 fds.ajouterService(s2);
		 System.out.println(fds.toString());
		 
		 
		 
  }
}
