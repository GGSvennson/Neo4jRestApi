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

import org.neo4j.graphdb.Direction;

@NodeEntity
public class CityNeo extends Place {

	@RelatedTo(type = "HAS_ADDRESS", direction = Direction.OUTGOING)
	Set<AddressNeo> addresses;
	
    @Fetch @RelatedToVia(type = "HAS_CITY", direction = Direction.INCOMING)
	Iterable<RoleHasCity> roles;
	
    public CityNeo() {
    }

    public CityNeo(String name) {
        super(name, null);
    }
    
    public CityNeo(String name, String id) {
        super(name, id);
    }

    public Set<AddressNeo> getAddresses() {
        return addresses;
    }
    
    public void hasAddress(AddressNeo address) {
    	if(addresses == null)
    		addresses = new HashSet<AddressNeo>();
    	addresses.add(address);
    }
    
    public RoleHasAddress hasAddress(AddressNeo address, String roleName) {
    	return new RoleHasAddress(this, address, roleName);
    }
    
    public Collection<RoleHasCity> getRoles() { 
    	Iterable<RoleHasCity> allRoles = roles;
		return allRoles == null ? Collections.<RoleHasCity>emptyList() : IteratorUtil.asCollection(allRoles);
    }

}
