# MySQL to Neo4j

The goal of this java project is convert a MySQL database into another Neo4j database.

Requirements
------------
    - Eclipse Luna
    - Latest Java 1.8
    - spring 2.5.6
    - spring framework 4.0.0.RELEASE
    - neo4j 2.1.8
    - spring data neo4j 3.1.2.RELEASE

It creates a hierarchical tree graph database. On the top level exist a node labeled as Root, on the second level are the countries labeled as Country. Every country has cities that is the third level labeled as City. And down, for every city, there are a subsets of addresses labeled as Address. The relationships should be,

(root)-[:IS_ROOT]->(country)-[:HAS_CITY]->(city)-[:HAS_ADDRESS]->(address)

Later, using Spring Data Neo4j repositories, I query the database with success.

I use JDBC for MySQL database and Spring Data Neo4j for Neo4j database, as mentioned before.

The project includes corresponding JUnit unit test that so far and such it is implemented it returns a valid result. The order of execution is first PlacesImporterTest to create the database and secondly PlacesDbImporter*Test in the order you want.