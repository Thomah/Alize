package alize.nau.service;

import static org.testng.Assert.*;

import java.io.File;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Test
@ContextConfiguration(locations = {"/test-context.xml"})
public class DOMServiceImplTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private DOMService domService;
	
	@Autowired
	private DSLContext dsl;
	
	@BeforeClass
	public void setup() {
		domService = new DOMServiceImpl();
		domService.setDsl(dsl);
	}

	@Test
	public void testImporterReseau() {
		assertNotNull(getClass().getResource("/reseaux.xml"), "Test file missing");
		domService.importerReseau(new File(getClass().getResource("/reseaux.xml").getFile()));
	}
	
	@AfterClass
	public void destroy() {
		dsl.execute("SET FOREIGN_KEY_CHECKS=0;");
		dsl.truncate(alize.commun.modele.tables.Transition.TRANSITION).execute();
		dsl.truncate(alize.commun.modele.tables.Periodedeconduite.PERIODEDECONDUITE).execute();
		dsl.truncate(alize.commun.modele.tables.LigneVoie.LIGNE_VOIE).execute();
		dsl.truncate(alize.commun.modele.tables.Voie.VOIE).execute();
		dsl.truncate(alize.commun.modele.tables.Terminus.TERMINUS).execute();
		dsl.truncate(alize.commun.modele.tables.Depot.DEPOT).execute();
		dsl.truncate(alize.commun.modele.tables.Associationconducteurservice.ASSOCIATIONCONDUCTEURSERVICE).execute();
		dsl.truncate(alize.commun.modele.tables.Service.SERVICE).execute();
		dsl.truncate(alize.commun.modele.tables.Feuilledeservice.FEUILLEDESERVICE).execute();
		dsl.truncate(alize.commun.modele.tables.Vehicule.VEHICULE).execute();
		dsl.truncate(alize.commun.modele.tables.Reseau.RESEAU).execute();
		dsl.truncate(alize.commun.modele.tables.Phase.PHASE).execute();
		dsl.truncate(alize.commun.modele.tables.Ligne.LIGNE).execute();
		dsl.truncate(alize.commun.modele.tables.Intervalle.INTERVALLE).execute();
		dsl.truncate(alize.commun.modele.tables.Conducteur.CONDUCTEUR).execute();
		dsl.truncate(alize.commun.modele.tables.Arret.ARRET).execute();
		dsl.execute("SET FOREIGN_KEY_CHECKS=1;");
	}
}
