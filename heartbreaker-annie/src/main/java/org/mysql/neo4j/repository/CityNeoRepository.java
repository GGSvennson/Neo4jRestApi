package org.mysql.neo4j.repository;

import org.mysql.neo4j.domain.CityNeo;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository; 
import org.springframework.stereotype.Repository; 

@Repository
public interface CityNeoRepository extends GraphRepository<CityNeo> {
	
	@Query("MATCH (a)-[r {__type__:'RoleHasCity'}]->(b) WHERE a.name={0} RETURN b")
	public Iterable<CityNeo> getCitiesOfCountry(String countryName);
}
