package org.mysql.neo4j.domain;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

@RelationshipEntity(type = "HAS_CITY")
public class RoleHasCity {
	@GraphId Long id;
	
	@StartNode CountryNeo country;
    @EndNode CityNeo city;
    
    String name;
    
    public RoleHasCity() {
    	
    }
    
    public RoleHasCity(CountryNeo country, CityNeo city, String roleName) {
    	this.country = country;
    	this.city = city;
    	this.name = roleName;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CountryNeo getCountry() {
		return country;
	}

	public void setCountry(CountryNeo country) {
		this.country = country;
	}

	public CityNeo getCity() {
		return city;
	}

	public void setCity(CityNeo city) {
		this.city = city;
	}

	@Override
    public String toString() {
        return String.format("%s %s %s", city, name, country);
    }
}
