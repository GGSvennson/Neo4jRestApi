package com.primefaces.hibernate.bean;

import com.primefaces.hibernate.criteria.dao.EmployeesDAO;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.primefaces.hibernate.criteria.dao.UsersDAO;
import com.primefaces.hibernate.model.Employees;
import com.primefaces.hibernate.model.Roles;
import com.primefaces.hibernate.model.Users;
import com.primefaces.hibernate.util.HttpSessionUtil;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import javax.faces.bean.ManagedProperty;

@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {
 
    private static final long serialVersionUID = 1L;
    
    private String message;
    private Users user = new Users();
    private Employees employee = new Employees();
    
    private Roles roleAdmin = Roles.Administrator;
    private Roles roleUser = Roles.User;
    
    @ManagedProperty(value="#{resourceBundleBean}")
    private ResourceBundleBean resourceBundleBean;
    
    public LoginBean() {
    }
    
    public Roles getRoleAdmin() {
        return roleAdmin;
    }

    public void setRoleAdmin(Roles roleAdmin) {
        this.roleAdmin = roleAdmin;
    }
    
    public Roles getRoleUser() {
        return roleUser;
    }
    
    public void setRoleUser(Roles roleUser) {
        this.roleUser = roleUser;
    }
    
    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Employees getEmployee() {
        return employee;
    }

    public void setEmployee(Employees employee) {
        this.employee = employee;
    }
 
    public String getMessage() {
        return message;
    }
 
    public void setMessage(String message) {
        this.message = message;
    }

    public ResourceBundleBean getResourceBundleBean() {
        return resourceBundleBean;
    }

    public void setResourceBundleBean(ResourceBundleBean resourceBundleBean) {
        this.resourceBundleBean = resourceBundleBean;
    }

    public void login() throws IOException, NoSuchAlgorithmException {
        
        UsersDAO usersDAO = new UsersDAO();
        user = usersDAO.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        
        if (null != user) {
            EmployeesDAO employeesDAO = new EmployeesDAO();
            employee = employeesDAO.findByUser(user);
            
            // get Http Session
            HttpSession session = HttpSessionUtil.getSession(true);
        
            //store user
            session.setAttribute("user", user);
            
            HttpSessionUtil.redirect(HttpSessionUtil.getRequest().getContextPath() + "/ui/home.jsf");
            
        } else {
            user = new Users();
            
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    ResourceBundleBean.getResourceBundleString("LoginBean.error.login.1"),
                    ResourceBundleBean.getResourceBundleString("LoginBean.error.login.2"));
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    
 
    public void signOut() throws IOException {
        HttpSession session = HttpSessionUtil.getSession(false);
        session.removeAttribute("user");
        session.invalidate();
        user = new Users();
        
        HttpSessionUtil.redirect(HttpSessionUtil.getRequest().getContextPath() + "/ui/home.jsf");
    }
}
