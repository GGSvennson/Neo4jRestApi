package com.primefaces.hibernate.Idao;

import com.primefaces.hibernate.exception.UnableToSaveException;
import java.util.List;

import com.primefaces.hibernate.model.Users;
import com.primefaces.hibernate.generic.GenericDao;
import com.primefaces.hibernate.model.Employees;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public interface IUsersDAO extends GenericDao<Users, Integer> {
    
    public void create(Employees employee, Users user)
            throws UnableToSaveException, NoSuchAlgorithmException, UnsupportedEncodingException;
    public Users findByUsername(String username);
    public Users findByPassword(String password);
    public Users findByUsernameAndPassword(String username, String password);
    public Users findByEmployee(Employees employee);
    public List<Users> list();
}
