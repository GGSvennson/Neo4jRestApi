package com.primefaces.hibernate.bean;

import com.primefaces.hibernate.criteria.dao.AddressDAO;
import com.primefaces.hibernate.criteria.dao.CityDAO;
import com.primefaces.hibernate.criteria.dao.CountryDAO;
import com.primefaces.hibernate.criteria.dao.DepartmentDAO;
import com.primefaces.hibernate.criteria.dao.EmployeesDAO;
import com.primefaces.hibernate.criteria.dao.UsersDAO;
import com.primefaces.hibernate.model.Address;
import com.primefaces.hibernate.model.City;
import com.primefaces.hibernate.model.Country;
import com.primefaces.hibernate.model.Department;
import com.primefaces.hibernate.model.Employees;
import com.primefaces.hibernate.model.Roles;
import java.io.Serializable;
import com.primefaces.hibernate.model.Users;
import com.primefaces.hibernate.util.HttpSessionUtil;
import com.primefaces.hibernate.util.LoginConverter;

import java.util.ArrayList;
import java.util.List;
 
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@ManagedBean(name = "userBean")
@ViewScoped
public class UserBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Users newUser = new Users();
    private Employees newEmployee = new Employees();
    private Department newDepartment = new Department();
    private Address newAddress = new Address();
    private City newCity = new City();
    private Country newCountry = new Country();
    private Users selectedUser = new Users();
    private Employees selectedEmployee = new Employees();
    private Department selectedDepartment = new Department();
    private Address selectedAddress = new Address();
    private City selectedCity = new City();
    private Country selectedCountry = new Country();
    private List<Users> users = new ArrayList<>();
    private List<Employees> employees = new ArrayList<>();
    private List<Department> departments = new ArrayList<>();
    private List<Address> addresses = new ArrayList<>();
    private List<City> cities = new ArrayList<>();
    private List<Country> countries = new ArrayList<>();
    private Roles[] roleses;
    
    private final Roles roleAdmin = Roles.Administrator;
    private final Roles roleUser = Roles.User;
    
    private int employeeId;
    
    private String password;
    private String newPassword;
    private String repeatedPassword;
    
    private UsersDAO usersDAO;
    private EmployeesDAO employeesDAO;
    private AddressDAO addressDAO;
    private CityDAO cityDAO;
    private CountryDAO countryDAO;
    private DepartmentDAO departmentDAO;
    
    @ManagedProperty(value="#{resourceBundleBean}")
    private ResourceBundleBean resourceBundleBean;
    
    public UserBean() {
    }
    
    @PostConstruct
    public void initUsers() {
        HttpSession session = HttpSessionUtil.getSession(false);
        selectedUser = (Users) session.getAttribute("user");
        
        usersDAO = new UsersDAO();
        employeesDAO = new EmployeesDAO();
        addressDAO = new AddressDAO();
        cityDAO = new CityDAO();
        countryDAO = new CountryDAO();
        departmentDAO = new DepartmentDAO();
        
        users = usersDAO.list();
        employees = employeesDAO.list();
        departments = departmentDAO.listNotAdministration();
        countries = countryDAO.list();
    }
    
    public void changePasswordFromDialog() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        if(!LoginConverter.hash256(password).equals(selectedUser.getPassword())) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(
                        ResourceBundleBean.getResourceBundleString("UserBean.change.password.password.does.not.match")
                ));
        } else if(!newPassword.equals(repeatedPassword)) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(
                        ResourceBundleBean.getResourceBundleString("UserBean.change.password.new.password.does.not.match")
                ));
        } else {
            selectedUser.setPassword(LoginConverter.hash256(newPassword));
            usersDAO.update(selectedUser);
            
            RequestContext rc = RequestContext.getCurrentInstance();
            rc.execute("PF('changePasswordEmployee').hide()");
        
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(
                        ResourceBundleBean.getResourceBundleString("UserBean.change.password.password.changed")
                ));
        }
    }
    
    public void createEmployeeFromDialog() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        if( ("".equals(newUser.getUsername())) ||
                ("".equals(newUser.getPassword()))
                || ("".equals(password))
                || ("".equals(newEmployee.getName()))
                || ("".equals(newEmployee.getJobRole()))
                || (null == newEmployee.getInsertTime())
                || (null == newCountry)
                || (null == newCity)
                || (null == newAddress)
                || (null == newDepartment) ) {
            
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(
                        ResourceBundleBean.getResourceBundleString("UserBean.create.employee.no.one.empty.field")
                ));
            
        } else if(!password.equals(newUser.getPassword()))
            
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(
                        ResourceBundleBean.getResourceBundleString("UserBean.create.employee.passwords")
                ));
        
        else {
            Users user = usersDAO.findByUsername(newUser.getUsername());
            if(null != user) {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(
                            ResourceBundleBean.getResourceBundleString("UserBean.check.username")
                    ));
            } else {
                newUser.setRoles(this.roleUser);
                newEmployee.setAddress(newAddress);
                newEmployee.setDepartment(newDepartment);
                usersDAO.create(newEmployee, newUser);

                RequestContext rc = RequestContext.getCurrentInstance();
                rc.execute("PF('createEmployee').hide()");

                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(
                                ResourceBundleBean.getResourceBundleString("UserBean.create.employee.new.employee.created")
                        ));

                reset();
            }
        }
    }
    
    public void openEditEmployeeDialog() {
        if(null == selectedEmployee) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(
                            ResourceBundleBean.getResourceBundleString("UserBean.open.edit.employee.dialog")
                    ));
        }
        else {
            RequestContext rc = RequestContext.getCurrentInstance();
            rc.execute("PF('editEmployee').show()");
        }
    }
    
    public void closeEditEmployeeDialog() {
        reset();
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.execute("PF('editEmployee').hide()");
    }
    
    public void editEmployeeFromDialog() {
        if( ("".equals(selectedEmployee.getName()))
                || ("".equals(selectedEmployee.getJobRole()))
                || (null == selectedEmployee.getInsertTime())
                || (null == newCountry)
                || (null == newCity)
                || (null == newAddress)
                || (null == newDepartment) ) {
            
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(
                            ResourceBundleBean.getResourceBundleString("UserBean.edit.employee.from.dialog.no.one.field.empty")
                    ));
        } else {
            employeesDAO.update(selectedEmployee);

            RequestContext rc = RequestContext.getCurrentInstance();
            rc.execute("PF('editEmployee').hide()");

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(
                            ResourceBundleBean.getResourceBundleString("UserBean.edit.employee.from.dialog.employee.updated")
                    ));

            reset();
        }
    }
    
    public void openDeleteEmployeeDialog() {
        if(null == selectedEmployee) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(
                            ResourceBundleBean.getResourceBundleString("UserBean.open.delete.employee.dialog")
                    ));
        } else {
            RequestContext rc = RequestContext.getCurrentInstance();
            rc.execute("PF('deleteEmployee').show()");
        }
    }
    
    public void closeDeleteEmployeeDialog() {
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.execute("PF('deleteEmployee').hide()");
        reset();
    }
    
    public void deleteEmployee() {
        if(selectedEmployee.equals(selectedUser.getEmployee())) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(
                        ResourceBundleBean.getResourceBundleString("UserBean.delete.employee")
                ));
        } else {
            employeesDAO.delete(selectedEmployee);
        
            RequestContext rc = RequestContext.getCurrentInstance();
            rc.execute("PF('deleteEmployee').hide()");
        
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(
                            ResourceBundleBean.getResourceBundleString("UserBean.delete.employee.employee.deleted")
                    ));
        
            reset();
        }
    }
    
    public void showEmployees() {
        if(employees.isEmpty()) {
            employees = employeesDAO.list();
        }
    }
    
    public void updateData() {
        users.clear();
        employees.clear();
        users = usersDAO.list();
        employees = employeesDAO.list();
    }
    
    public void changeEmployee(SelectEvent event) {
        selectedEmployee = (Employees) event.getObject();
        Users user = usersDAO.findByEmployee(selectedEmployee);
        if(user.getRoles() != this.roleUser) {
            selectedEmployee = new Employees();
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(
                        ResourceBundleBean.getResourceBundleString("UserBean.change.employee")
                ));
        } else {
            selectedDepartment = departmentDAO.findOfEmployee(selectedEmployee);
            selectedAddress = addressDAO.findOfEmployee(selectedEmployee);
            selectedCity = cityDAO.findFromAddress(selectedAddress);
            selectedCountry = countryDAO.findFromCity(selectedCity);
        }
    }
    
    public void loadCities() {
        cities = cityDAO.findOfCountry(newCountry);
    }
    
    public void loadAddresses() {
        addresses = addressDAO.findFromCity(newCity);
    }
    
    private void reset() {
        selectedUser = new Users();
        newUser = new Users();
        selectedEmployee = new Employees();
        newEmployee = new Employees();
        selectedDepartment = new Department();
        newDepartment = new Department();
        selectedAddress = new Address();
        newAddress = new Address();
        selectedCity = new City();
        newCity = new City();
        selectedCountry = new Country();
        newCountry = new Country();
    }
    
    public Users getNewUser() {
        return newUser;
    }

    public void setNewUser(Users newUser) {
        this.newUser = newUser;
    }

    public Users getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(Users selectedUser) {
        this.selectedUser = selectedUser;
    }

    public Employees getSelectedEmployee() {
        return selectedEmployee;
    }

    public void setSelectedEmployee(Employees selectedEmployee) {
        this.selectedEmployee = selectedEmployee;
    }

    public Employees getNewEmployee() {
        return newEmployee;
    }

    public void setNewEmployee(Employees newEmployee) {
        this.newEmployee = newEmployee;
    }

    public Department getNewDepartment() {
        return newDepartment;
    }

    public void setNewDepartment(Department newDepartment) {
        this.newDepartment = newDepartment;
    }

    public Address getNewAddress() {
        return newAddress;
    }

    public void setNewAddress(Address newAddress) {
        this.newAddress = newAddress;
    }

    public Department getSelectedDepartment() {
        return selectedDepartment;
    }

    public void setSelectedDepartment(Department selectedDepartment) {
        this.selectedDepartment = selectedDepartment;
    }

    public Address getSelectedAddress() {
        return selectedAddress;
    }

    public void setSelectedAddress(Address selectedAddress) {
        this.selectedAddress = selectedAddress;
    }

    public City getNewCity() {
        return newCity;
    }

    public void setNewCity(City newCity) {
        this.newCity = newCity;
    }

    public Country getNewCountry() {
        return newCountry;
    }

    public void setNewCountry(Country newCountry) {
        this.newCountry = newCountry;
    }

    public City getSelectedCity() {
        return selectedCity;
    }

    public void setSelectedCity(City selectedCity) {
        this.selectedCity = selectedCity;
    }

    public Country getSelectedCountry() {
        return selectedCountry;
    }

    public void setSelectedCountry(Country selectedCountry) {
        this.selectedCountry = selectedCountry;
    }
    
    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }

    public List<Employees> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employees> employees) {
        this.employees = employees;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public Roles[] getRoleses() {
        roleses = Roles.values();
        return roleses;
    }

    public void setRoleses(Roles[] roleses) {
        this.roleses = roleses;
    }

    public Roles getRoleAdmin() {
        return roleAdmin;
    }

    public Roles getRoleUser() {
        return roleUser;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }

    public UsersDAO getUsersDAO() {
        return usersDAO;
    }

    public EmployeesDAO getEmployeesDAO() {
        return employeesDAO;
    }

    public AddressDAO getAddressDAO() {
        return addressDAO;
    }

    public CityDAO getCityDAO() {
        return cityDAO;
    }

    public CountryDAO getCountryDAO() {
        return countryDAO;
    }

    public DepartmentDAO getDepartmentDAO() {
        return departmentDAO;
    }

    public ResourceBundleBean getResourceBundleBean() {
        return resourceBundleBean;
    }

    public void setResourceBundleBean(ResourceBundleBean resourceBundleBean) {
        this.resourceBundleBean = resourceBundleBean;
    }
}
