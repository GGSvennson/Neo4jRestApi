package org.mysql.neo4j.repository;

import org.mysql.neo4j.domain.CountryNeo;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository; 
import org.springframework.stereotype.Repository; 

@Repository
public interface CountryNeoRepository extends GraphRepository<CountryNeo> {
	
	@Query("MATCH (a)-[r {__type__:'RoleIsRoot'}]->(b) RETURN b")
	public Iterable<CountryNeo> getAllCountries();
	
	@Query("MATCH (a)-[r {__type__:'RoleIsRoot'}]-(b) WHERE b.id={0} RETURN b")
	public CountryNeo getCountryFromId(String id);
	
	@Query("MATCH (a)-[r {__type__:'RoleIsRoot'}]->(b) WHERE b.name={0} RETURN b")
	public CountryNeo getCountryFromName(String name);
}
