package org.mysql.neo4j.dao;

import java.util.List;

import org.mysql.neo4j.entity.Address;

public interface AddressDAO {

	public List<Address> findAddressesByCity(int cityId);
}
