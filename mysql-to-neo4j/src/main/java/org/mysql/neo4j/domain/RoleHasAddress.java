package org.mysql.neo4j.domain;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

@RelationshipEntity(type = "HAS_ADDRESS")
public class RoleHasAddress {
	@GraphId Long id;
	
	@StartNode CityNeo city;
    @EndNode AddressNeo address;
    
    String name;
    
    public RoleHasAddress() {
    	
    }
    
    public RoleHasAddress(CityNeo city, AddressNeo address, String roleName) {
    	this.city = city;
    	this.address = address;
    	this.name = roleName;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CityNeo getCity() {
		return city;
	}

	public void setCity(CityNeo city) {
		this.city = city;
	}

	public AddressNeo getAddress() {
		return address;
	}

	public void setAddress(AddressNeo address) {
		this.address = address;
	}

	@Override
    public String toString() {
        return String.format("%s %s %s", city, name, address);
    }
}
