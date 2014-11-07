package alize.eole.modele;

import java.time.LocalDateTime;

import org.testng.annotations.Test;

import alize.commun.Constantes;
import alize.commun.Heure;

public class ServiceTest {

  @Test
  public void ajouterPeriodeDeConduite() {
		 
	 PeriodeDeConduite pdc1 = new PeriodeDeConduite( new Heure(12,58), new Heure(15,02), 12, new Arret("Capucin"), new Arret("Molière"));
	 PeriodeDeConduite pdc2 = new PeriodeDeConduite( new Heure(15,38), new Heure(18,29), 15, new Arret("Jean XXIII"), new Arret("Molière"));
	 
	 Service s1 = new Service(512);
	 s1.ajouterPeriodeDeConduite(pdc1);
	 s1.ajouterPeriodeDeConduite(pdc2);
	 System.out.println(s1.toString());
  }
}
