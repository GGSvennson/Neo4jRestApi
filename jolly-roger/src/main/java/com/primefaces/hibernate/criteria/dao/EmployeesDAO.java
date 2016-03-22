package com.primefaces.hibernate.criteria.dao;

import com.primefaces.hibernate.Idao.IEmployeesDAO;
import com.primefaces.hibernate.generic.GenericDaoImpl;
import com.primefaces.hibernate.model.Department;
import com.primefaces.hibernate.model.Employees;
import com.primefaces.hibernate.model.Users;
import com.primefaces.hibernate.util.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

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
            Transaction tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(Employees.class)
                    .add(Restrictions.eq("name", name));
            emps = criteria.list();
            tx.commit();
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
            Transaction tx = session.beginTransaction();
            DetachedCriteria subCriteria = DetachedCriteria.forClass(Users.class)
                    .add(Property.forName("username").eq(user.getUsername()))
                    .setProjection(Property.forName("username"));
            Criteria criteria = session.createCriteria(Employees.class, "e")
                    .createAlias("e.user", "user")
                    .add(Subqueries.propertyIn("user.username", subCriteria));
            tx.commit();
            emps = criteria.list();
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
            Transaction tx = session.beginTransaction();
            DetachedCriteria subCriteria = DetachedCriteria.forClass(Department.class)
                    .add(Property.forName("departmentId").eq(department.getDepartmentId()))
                    .setProjection(Property.forName("id"));
            Criteria criteria = session.createCriteria(Employees.class, "e")
                    .createAlias("e.department", "department")
                    .add(Subqueries.propertyIn("department.departmentId", subCriteria));
            emps = criteria.list();
            tx.commit();
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
            Transaction tx = session.beginTransaction();
            DetachedCriteria subquery = DetachedCriteria.forClass(Department.class);
            subquery.setProjection(Projections.id());
            Criteria criteria = session.createCriteria(Employees.class, "e")
                    .createAlias("e.department", "department")
                    .add(Subqueries.propertyIn("department.departmentId", subquery))
                    .addOrder(Order.asc("department.departmentName"))
                    .addOrder(Order.asc("name"))
                    .addOrder(Order.asc("jobRole"));
            emps = criteria.list();
            tx.commit();
        } catch (RuntimeException e) {
            LOG.error("EmployeesDAO - listEmployees() failed, " + e.getMessage(), e);
        } finally {
            session.flush();
            session.close();
        }
        
        return emps;
    }
}