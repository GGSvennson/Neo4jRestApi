package org.mysql.neo4j.placesimport.test;

import org.mysql.neo4j.domain.RootNeo;
import org.neo4j.graphdb.GraphDatabaseService;
import org.mysql.neo4j.placesimport.PlacesDbImportController;
import org.mysql.neo4j.placesimport.PlacesDbQueries;
import org.mysql.neo4j.placesimport.PlacesImporter;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlacesImporterTest {

	public PlacesImporterTest() {
		
	}
	
	@BeforeClass
    public static void setUpClass() throws Exception {
    }
    
    @AfterClass
    public static void tearDownClass() throws Exception {
    }
	
	@Test
	public void testCreateDatabase() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

		GraphDatabaseService graphDatabase = ctx.getBean("graphDatabaseService", GraphDatabaseService.class);
		PlacesDbImportController importer = ctx.getBean("importer", PlacesDbImportController.class);
		PlacesDbQueries queries = ctx.getBean("queries", PlacesDbQueries.class);
		
		PlacesImporter placesImporter = new PlacesImporter(graphDatabase, importer, queries);
		RootNeo root = placesImporter.createDatabase();
		assertNotNull(root);
		
		ctx.close();
	}
	
}
