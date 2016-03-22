package com.primefaces.hibernate.dao;

import com.primefaces.hibernate.Idao.ICountryDAO;
import com.primefaces.hibernate.generic.GenericDaoImpl;
import com.primefaces.hibernate.model.City;
import com.primefaces.hibernate.model.Country;
import com.primefaces.hibernate.util.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.apache.log4j.Logger;

public class CountryDAO extends GenericDaoImpl<Country, Short> implements ICountryDAO {
    
    private static final Logger LOG = Logger.getLogger(CountryDAO.class);
    
    @Override
    public void addCities(Country country, List<City> cityList) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            cityList.stream().forEach((city) -> {
                city.setCountry(country);
                session.saveOrUpdate(city);
            });
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            LOG.error("CountryDAO - addCitiesToCountry() failed, " + e.getMessage(), e);
        } finally {
            session.flush();
            session.close();
        }
    }
    
    @Override
    public Country findByName(String name) {
        List<Country> countries = new ArrayList();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            countries = session.createQuery("from Country c where c.country = :name")
                        .setString("name", name)
                        .list();
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            LOG.error("CountryDAO - findCountryByName() failed, " + e.getMessage(), e);
        } finally {
            session.flush();
            session.close();
        }
        
        if(countries.size() > 0)
            return countries.get(0);
        
        return null;
    }
    
    @Override
    public Country findFromCity(City city) {
        Country country = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            String hql = "from City c join fetch c.country where c.cityId = :cityId";
            List<City> cities = session.createQuery(hql)
                                .setShort("cityId", city.getCityId())
                                .list();
            if(cities.size() > 0)
                country = cities.get(0).getCountry();
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            LOG.error("CountryDAO - findCountryFromCity() failed, " + e.getMessage(), e);
        } finally {
            session.flush();
            session.close();
        }
        
        return country;
    }
    
    @Override
    public List<Country> list() {
        List<Country> countries = new ArrayList();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            countries = session.createQuery("from Country c order by c.country")
                        .list();
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            LOG.error("CountryDAO - listCountries() failed, " + e.getMessage(), e);
        } finally {
            session.flush();
            session.close();
        }
        
        return countries;
    }
}
