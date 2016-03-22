package com.primefaces.hibernate.dao;

import com.primefaces.hibernate.Idao.IAddressDAO;
import com.primefaces.hibernate.generic.GenericDaoImpl;
import com.primefaces.hibernate.model.Address;
import com.primefaces.hibernate.model.City;
import com.primefaces.hibernate.model.Country;
import com.primefaces.hibernate.model.Employees;
import com.primefaces.hibernate.util.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.apache.log4j.Logger;

public class AddressDAO extends GenericDaoImpl<Address, Short> implements IAddressDAO {
    
    private static final Logger LOG = Logger.getLogger(AddressDAO.class);
    
    @Override
    public void create(Country country, City city, Address address) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            city.setCountry(country);
            address.setCity(city);
            session.save(address);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            LOG.error("AddressDAO - createAddress() failed, " + e.getMessage(), e);
        } finally {
            session.flush();
            session.close();
        }
    }
    
    @Override
    public void addToEmmployees(Address address, List<Employees> empsList) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            empsList.stream().forEach((emp) -> {
                emp.setAddress(address);
                session.saveOrUpdate(emp);
            });
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            LOG.error("AddressDAO - addAddressToEmployees() failed, " + e.getMessage(), e);
        } finally {
            session.flush();
            session.close();
        }
    }
    
    @Override
    public Address findByName(String address, String address2) {
        List<Address> result = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            String hql = "from Address a where a.address = :address and a.address2 = :address2";
            result = session.createQuery(hql)
                    .setString("address", address)
                    .setString("address2", address2)
                    .list();
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            LOG.error("AddressDAO - findAddressByName() failed, " + e.getMessage(), e);
        } finally {
            session.flush();
            session.close();
        }
        
        if(result.size() > 0)
            return result.get(0);
        
        return null;
    }
    
    @Override
    public Address findOfEmployee(Employees employee) {
        Address address = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            List<Employees> emps = session.createQuery("from Employees e join fetch e.address where e.id = :id")
                                .setInteger("id", employee.getId())
                                .list();
            if(emps.size() > 0) {
                Employees emp = emps.get(0);
                address = emp.getAddress();
            }
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            LOG.error("AddressDAO - findAddressOfEmployee() failed, " + e.getMessage(), e);
        } finally {
            session.flush();
            session.close();
        }
        
        return address;
    }
    
    @Override
    public List<Address> findFromCity(City city) {
        List<Address> addressList = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            addressList = session.createQuery("from Address a where a.city = :city")
                            .setEntity("city", city)
                            .list();
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            LOG.error("AddressDAO - findAddressFromCity() failed, " + e.getMessage(), e);
        } finally {
            session.flush();
            session.close();
        }
        
        return addressList;
    }
    
    @Override
    public List<Address> list() {
        List<Address> addressList = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            addressList = session.createQuery("from Address").list();
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            LOG.error("AddressDAO - listAddress() failed, " + e.getMessage(), e);
        } finally {
            session.flush();
            session.close();
        }
        
        return addressList;
    }
}
