package com.primefaces.hibernate.Idao;

import com.primefaces.hibernate.generic.GenericDao;
import com.primefaces.hibernate.model.Department;
import com.primefaces.hibernate.model.Employees;
import java.util.List;

public interface IDepartmentDAO extends GenericDao<Department, Long> {
    
    public void delete(Department department);
    public Department findByName(String name);
    public Department findOfEmployee(Employees employee);
    public List<Department> listNotAdministration();
    public List<Department> list();
}
