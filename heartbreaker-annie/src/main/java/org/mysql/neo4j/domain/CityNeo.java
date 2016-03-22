package org.mysql.neo4j.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.neo4j.helpers.collection.IteratorUtil;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.annotation.RelatedToVia;

import static org.neo4j.graphdb.Direction.INCOMING;

@NodeEntity
public class CityNeo extends Place {

	@RelatedTo(type = "HAS_ADDRESS")
	Set<AddressNeo> addresses = new HashSet<AddressNeo>();
	
    @Fetch @RelatedToVia(type = "HAS_CITY", direction = INCOMING)
	Iterable<RoleHasCity> roles;
	
    public CityNeo() {
    }

    public CityNeo(String id, String name) {
        super(id, name);
    }

    public Set<AddressNeo> getAddresses() {
        return addresses;
    }
    
    public void hasAddress(AddressNeo address) {
    	this.addresses.add(address);
    }
    
    public RoleHasAddress hasAddress(AddressNeo address, String roleName) {
    	return new RoleHasAddress(this, address, roleName);
    }
    
    public Collection<RoleHasCity> getRoles() { 
    	Iterable<RoleHasCity> allRoles = roles;
		return allRoles == null ? Collections.<RoleHasCity>emptyList() : IteratorUtil.asCollection(allRoles);
    }

}
