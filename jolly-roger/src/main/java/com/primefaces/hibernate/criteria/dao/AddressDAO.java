package com.primefaces.hibernate.criteria.dao;

import com.primefaces.hibernate.Idao.IAddressDAO;
import com.primefaces.hibernate.generic.GenericDaoImpl;
import com.primefaces.hibernate.model.Address;
import com.primefaces.hibernate.model.City;
import com.primefaces.hibernate.model.Country;
import com.primefaces.hibernate.model.Employees;
import com.primefaces.hibernate.util.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

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
            Transaction tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(Address.class)
                    .add(Restrictions.eq("address", address));
            if(null != address2)
                criteria.add(Restrictions.eq("address2", address2));
            result = criteria.list();
            tx.commit();
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
            Transaction tx = session.beginTransaction();
            List<Short> ids = session.createCriteria(Employees.class, "e")
                    .createAlias("e.address", "address")
                    .add(Restrictions.idEq(employee.getId()))
                    .setProjection(Property.forName("address.addressId"))
                    .list();
            Criteria criteria = session.createCriteria(Address.class)
                    .add(Restrictions.in("addressId", ids));
            address = (Address) criteria.uniqueResult();
            tx.commit();
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
            Transaction tx = session.beginTransaction();
            addressList = (List<Address>) session.createCriteria(Address.class)
                    .add(Restrictions.eq("city", city))
                    .list();
            tx.commit();
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
            Transaction tx = session.beginTransaction();
            addressList = session.createCriteria(Address.class).list();
            tx.commit();
        } catch (RuntimeException e) {
            LOG.error("AddressDAO - listAddress() failed, " + e.getMessage(), e);
        } finally {
            session.flush();
            session.close();
        }
        
        return addressList;
    }
}
