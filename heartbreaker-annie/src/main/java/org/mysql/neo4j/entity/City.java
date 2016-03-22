package org.mysql.neo4j.entity;

import java.util.List;

/**
 * The persistent class for the city database table.
 * 
 */
public class City {

	private int cityId;
	private String city;

	//bi-directional many-to-one association to Address
	private List<Address> addresses;

	//bi-directional many-to-one association to Country
	private Country country;

	public City() {
	}

	public int getCityId() {
		return this.cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public List<Address> getAddresses() {
		return this.addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public Address addAddress(Address address) {
		getAddresses().add(address);
		address.setCity(this);

		return address;
	}

	public Address removeAddress(Address address) {
		getAddresses().remove(address);
		address.setCity(null);

		return address;
	}

	public Country getCountry() {
		return this.country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

}