package org.neo4j.examples.server;

import org.neo4j.examples.server.Relation;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Ignore;

public class RelationTest {
    
    public RelationTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception {
        System.out.println("* RelationTest: @BeforeClass method");
    }
    
    @AfterClass
    public static void tearDownClass() throws Exception {
        System.out.println("* RelationTest: @AfterClass method");
    }
    
    @Before
    public void setUp() {
        System.out.println("* RelationTest: @Before method");
    }

    @After
    public void tearDown() {
        System.out.println("* RelationTest: @After method");
    }

    /**
     * Test of toJsonCollection method, of class Relation.
     */
    @Test
    public void testToJsonCollection() {
        System.out.println("testToJsonCollection()");
        Relation instance = new Relation("singer", Relation.OUT);
        String actualResult = "";
        String result = instance.toJsonCollection();
        assertNotSame(result, actualResult);
    }
    
    /**
     * Test of setType method, of class Relation.
     */
    @Ignore
    @Test
    public void testSetType() {
        System.out.println("testSetType()");
        Relation instance = new Relation(null, null);
        instance.setDirection(Relation.OUT);
        instance.setType("singer");
    }
    
    /**
     * Test of setDirection method, of class Relation.
     */
    @Ignore
    @Test
    public void testSetDirection() {
        System.out.println("testSetDirection()");
        Relation instance = new Relation("singer");
        instance.setDirection(Relation.OUT);
    }
}
