package org.mysql.neo4j.domain;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

@RelationshipEntity(type = "IS_ROOT")
public class RoleIsRoot {
	@GraphId Long id;
	
	@StartNode RootNeo root;
    @EndNode CountryNeo country;
    
    String name;
    
    public RoleIsRoot() {
    	
    }
    
    public RoleIsRoot(RootNeo root, CountryNeo country, String name) {
    	this.root = root;
    	this.country = country;
    	this.name = name;
    }

	public RootNeo getRoot() {
		return root;
	}
	
	public void setRoot(RootNeo root) {
		this.root = root;
	}
	
	public CountryNeo getCountry() {
		return country;
	}
	
	public void setCountry(CountryNeo country) {
		this.country = country;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
    public String toString() {
        return String.format("%s %s %s", root, name, country);
    }
}
