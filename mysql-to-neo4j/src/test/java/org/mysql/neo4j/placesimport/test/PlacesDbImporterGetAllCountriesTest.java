package org.mysql.neo4j.placesimport.test;

import java.util.List;

import org.mysql.neo4j.domain.CountryNeo;
import org.neo4j.graphdb.GraphDatabaseService;
import org.mysql.neo4j.placesimport.PlacesDbImportController;
import org.mysql.neo4j.placesimport.PlacesDbQueries;
import org.mysql.neo4j.placesimport.PlacesImporter;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlacesDbImporterGetAllCountriesTest {
	
	public PlacesDbImporterGetAllCountriesTest() {
		
	}
	
	@BeforeClass
    public static void setUpClass() throws Exception {
    }
    
    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Test
	public void testQueryDbGetAllCountries() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

		GraphDatabaseService graphDatabase = ctx.getBean("graphDatabaseService", GraphDatabaseService.class);
		PlacesDbImportController importer = ctx.getBean("importer", PlacesDbImportController.class);
		PlacesDbQueries queries = ctx.getBean("queries", PlacesDbQueries.class);
		
		PlacesImporter placesImporter = new PlacesImporter(graphDatabase, importer, queries);
		List<CountryNeo> countries = placesImporter.queryDbGetAllCountries();
		assertTrue(!countries.isEmpty());
		
		ctx.close();
	}
}
