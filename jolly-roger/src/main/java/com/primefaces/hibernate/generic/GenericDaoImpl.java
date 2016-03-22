package com.primefaces.hibernate.generic;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.primefaces.hibernate.exception.UnableToSaveException;

public class GenericDaoImpl<Entity, K extends Serializable> implements GenericDao<Entity, K> {
 
    public Class<Entity> domainClass = getDomainClass();
    private Session session;

    protected Class getDomainClass() {
        if (domainClass == null) {
            ParameterizedType thisType = (ParameterizedType) getClass()
                .getGenericSuperclass();
            domainClass = (Class) thisType.getActualTypeArguments()[0];
        }
        return domainClass;
    }

    private Session getHibernateTemplate() {
        session = com.primefaces.hibernate.util.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        return session;
    }

    @Override
    public Entity find(K id) {
        Entity returnValue = (Entity) getHibernateTemplate().load(domainClass, id);
        session.getTransaction().commit();
        return returnValue;
    }
    
    @Override
    public void update(Entity t) throws UnableToSaveException {
        try {
            getHibernateTemplate().update(t);
            session.getTransaction().commit();
         } catch (HibernateException e) {
            throw new UnableToSaveException(e);
        }
    }

    @Override
    public void create(Entity t) throws UnableToSaveException {
        try {
            getHibernateTemplate().save(t);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            throw new UnableToSaveException(e);
        }
    }
    
    @Override
    public void delete(Entity t) {
        getHibernateTemplate().delete(t);
        session.getTransaction().commit();
    }
}