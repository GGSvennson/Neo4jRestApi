package org.mysql.neo4j.service;

import java.util.List;

import org.mysql.neo4j.entity.Country;

public interface CountryManager {
	
	public List<Country> findAllCountry();

}
