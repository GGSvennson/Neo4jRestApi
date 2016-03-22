package org.mysql.neo4j.dao;

import java.util.List;

import org.mysql.neo4j.entity.City;

public interface CityDAO {

	public List<City> findCitiesByCountry(int countryId);
}
