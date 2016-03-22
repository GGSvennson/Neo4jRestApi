package org.mysql.neo4j.placesimport.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mysql.neo4j.domain.CountryNeo;
import org.mysql.neo4j.placesimport.PlacesDbImportController;
import org.mysql.neo4j.placesimport.PlacesDbQueries;
import org.mysql.neo4j.placesimport.PlacesImporter;
import org.neo4j.graphdb.GraphDatabaseService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PlacesDbImporterGetCountryByIdTest {
	
	public PlacesDbImporterGetCountryByIdTest() {
		
	}
	
	@BeforeClass
    public static void setUpClass() throws Exception {
    }
    
    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Test
	public void testQueryDbGetCountryById() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

		GraphDatabaseService graphDatabase = ctx.getBean("graphDatabaseService", GraphDatabaseService.class);
		PlacesDbImportController importer = ctx.getBean("importer", PlacesDbImportController.class);
		PlacesDbQueries queries = ctx.getBean("queries", PlacesDbQueries.class);
		
		PlacesImporter placesImporter = new PlacesImporter(graphDatabase, importer, queries);
		CountryNeo country = placesImporter.queryDbGetCountryById("1");
		assertNotNull(country);
		
		ctx.close();
	}
}
