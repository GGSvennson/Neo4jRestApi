package org.mysql.neo4j.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import org.mysql.neo4j.dao.CityDAO;
import org.mysql.neo4j.entity.City;

@Controller("cityManager")
public class CityManagerImpl implements CityManager {

	@Autowired
	private CityDAO cityDAO;
	
	public void setCityDAO(CityDAO cityDAO) {
		this.cityDAO = cityDAO;
	}

	@Transactional
	public List<City> findAllCityByCountry(int countryId) {
		// TODO Auto-generated method stub
		return cityDAO.findCitiesByCountry(countryId);
	}

}
