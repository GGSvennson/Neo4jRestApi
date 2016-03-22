package org.mysql.neo4j.placesimport;

import java.util.List;

import org.mysql.neo4j.entity.Address;
import org.mysql.neo4j.entity.City;
import org.mysql.neo4j.entity.Country;
import org.mysql.neo4j.service.AddressManager;
import org.mysql.neo4j.service.CityManager;
import org.mysql.neo4j.service.CountryManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

@Controller("client")
public class PlacesDbApiClient {
	
	private final static Logger logger = LoggerFactory.getLogger(PlacesDbApiClient.class);
	
	@Autowired AddressManager addressManager;
	@Autowired CityManager cityManager;
	@Autowired CountryManager countryManager;
	
	public PlacesDbApiClient() {
	}

	@Transactional
	public List<Country> readAllCountries() {
        logger.debug("Reading all countries.");
        return countryManager.findAllCountry();
    }
	
	@Transactional
	public List<City> readAllCitiesByCountry(Country country) {
		logger.debug("Reading all cities by " + country);
		return cityManager.findAllCityByCountry(country.getCountryId());
	}

	@Transactional
	public List<Address> readAllAddressesByCity(City city) {
		logger.debug("Reading all addresses by " + city);
		return addressManager.findAllAddressByCity(city.getCityId());
	}
}
