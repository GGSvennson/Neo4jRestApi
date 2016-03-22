package com.primefaces.hibernate.criteria.dao;

import com.primefaces.hibernate.Idao.IDepartmentDAO;
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

public class DepartmentDAO extends GenericDaoImpl<Department, Long> implements IDepartmentDAO {
    
    private static final Logger LOG = Logger.getLogger(DepartmentDAO.class);
    
    @Override
    public void delete(Department department) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            List<Employees> emps = (List<Employees>) session.createQuery("from Employees e where e.department = :department")
                                .setEntity("department", department)
                                .list();
            while(!emps.isEmpty()) {
                Employees emp = emps.remove(0);
                Users usr = emp.getUser();
                session.delete(emp);
                session.delete(usr);
                session.flush();
                session.clear();
            }
            session.delete(department);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            LOG.error("DepartmentDAO - createDepartment() failed, " + e.getMessage(), e);
        } finally {
            session.flush();
            session.close();
        }
    }
    
    @Override
    public Department findByName(String name) {
        List<Department> depts = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(Department.class)
                    .add(Restrictions.eq("departmentName", name));
            depts = criteria.list();
            tx.commit();
        } catch (RuntimeException e) {
            LOG.error("DepartmentDAO - findDepartmentByName() failed, " + e.getMessage(), e);
        } finally {
            session.flush();
            session.close();
        }
        
        if(depts.size() > 0)
            return depts.get(0);
        
        return null;
    }
    
    @Override
    public Department findOfEmployee(Employees employee) {
        Department dept = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction tx = session.beginTransaction();
            List<Long> deptIds = session.createCriteria(Employees.class, "e")
                    .createAlias("e.department", "department")
                    .add(Restrictions.idEq(employee.getId()))
                    .setProjection(Property.forName("department.departmentId"))
                    .list();
            Criteria criteria = session.createCriteria(Department.class)
                    .add(Restrictions.in("departmentId", deptIds));
            dept = (Department) criteria.uniqueResult();
            tx.commit();
        } catch (RuntimeException e) {
            LOG.error("DepartmentDAO - findDepartmentOfEmployee() failed, " + e.getMessage(), e);
        } finally {
            session.flush();
            session.close();
        }
        
        return dept;
    }
    
    @Override
    public List<Department> listNotAdministration() {
        List<Department> depts = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction tx = session.beginTransaction();
            DetachedCriteria subquery = DetachedCriteria.forClass(Department.class)
                    .add(Restrictions.like("departmentName", "Administration"))
                    .setProjection(Projections.property("departmentName"));
            Criteria criteria = session.createCriteria(Department.class)
                    .add(Subqueries.propertyNotIn("departmentName", subquery))
                    .addOrder(Order.asc("departmentName"));
            depts = criteria.list();        
            tx.commit();
        } catch (RuntimeException e) {
            LOG.error("DepartmentDAO - listDepartmentsNotAdministration() failed, " + e.getMessage(), e);
        } finally {
            session.flush();
            session.close();
        }
        
        return depts;
    }
    
    @Override
    public List<Department> list() {
        List<Department> depts = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction tx = session.beginTransaction();        
            depts = session.createCriteria(Department.class)
                    .addOrder(Order.asc("departmentName"))
                    .list();
            tx.commit();
        } catch (RuntimeException e) {
            LOG.error("DepartmentDAO - listDepartments() failed, " + e.getMessage(), e);
        } finally {
            session.flush();
            session.close();
        }
        
        return depts;
    }
}
