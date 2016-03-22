package org.mysql.neo4j.placesimport;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.mysql.neo4j.domain.CityNeo;
import org.mysql.neo4j.domain.CountryNeo;
import org.mysql.neo4j.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PlacesDbQueries {

    @Autowired
    @Lazy
    AddressNeoRepository addressRepo;
	
    @Autowired
    @Lazy
    CityNeoRepository cityRepo;
	
    @Autowired
    @Lazy
    CountryNeoRepository countryRepo;
    
    @Autowired
    @Lazy
    RootRepository rootRepo;
    
    public PlacesDbQueries() {
    	
    }
    
    public List<CountryNeo> findAllCountries() {
    	Iterable<CountryNeo> result = countryRepo.getAllCountries();
    	List<CountryNeo> list = new ArrayList<CountryNeo>();
    	Iterator<CountryNeo> it = result.iterator();
    	while(it.hasNext()) {
    		list.add(it.next());
    	}
    	return list;
    }
    
    public CountryNeo findCountryById(String id) {
    	return countryRepo.getCountryFromId(id);
    }
    
    public CountryNeo findCountryByName(String name) {
    	return countryRepo.getCountryFromName(name);
    }
    
    public List<CityNeo> findCitiesByCountryName(String countryName) {
    	Iterable<CityNeo> result = cityRepo.getCitiesOfCountry(countryName);
    	List<CityNeo> list = new ArrayList<CityNeo>();
    	Iterator<CityNeo> it = result.iterator();
    	while(it.hasNext()) {
    		list.add(it.next());
    	}
    	return list;
    }
}
