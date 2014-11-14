package alize.nau.service;

import static org.testng.Assert.*;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Test
@ContextConfiguration(locations = {"classpath:**/WEB-INF/alize-servlet.xml"})
public class DOMServiceImplTest {

	@Autowired
	private DOMService domService;

	@BeforeClass
	public void setup() {
		domService = new DOMServiceImpl();
	}

	@Test
	public void testImporterReseau() {
		assertNotNull(getClass().getResource("/reseaux.xml"), "Test file missing");
		domService.importerReseau(new File(getClass().getResource("/reseaux.xml").getFile()));
	}
	
}
