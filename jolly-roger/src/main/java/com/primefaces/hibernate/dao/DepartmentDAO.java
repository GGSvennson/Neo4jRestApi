package com.primefaces.hibernate.dao;

import com.primefaces.hibernate.Idao.IDepartmentDAO;
import com.primefaces.hibernate.generic.GenericDaoImpl;
import com.primefaces.hibernate.model.Department;
import com.primefaces.hibernate.model.Employees;
import com.primefaces.hibernate.model.Users;
import com.primefaces.hibernate.util.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.apache.log4j.Logger;

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
            session.beginTransaction();
            depts = session.createQuery("from Department d where d.departmentName = :name")
            .setString("name", name)
            .list();
            session.getTransaction().commit();
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
            session.beginTransaction();
            List<Employees> emps = session.createQuery("from Employees e join fetch e.department where e.id = :id")
                            .setParameter("id", employee.getId())
                            .list();
            if(emps.size() > 0) {
                Employees emp = emps.get(0);
                dept = emp.getDepartment();
            }
            session.getTransaction().commit();
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
            session.beginTransaction();
            String hql = "from Department d where d.departmentName not in "
                + "(select d1.departmentName from Department d1 where d1.departmentName like 'Administration') "
                + "order by d.departmentName";
            depts = session.createQuery(hql).list();
            session.getTransaction().commit();
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
            session.beginTransaction();
            depts = session.createQuery("from Department d order by d.departmentName")
                    .list();
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            LOG.error("DepartmentDAO - listDepartments() failed, " + e.getMessage(), e);
        } finally {
            session.flush();
            session.close();
        }
        
        return depts;
    }
}
