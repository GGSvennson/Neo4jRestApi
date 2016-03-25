package org.neo4j.examples.server.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    org.neo4j.examples.server.CreateSimpleGraph.class,
    org.neo4j.examples.server.Relation.class,
    org.neo4j.examples.server.TraversalDefinition.class
    })
public class JUnit4TestSuite {
    
}
