package org.mysql.neo4j.domain;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@NodeEntity
public class RootNeo extends Place {
	
	@RelatedTo(type = "IS_ROOT", direction = Direction.OUTGOING)
	Set<CountryNeo> countries;
	
    public RootNeo() {
		
	}
	
	public RootNeo(String name) {
		super(name, null);
	}
	
	public RootNeo(String name, String id) {
		super(name, id);
	}

	public Set<CountryNeo> getCountries() {
		return countries;
	}

	public void isRoot(CountryNeo country) {
		if(countries == null)
			countries = new HashSet<CountryNeo>();
		countries.add(country);
	}
	
	public RoleIsRoot isRoot(CountryNeo country, String roleName) {
		return new RoleIsRoot(this, country, roleName);
	}

}
