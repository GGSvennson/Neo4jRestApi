package org.mysql.neo4j.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import org.mysql.neo4j.dao.CountryDAO;
import org.mysql.neo4j.entity.Country;

@Controller("countryManager")
public class CountryManagerImpl implements CountryManager {

	@Autowired
	private CountryDAO countryDAO;
	
	public void setCountryDAO(CountryDAO countryDAO) {
		this.countryDAO = countryDAO;
	}

	@Transactional
	public List<Country> findAllCountry() {
		// TODO Auto-generated method stub
		return countryDAO.findAllCountries();
	}

}
