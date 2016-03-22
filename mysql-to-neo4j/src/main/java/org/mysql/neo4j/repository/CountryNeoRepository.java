package org.mysql.neo4j.repository;

import java.util.Collection;
import java.util.Collections;

import org.mysql.neo4j.domain.CountryNeo;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.annotation.QueryResult;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface CountryNeoRepository extends GraphRepository<CountryNeo> {
	
	@Query("MATCH (a)-[r {__type__:'RoleIsRoot'}]->(b) RETURN b")
	public Iterable<CountryNeo> getAllCountries();
	
	@Query("MATCH (a)-[r {__type__:'RoleIsRoot'}]-(b) WHERE b.id={0} RETURN b")
	public CountryNeo getCountryFromId(String id);
	
	@Query("MATCH (a)-[r {__type__:'RoleIsRoot'}]->(b) WHERE b.name={0} RETURN b")
	public CountryNeo getCountryFromName(String name);
}
