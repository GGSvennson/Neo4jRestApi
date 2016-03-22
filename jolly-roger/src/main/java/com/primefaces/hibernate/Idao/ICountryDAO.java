package com.primefaces.hibernate.Idao;

import com.primefaces.hibernate.generic.GenericDao;
import com.primefaces.hibernate.model.City;
import com.primefaces.hibernate.model.Country;
import java.util.List;

public interface ICountryDAO extends GenericDao<Country, Short> {
    
    public void addCities(Country country, List<City> cityList);
    public Country findByName(String name);
    public Country findFromCity(City city);
    public List<Country> list();
}
