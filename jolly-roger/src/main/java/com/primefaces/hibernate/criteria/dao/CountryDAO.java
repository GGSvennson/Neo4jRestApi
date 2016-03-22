package com.primefaces.hibernate.criteria.dao;

import com.primefaces.hibernate.Idao.ICountryDAO;
import com.primefaces.hibernate.generic.GenericDaoImpl;
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
            Transaction tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(Country.class)
                    .add(Restrictions.eq("country", name));
            countries = criteria.list();
            tx.commit();
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
            Transaction tx = session.beginTransaction();
            List<Short> countryIds = session.createCriteria(City.class, "c")
                    .createAlias("c.country", "country")
                    .add(Restrictions.idEq(city.getCityId()))
                    .setProjection(Property.forName("country.countryId"))
                    .list();
            Criteria criteria = session.createCriteria(Country.class)
                    .add(Restrictions.in("countryId", countryIds));        
            country = (Country) criteria.uniqueResult();        
            tx.commit();
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
            Transaction tx = session.beginTransaction();
            countries = session.createCriteria(Country.class).list();
            tx.commit();
        } catch (RuntimeException e) {
            LOG.error("CountryDAO - listCountries() failed, " + e.getMessage(), e);
        } finally {
            session.flush();
            session.close();
        }
        
        return countries;
    }
}
