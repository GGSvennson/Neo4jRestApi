package org.mysql.neo4j.service;

import java.util.List;

import org.mysql.neo4j.entity.City;

public interface CityManager {

	public List<City> findAllCityByCountry(int countryId);
}
