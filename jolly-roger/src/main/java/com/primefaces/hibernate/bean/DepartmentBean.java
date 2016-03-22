package com.primefaces.hibernate.bean;

import com.primefaces.hibernate.criteria.dao.DepartmentDAO;
import com.primefaces.hibernate.criteria.dao.EmployeesDAO;
import com.primefaces.hibernate.model.Department;
import com.primefaces.hibernate.model.Employees;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@ManagedBean(name = "departmentBean")
@ViewScoped
public class DepartmentBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private List<Department> departments = new ArrayList<>();
    private Department newDepartment = new Department();
    private Department selectedDepartment = new Department();
    private List<Employees> selectedEmployees = new ArrayList<>();
    private String deptName;
    
    private DepartmentDAO departmentDAO;
    private EmployeesDAO employeesDAO;
    
    @ManagedProperty(value="#{resourceBundleBean}")
    private ResourceBundleBean resourceBundleBean;
    
    public DepartmentBean() {
    }
    
    @PostConstruct
    public void init() {
        departmentDAO = new DepartmentDAO();
        employeesDAO = new EmployeesDAO();
        
        departments = departmentDAO.list();
    }
    
    public void checkDepartmentName() {
        Department dpt = departmentDAO.findByName(newDepartment.getDepartmentName());
        if(null != dpt)
            if(dpt.getDepartmentName().equals("Administration"))
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(
                            ResourceBundleBean.getResourceBundleString("DepartmentBean.check.department.name")
                    ));
    }
    
    public void createDepartmentFromDialog() {
        if("".equals(newDepartment.getDepartmentName()))
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(
                        ResourceBundleBean.getResourceBundleString("DepartmentBean.create.department.from.dialog.name.is.required")
                ));
        else {
            Department dpt = departmentDAO.findByName(newDepartment.getDepartmentName());
            
            if(null != dpt)
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(
                            ResourceBundleBean.getResourceBundleString("DepartmentBean.create.department.from.dialog.name.already.exists")
                    ));
            else {
                departmentDAO.create(newDepartment);

                RequestContext rc = RequestContext.getCurrentInstance();
                rc.execute("PF('createDepartment').hide()");

                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(
                                ResourceBundleBean.getResourceBundleString("DepartmentBean.create.department.from.dialog.department.created")
                        ));

                newDepartment = new Department();
            }
        }
    }
    
    public void openEditDepartmentDialog() {
        if(null == selectedDepartment) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(
                        ResourceBundleBean.getResourceBundleString("DepartmentBean.open.edit.department.dialog.select.row")
                ));
        } else if(selectedDepartment.getDepartmentName().equals("Administration")) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(
                        ResourceBundleBean.getResourceBundleString("DepartmentBean.open.edit.department.dialog.administration.department.edited")
                ));
        } else {
            // PF('editDepartment').show()
            RequestContext rc = RequestContext.getCurrentInstance();
            rc.execute("PF('editDepartment').show()");
        }    
    }
    
    public void editDepartmentFromDialog() {
        if(selectedEmployees.size() > 0)
            selectedDepartment.setEmployees(selectedEmployees);
        
        departmentDAO.update(selectedDepartment);
        
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.execute("PF('editDepartment').hide()");
        
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(
                        ResourceBundleBean.getResourceBundleString("DepartmentBean.edit.department.from.dialog.department.updated")
                ));
    }
    
    public void openDeleteDepartmentDialog() {
        if(null == selectedDepartment)
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(
                        ResourceBundleBean.getResourceBundleString("DepartmentBean.open.delete.department.dialog.select.row")
                ));
        else {
            String depmtName = selectedDepartment.getDepartmentName();
            if("Administration".equals(depmtName))
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(
                            ResourceBundleBean.getResourceBundleString("DepartmentBean.open.delete.department.dialog.administration.department.deleted")
                    ));
            else {
                // PF('deleteDepartment').show()
                RequestContext rc = RequestContext.getCurrentInstance();
                rc.execute("PF('deleteDepartment').show()");
            }
        }
    }
    
    public void deleteDepartment() {
        departmentDAO.delete(selectedDepartment);

        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(
                    ResourceBundleBean.getResourceBundleString("DepartmentBean.open.delete.department.dialog.department.deleted")
            ));

        selectedDepartment = new Department();
    }
    
    public void showDepartments() {
        if(departments.isEmpty()) {
            departments = departmentDAO.list();
        }
    }
    
    public void updateData() {
        departments.clear();
        departments = departmentDAO.list();
    }
    
    public void changeDepartment(SelectEvent event) {
        selectedDepartment = (Department) event.getObject();
        deptName = "( " + selectedDepartment.getDepartmentName() + " )";
    }
    
    public void showEmployees() {
        if(null == selectedDepartment) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(
                        ResourceBundleBean.getResourceBundleString("DepartmentBean.show.employees.select.row")
                ));
        } else {
            selectedEmployees = employeesDAO.findFromDepartment(selectedDepartment);
        }
        selectedDepartment = new Department();
    }
    
    public Department getNewDepartment() {
        return newDepartment;
    }

    public void setNewDepartment(Department newDepartment) {
        this.newDepartment = newDepartment;
    }

    public Department getSelectedDepartment() {
        return selectedDepartment;
    }

    public void setSelectedDepartment(Department selectedDepartment) {
        this.selectedDepartment = selectedDepartment;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public List<Employees> getSelectedEmployees() {
        return selectedEmployees;
    }

    public void setSelectedEmployees(List<Employees> selectedEmployees) {
        this.selectedEmployees = selectedEmployees;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public DepartmentDAO getDepartmentDAO() {
        return departmentDAO;
    }

    public EmployeesDAO getEmployeesDAO() {
        return employeesDAO;
    }

    public ResourceBundleBean getResourceBundleBean() {
        return resourceBundleBean;
    }

    public void setResourceBundleBean(ResourceBundleBean resourceBundleBean) {
        this.resourceBundleBean = resourceBundleBean;
    }
}
