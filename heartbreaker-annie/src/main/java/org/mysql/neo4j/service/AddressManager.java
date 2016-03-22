package org.mysql.neo4j.service;

import java.util.List;

import org.mysql.neo4j.entity.Address;

public interface AddressManager {

	public List<Address> findAllAddressByCity(int cityId);
}
