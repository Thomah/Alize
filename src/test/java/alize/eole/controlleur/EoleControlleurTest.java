package alize.eole.controlleur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Test
@ContextConfiguration(locations = {"/test-context.xml"})
public class EoleControlleurTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private EoleControlleur eoleControlleur;
	
	@BeforeClass
	public void setup() {
	}
	
	@Test
	public void getListVoiesTest()
	{
		System.out.println(eoleControlleur.getListVoies(1));
	}
	
}
