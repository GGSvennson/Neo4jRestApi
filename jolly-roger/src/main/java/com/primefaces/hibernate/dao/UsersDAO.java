package com.primefaces.hibernate.dao;

import com.primefaces.hibernate.Idao.IUsersDAO;
import com.primefaces.hibernate.exception.UnableToSaveException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.primefaces.hibernate.model.Users;
import com.primefaces.hibernate.generic.GenericDaoImpl;
import com.primefaces.hibernate.model.Employees;
import com.primefaces.hibernate.util.HibernateUtil;
import com.primefaces.hibernate.util.LoginConverter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class UsersDAO extends GenericDaoImpl<Users, Integer> implements IUsersDAO {
    
    private static final Logger LOG = Logger.getLogger(UsersDAO.class);
    
    @Override
    public void create(Employees employee, Users user)
            throws UnableToSaveException, NoSuchAlgorithmException, UnsupportedEncodingException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            user.setPassword(LoginConverter.hash256(user.getPassword()));
            user.setEmployee(employee);
            employee.setUser(user);
            session.save(user);
            session.getTransaction().commit();
        } catch (UnableToSaveException e) {
            LOG.error("UsersDAO - createUser() failed, " + e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            LOG.error("UsersDAO - LoginConverter (NoSuchAlgorithmException) in createUser() failed, " + e.getMessage(), e);
        } catch (UnsupportedEncodingException e) {
            LOG.error("UsersDAO - LoginConverter (UnsupportedEncodingException) in createUser() failed, " + e.getMessage(), e);
        } finally {
            session.flush();
            session.close();
        }
    }
    
    @Override
    public Users findByUsername(String username) {
        List<Users> users = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            users = session.createQuery("from Users u where u.username = :username")
                    .setString("username", username)
                    .list();
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            LOG.error("UsersDAO - findUserByUsername() failed, " + e.getMessage(), e);
        } finally {
            session.flush();
            session.close();
        }
        
        if(users.size() > 0)
            return users.get(0);
        
        return null;
    }
    
    @Override
    public Users findByPassword(String password) {
        List<Users> users = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            String pass = LoginConverter.hash256(password);
            users = session.createQuery("from Users u where u.password = :password")
                    .setString("password", pass)
                    .list();
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            LOG.error("UsersDAO - findUserByPassword() failed, " + e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            LOG.error("UsersDAO - LoginConverter (NoSuchAlgorithmException) in findUserByPassword() failed, " + e.getMessage(), e);
        } catch (UnsupportedEncodingException e) {
           LOG.error("UsersDAO - LoginConverter (UnsupportedEncodingException) in findUserByPassword() failed, " + e.getMessage(), e);
        } finally {
            session.flush();
            session.close();
        }
        
        if(users.size() > 0)
            return users.get(0);
        
        return null;
    }
    @Override
    public Users findByUsernameAndPassword(String username, String password) {
        List<Users> users = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            String pass = LoginConverter.hash256(password);
            users = session.createQuery("from Users u where u.username = :username and u.password = :password")
                    .setString("username", username)
                    .setString("password", pass)
                    .list();
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            LOG.error("UsersDAO - findUserByUsernameAndPassword failed, " + e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            LOG.error("UsersDAO - LoginConverter (NoSuchAlgorithmException) in findUserByUsernameAndPassword failed, " + e.getMessage(), e);
        } catch (UnsupportedEncodingException e) {
            LOG.error("UsersDAO - LoginConverter (UnsupportedEncodingException) in findUserByUsernameAndPassword failed, " + e.getMessage(), e);
        } finally {
            session.flush();
            session.close();
        }
        
        if(users.size() > 0)
            return users.get(0);
        
        return null;
    }
    @Override
    public Users findByEmployee(Employees employee) {
        List<Users> users = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            users = session.createQuery("from Users u join fetch u.employee where u.employee = :employee")
                    .setEntity("employee", employee)
                    .list();
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            LOG.error("UsersDAO - findUsersByEmployee failed, " + e.getMessage(), e);
        } finally {
            session.flush();
            session.close();
        }
        
        if(users.size() > 0)
            return users.get(0);
        
        return null;
    }
    
    @Override
    public List<Users> list() {
        List<Users> users = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            users = session.createQuery("from Users").list();
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            LOG.error("UsersDAO -  listUsers() failed, " + e.getMessage(), e);
        } finally {
            session.flush();
            session.close();
        }
        return users;
    }
}
