package alize;

import java.util.List;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import alize.commun.modele.tables.daos.IntervalleDao;
import alize.commun.modele.tables.pojos.Intervalle;

@Test
@ContextConfiguration(locations = {"/test-context.xml"})
public class TestDAO extends AbstractTestNGSpringContextTests  {
	
	@Autowired
	private DSLContext dsl;
	
	@Test
	public void testFetch() {
		Assert.assertNotNull(dsl);
		
		IntervalleDao intervalleDao = new IntervalleDao(dsl.configuration());
		List<Intervalle> intervallesById = intervalleDao.fetchById(0);
		
		Assert.assertEquals(intervallesById.size(), 1);
	}
	
}
