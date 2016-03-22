package org.mysql.neo4j.repository;

import java.util.Collection;

import org.mysql.neo4j.domain.CityNeo;
import org.springframework.data.neo4j.annotation.Query;
//import org.springframework.data.neo4j.annotation.QueryResult;
import org.springframework.data.neo4j.repository.GraphRepository; 

public interface CityNeoRepository extends GraphRepository<CityNeo> {
	
}
