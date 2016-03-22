package org.mysql.neo4j.placesimport;

import org.mysql.neo4j.domain.AddressNeo;
import org.mysql.neo4j.domain.CityNeo;
import org.mysql.neo4j.domain.CountryNeo;
import org.mysql.neo4j.domain.RoleHasAddress;
import org.mysql.neo4j.domain.RoleHasCity;
import org.mysql.neo4j.domain.RoleIsRoot;
import org.mysql.neo4j.domain.RootNeo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

@Controller("importer")
public class PlacesDbImportController {
    
    @Autowired
    Neo4jTemplate template;
    
	public PlacesDbImportController() {
    	
    }

	@Transactional
    public RootNeo createGraphDb() {
		RootNeo root = new RootNeo("Root", "0");
		root.addLabel("_Root");
		
		AddressNeo address1 = new AddressNeo("391 Callao Drive", "1");
		address1.setPostalCode("34021");
		address1.addLabel("_Address");
		template.save(address1);
		
		CityNeo city1 = new CityNeo("Toulouse", "1");
		city1.addLabel("_City");
		RoleHasAddress roleHasAddress1 = city1.hasAddress(address1, "IS_ADDRESS_IN");
		template.save(city1);
		template.save(roleHasAddress1);
		
		CountryNeo country1 = new CountryNeo("France", "1");
		country1.addLabel("_Country");
		RoleHasCity roleHasCity1 = country1.hasCity(city1, "IS_CITY_FROM");
		template.save(country1);
		template.save(roleHasCity1);
		
		AddressNeo address2 = new AddressNeo("939 Probolinggo Loop", "2");
		address2.setPostalCode("4166");
		address2.addLabel("_Address");
		template.save(address2);
		
		CityNeo city2 = new CityNeo("A Corua (La Corua)", "2");
		city2.addLabel("_City");
		RoleHasAddress roleHasAddress2 = city2.hasAddress(address2, "IS_ADDRESS_IN");
		template.save(city2);
		template.save(roleHasAddress2);
		
		CountryNeo country2 = new CountryNeo("Spain", "2");
		country2.addLabel("_Country");
		country2.hasCity(city2);
		RoleHasCity roleHasCity2 = country2.hasCity(city2, "IS_CITY_FROM");
		template.save(country2);
		template.save(roleHasCity2);
		
		template.save(root);
		
		RoleIsRoot role1 = root.isRoot(country1, "IS_ROOT_OF");
		template.save(role1);
		RoleIsRoot role2 = root.isRoot(country2, "IS_ROOT_OF");
		template.save(role2);
		
        return root;
	}
}
