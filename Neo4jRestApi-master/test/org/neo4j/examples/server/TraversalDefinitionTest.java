package org.neo4j.examples.server;

import org.neo4j.examples.server.Relation;
import org.neo4j.examples.server.TraversalDefinition;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Ignore;

public class TraversalDefinitionTest {
    
    public TraversalDefinitionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception {
        System.out.println("* TraversalDefinitionTest: @BeforeClass method");
    }
    
    @AfterClass
    public static void tearDownClass() throws Exception {
        System.out.println("* TraversalDefinitionTest: @AfterClass method");
    }
    
    @Before
    public void setUp() {
        System.out.println("* TraversalDefinitionTest: @Before method");
    }

    @After
    public void tearDown() {
        System.out.println("* TraversalDefinitionTest: @After method");
    }
    
    @Test
    public void testToJson() {
        System.out.println("testToJson()");
        TraversalDefinition instance = new TraversalDefinition();
        instance.setUniqueness(TraversalDefinition.NODE);
        instance.setMaxDepth(10);
        instance.setReturnFilter(TraversalDefinition.ALL);
        instance.setRelationships(new Relation("singer", Relation.OUT));
        instance.setOrder(TraversalDefinition.DEPTH_FIRST);
        String actualResult = "";
        String result = instance.toJson();
        assertNotSame(actualResult, result);
    }
    
    @Ignore
    @Test
    public void testSetOrder() {
        System.out.println("testSetOrder()");
        TraversalDefinition instance = new TraversalDefinition();
        instance.setOrder(TraversalDefinition.DEPTH_FIRST);
    }
    
    @Ignore
    @Test
    public void testSetUniqueness() {
        System.out.println("setUniqueness");
        TraversalDefinition instance = new TraversalDefinition();
        instance.setUniqueness(TraversalDefinition.NODE);
    }
    
    @Ignore
    @Test
    public void testSetMaxDepth() {
        System.out.println("testSetMaxDepth()");
        TraversalDefinition instance = new TraversalDefinition();
        instance.setMaxDepth(1);
    }
    
    @Ignore
    @Test
    public void testSetReturnFilter() {
        System.out.println("testSetReturnFilter()");
        TraversalDefinition instance = new TraversalDefinition();
        instance.setReturnFilter(TraversalDefinition.ALL);
    }
    
    @Ignore
    @Test
    public void testSetRelationships() {
        System.out.println("testSetRelationships()");
        TraversalDefinition instance = new TraversalDefinition();
        instance.setRelationships(new Relation("singer", Relation.OUT));
    }
}
