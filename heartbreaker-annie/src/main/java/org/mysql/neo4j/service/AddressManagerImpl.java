package org.mysql.neo4j.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import org.mysql.neo4j.dao.AddressDAO;
import org.mysql.neo4j.entity.Address;

@Controller("addressManager")
public class AddressManagerImpl implements AddressManager {

	@Autowired
	private AddressDAO addressDAO;

	public void setAddressDAO(AddressDAO addressDAO) {
		this.addressDAO = addressDAO;
	}

	@Transactional
	public List<Address> findAllAddressByCity(int cityId) {
		// TODO Auto-generated method stub
		return addressDAO.findAddressesByCity(cityId);
	}

}
