package org.mysql.neo4j.domain;

import java.util.Collection;
import java.util.Collections;

import org.neo4j.helpers.collection.IteratorUtil;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedToVia;

import static org.neo4j.graphdb.Direction.INCOMING;

@NodeEntity
public class AddressNeo extends Place {

	@Indexed
	String postalCode;

	@Fetch @RelatedToVia(type = "HAS_ADDRESS", direction = INCOMING)
	Iterable<RoleHasAddress> roles;
	
	public AddressNeo() {		
	}
	
	public AddressNeo(String id, String name) {
		super(id, name);
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	
	public Collection<RoleHasAddress> getRoles() {
    	Iterable<RoleHasAddress> allRoles = roles;
		return allRoles == null ? Collections.<RoleHasAddress>emptyList() : IteratorUtil.asCollection(allRoles);
    }
}
