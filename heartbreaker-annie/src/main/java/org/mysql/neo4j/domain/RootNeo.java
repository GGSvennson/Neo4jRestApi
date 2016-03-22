package org.mysql.neo4j.domain;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@NodeEntity
public class RootNeo extends Place {
	
	@RelatedTo(type = "IS_ROOT")
	Set<CountryNeo> countries = new HashSet<CountryNeo>();
	
    public RootNeo() {
		
	}
	
	public RootNeo(String id, String name) {
		super(id, name);
	}

	public Set<CountryNeo> getCountries() {
		return countries;
	}

	public void isRoot(CountryNeo country) {
		this.countries.add(country);
	}
	
	public RoleIsRoot isRootOf(CountryNeo country, String roleName) {
		return new RoleIsRoot(this, country, roleName);
	}

}
