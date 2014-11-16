package alize.nau.service;

import static org.testng.Assert.*;

import java.io.File;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
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
		System.out.println("dsl @BeforeClass : " + dsl);
	}

	@Test
	public void testImporterReseau() {
		assertNotNull(getClass().getResource("/reseaux.xml"), "Test file missing");
		domService.importerReseau(new File(getClass().getResource("/reseaux.xml").getFile()));
	}
	
}
