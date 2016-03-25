package org.neo4j.examples.server;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;


import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.neo4j.examples.server.CreateSimpleGraph;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class CreateSimpleGraphTest {
    
    public CreateSimpleGraphTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception {
    }
    
    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    /**
     * Test of main method, of class CreateSimpleGraphTest.
     */
    @Test
    public void testMain() {
        System.out.println("* Neo4jRestAPITest: testMain()");
        String[] args = new String[]{};
        CreateSimpleGraph.main(args);
    }
    
    /**
     * Test of testTestDatabaseAuthentication method, of class CreateSimpleGraphTest.
     */
    @Test
    public void testTestDatabaseAuthentication() {
        System.out.println("* Neo4jRestAPITest: testTestDatabaseAuthentication()");
        Client client = ClientBuilder.newClient();
        HttpAuthenticationFeature authFeature = HttpAuthenticationFeature.basic("myUsername", "myPassword");
        client.register(authFeature);
        WebTarget expected = client.target("http://localhost:7474/db/data/");
        WebTarget actual = CreateSimpleGraph.testDatabaseAuthentication();
        assertEquals(0, expected.getUri().toString().compareTo(actual.getUri().toString()));
    }
}
