package com.primefaces.hibernate.Idao;

import com.primefaces.hibernate.generic.GenericDao;
import com.primefaces.hibernate.model.Address;
import com.primefaces.hibernate.model.City;
import com.primefaces.hibernate.model.Country;
import java.util.List;

public interface ICityDAO extends GenericDao<City, Short> {
    
    public void create(Country country, City city);
    public City findByName(String name);
    public City findFromAddress(Address address);
    public List<City> findOfCountry(Country country);
    public List<City> list();
}
