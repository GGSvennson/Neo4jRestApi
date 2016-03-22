package org.mysql.neo4j.placesimport;

import org.mysql.neo4j.entity.*;
import org.mysql.neo4j.domain.AddressNeo;
import org.mysql.neo4j.domain.CityNeo;
import org.mysql.neo4j.domain.CountryNeo;
import org.mysql.neo4j.domain.RoleHasAddress;
import org.mysql.neo4j.domain.RoleHasCity;
import org.mysql.neo4j.domain.RoleIsRoot;
import org.mysql.neo4j.domain.RootNeo;
//import org.neo4j.graphdb.DynamicLabel;
//import org.neo4j.graphdb.Label;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Controller("importer")
public class PlacesDbImportController {

    private static final Logger logger = LoggerFactory.getLogger(PlacesDbImportController.class);
    
    @Autowired
    Neo4jTemplate template;
    
	@Autowired
    PlacesDbApiClient client;
	
	public PlacesDbImportController() {
    	
    }

	@Transactional
    public RootNeo createGraphDb() {
		RootNeo root = doImportRoot();
        return root;
	}
	
	@Transactional
	public RootNeo importRoot() {
		return doImportRoot();
	}
	
	private RootNeo doImportRoot() {
		logger.debug("Importing root");
		
		RootNeo root = new RootNeo("1", "Root");
		//Label myLabel = DynamicLabel.label("Root");
		root.addLabel("_MyRoot");
		root.addLabel("MyRoot");
				
		List<Country> data = client.readAllCountries();
		if (data.isEmpty()) throw new RuntimeException("Data for Root not found.");
		
		List<RoleIsRoot> roles = relateCountriesToRoot(root, data);
		System.out.println("Saving root " + root);
		template.save(root);
		for(RoleIsRoot role: roles){
        	template.save(role);
        }
		
		return root;
	}

	private List<RoleIsRoot> relateCountriesToRoot(RootNeo root, List<Country> data) {
		List<RoleIsRoot> roles = new ArrayList<RoleIsRoot>();
		for (Country country : data) {
			CountryNeo countryNeo = doImportCountryNeo(country);
            RoleIsRoot role = root.isRootOf(countryNeo, "IS_ROOT_OF");
            System.out.println("RoleIsRoot: " + role);
            roles.add(role);
        }
		
		return roles;
	}

    @Transactional
    public CountryNeo importCountryNeo(Country country) {
        return doImportCountryNeo(country);
    }

    private CountryNeo doImportCountryNeo(Country country) {
        logger.debug("Importing countryNeo");

        CountryNeo countryNeo = new CountryNeo(generateIndex(country.getCountryId()), country.getCountry());
        countryNeo.addLabel("_Countries");
        countryNeo.addLabel("Countries");
        System.out.println("new country: " + countryNeo);

        List<City> data = client.readAllCitiesByCountry(country);
        if (data.isEmpty()) throw new RuntimeException("Data for Country not found.");
        
        List<RoleHasCity> roles = relateCitiesToCountry(countryNeo, data);
        template.save(countryNeo);
        for(RoleHasCity role: roles){
        	template.save(role);
        }
        
        return countryNeo;
    }

    private List<RoleHasCity> relateCitiesToCountry(CountryNeo countryNeo, List<City> data) {
    	List<RoleHasCity> roles = new ArrayList<RoleHasCity>();
    	for (City city : data) {
            CityNeo cityNeo = doImportCityNeo(city);
            RoleHasCity role = countryNeo.hasCity(cityNeo, "IS_CITY_FROM");
            System.out.println("RoleHasCity: " + role);
            roles.add(role);
        }
    	
    	return roles;
    }

    @Transactional
    public CityNeo importCityNeo(City city) {
        return doImportCityNeo(city);
    }

    private CityNeo doImportCityNeo(City city) {
    	logger.debug("Importing cityNeo");
    	
    	CityNeo cityNeo = new CityNeo(generateIndex(city.getCityId()), city.getCity());
        cityNeo.addLabel("_Cities");
        cityNeo.addLabel("Cities");
        System.out.println("new city: " + cityNeo);

        List<Address> data = client.readAllAddressesByCity(city);
        if (data.isEmpty()) throw new RuntimeException("Data for City not found.");
        
        List<RoleHasAddress> roles = relateAddressesToCity(cityNeo, data);
    	template.save(cityNeo);
    	for(RoleHasAddress role: roles){
    		template.save(role);
    	}
        
        return cityNeo;
    }

    private List<RoleHasAddress> relateAddressesToCity(CityNeo cityNeo, List<Address> data) {
    	List<RoleHasAddress> roles = new ArrayList<RoleHasAddress>();
    	for(Address address: data) {
    		AddressNeo addressNeo = doImportAddressNeo(address);
    		RoleHasAddress role = cityNeo.hasAddress(addressNeo, "IS_ADDRESS_IN");
    		System.out.println("RoleHasAddress: " + role);
    		roles.add(role);
    	}
    	
    	return roles;
    }

    @Transactional
    public AddressNeo importAddressNeo(Address address) {
        return doImportAddressNeo(address);
    }

    private AddressNeo doImportAddressNeo(Address address) {
    	logger.debug("Importing addressNeo");

    	if (address == null) throw new RuntimeException("Address not found.");
    	
		AddressNeo addressNeo = new AddressNeo(generateIndex(address.getAddressId()), address.getAddress());
		addressNeo.addLabel("_Addresses");
		addressNeo.addLabel("Addresses");
		System.out.println("new address: " + addressNeo);
		
		addressNeo.setPostalCode(address.getPostalCode());
		template.save(addressNeo);
		
    	return addressNeo;
    }
    
    private String generateIndex(int index) {
    	return String.valueOf(index);
    }
}
