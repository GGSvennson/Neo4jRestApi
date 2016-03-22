package com.primefaces.hibernate.dao;

import com.primefaces.hibernate.Idao.ICityDAO;
import com.primefaces.hibernate.generic.GenericDaoImpl;
import com.primefaces.hibernate.model.Address;
import com.primefaces.hibernate.model.City;
import com.primefaces.hibernate.model.Country;
import com.primefaces.hibernate.util.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.apache.log4j.Logger;

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
            session.beginTransaction();
            cities = session.createQuery("from City c where c.city = :name")
                                .setString("name", name)
                                .list();
            session.getTransaction().commit();
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
            session.beginTransaction();
            String hql = "from Address a join fetch a.city where a.addressId = :addressId";
            List<Address> addrs = session.createQuery(hql)
                                    .setShort("addressId", address.getAddressId())
                                    .list();
            if(addrs.size() > 0) {
                Address addr = addrs.get(0);
                city = addr.getCity();
            }    
            session.getTransaction().commit();
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
            session.beginTransaction();
            cities = session.createQuery("from City c where c.country = :country")
                                .setEntity("country", country)
                                .list();
            session.getTransaction().commit();
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
            session.beginTransaction();
            cities = session.createQuery("from City c order by c.city").list();
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            LOG.error("CityDAO - listCities() failed, " + e.getMessage(), e);
        } finally {
            session.flush();
            session.close();
        }
        
        return cities;
    }
}
