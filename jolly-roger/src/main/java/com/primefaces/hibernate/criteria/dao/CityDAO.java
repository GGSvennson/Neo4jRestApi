package com.primefaces.hibernate.criteria.dao;

import com.primefaces.hibernate.Idao.ICityDAO;
import com.primefaces.hibernate.generic.GenericDaoImpl;
import com.primefaces.hibernate.model.Address;
import com.primefaces.hibernate.model.City;
import com.primefaces.hibernate.model.Country;
import com.primefaces.hibernate.util.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

public class CityDAO extends GenericDaoImpl<City, Short> implements ICityDAO {
    
    private static final Logger LOG = Logger.getLogger(CityDAO.class);
    
    @Override
    public void create(Country country, City city) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            city.setCountry(country);
            session.save(city);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            LOG.error("CityDAO - createCity() failed, " + e.getMessage(), e);
        } finally {
            session.flush();
            session.close();
        }
    }
    
    @Override
    public City findByName(String name) {
        List<City> cities = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction tx = session.beginTransaction();        
            Criteria criteria = session.createCriteria(City.class)
                    .add(Restrictions.eq("city", name));
            cities = criteria.list();        
            tx.commit();
        } catch (RuntimeException e) {
            LOG.error("CityDAO - findCityByName() failed, " + e.getMessage(), e);
        } finally {
            session.flush();
            session.close();
        }
        
        if(cities.size() > 0)
            return cities.get(0);
        
        return null;
    }
    
    @Override
    public City findFromAddress(Address address) {
        City city = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction tx = session.beginTransaction();        
            List<Short> cityIds = session.createCriteria(Address.class, "a")
                    .createAlias("a.city", "city")
                    .add(Restrictions.idEq(address.getAddressId()))
                    .setProjection(Property.forName("city.cityId"))
                    .list();
            Criteria criteria = session.createCriteria(City.class)
                    .add(Restrictions.in("cityId", cityIds));
            city = (City) criteria.uniqueResult();
            tx.commit();
        } catch (RuntimeException e) {
            LOG.error("CityDAO - findCityFromAddress() failed, " + e.getMessage(), e);
        } finally {
            session.flush();
            session.close();
        }
        
        return city;
    }
    
    @Override
    public List<City> findOfCountry(Country country) {
        List<City> cities = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction tx = session.beginTransaction();
            cities = (List<City>) session.createCriteria(City.class)
                    .add(Restrictions.eq("country", country))
                    .list();
            tx.commit();
        } catch (RuntimeException e) {
            LOG.error("CityDAO - findCityOfCountry() failed, " + e.getMessage(), e);
        } finally {
            session.flush();
            session.close();
        }
        
        return cities;
    }
    
    @Override
    public List<City> list() {
        List<City> cities = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction tx = session.beginTransaction();
            cities = session.createCriteria(City.class).list();        
            tx.commit();
        } catch (RuntimeException e) {
            LOG.error("CityDAO - listCities() failed, " + e.getMessage(), e);
        } finally {
            session.flush();
            session.close();
        }
        
        return cities;
    }
}
