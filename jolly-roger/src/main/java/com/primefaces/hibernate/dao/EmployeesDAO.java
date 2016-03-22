package com.primefaces.hibernate.dao;

import com.primefaces.hibernate.Idao.IEmployeesDAO;
import com.primefaces.hibernate.generic.GenericDaoImpl;
import com.primefaces.hibernate.model.Address;
import com.primefaces.hibernate.model.Department;
import com.primefaces.hibernate.model.Employees;
import com.primefaces.hibernate.model.Users;
import com.primefaces.hibernate.util.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.apache.log4j.Logger;

public class EmployeesDAO extends GenericDaoImpl<Employees, Integer> implements IEmployeesDAO {
    
    private static final Logger LOG = org.apache.log4j.Logger.getLogger(EmployeesDAO.class);
    
    @Override
    public void addToDepartment(Department department, List<Employees> employees) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            employees.stream().forEach((emp) -> {
                emp.setDepartment(department);
                session.saveOrUpdate(emp);
            });
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            LOG.error("EmployeesDAO - addEmployeesToDepartment() failed, " + e.getMessage(), e);
        } finally {
            session.flush();
            session.close();
        }
    }
    
    @Override
    public void delete(Employees employee) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Users user = employee.getUser();
            session.delete(employee);
            session.delete(user);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            LOG.error("EmployeesDAO - deleteEmployee() failed, " + e.getMessage(), e);
        } finally {
            session.flush();
            session.close();
        }
    }
    
    @Override
    public Employees findByName(String name) {
        List<Employees> emps = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            emps = session.createQuery("from Employees e where e.name = :name")
            .setString("name", name)
            .list();
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            LOG.error("EmployeesDAO - findEmployeeByName() failed, " + e.getMessage(), e);
        } finally {
            session.flush();
            session.close();
        }
        
        if(emps.size() > 0)
            return emps.get(0);
        
        return null;
    }
    
    @Override
    public Employees findByUser(Users user) {
        List<Employees> emps = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            emps = session.createQuery("from Employees e join fetch e.user where e.user = :user")
                        .setEntity("user", user)
                        .list();
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            LOG.error("EmployeesDAO - findEmployeeByUser() failed, " + e.getMessage(), e);
        } finally {
            session.flush();
            session.close();
        }
        
        if(emps.size() > 0)
            return emps.get(0);
        
        return null;
    }
    
    @Override
    public List<Employees> findFromDepartment(Department department) {
        List<Employees> emps = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            emps = session.createQuery("from Employees e where e.department = :department")
                    .setEntity("department", department)
                    .list();
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            LOG.error("EmployeesDAO - findEmployeeFromDepartment() failed, " + e.getMessage(), e);
        } finally {
            session.flush();
            session.close();
        }
        
        return emps;
    }
    
    @Override
    public List<Employees> list() {
        List<Employees> emps = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            emps = session.createQuery("from Employees e join fetch e.department order by e.department.departmentName")
                    .list();
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            LOG.error("EmployeesDAO - listEmployees() failed, " + e.getMessage(), e);
        } finally {
            session.flush();
            session.close();
        }
        
        return emps;
    }
}
