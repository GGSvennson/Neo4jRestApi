package com.primefaces.hibernate.Idao;

import com.primefaces.hibernate.generic.GenericDao;
import com.primefaces.hibernate.model.Address;
import com.primefaces.hibernate.model.City;
import com.primefaces.hibernate.model.Country;
import com.primefaces.hibernate.model.Employees;
import java.util.List;

public interface IAddressDAO extends GenericDao<Address, Short> {
    
    public void create(Country country, City city, Address address);
    public void addToEmmployees(Address address, List<Employees> empsList);
    public Address findByName(String address, String address2);
    public Address findOfEmployee(Employees employee);
    public List<Address> findFromCity(City city);
    public List<Address> list();
}
