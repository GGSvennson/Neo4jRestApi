package org.mysql.neo4j.dao;

import java.util.List;

import org.mysql.neo4j.entity.Country;

public interface CountryDAO {

	public List<Country> findAllCountries();
}
