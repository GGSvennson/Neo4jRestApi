package com.primefaces.hibernate.bean;

import com.primefaces.hibernate.criteria.dao.AddressDAO;
import com.primefaces.hibernate.criteria.dao.CityDAO;
import com.primefaces.hibernate.criteria.dao.CountryDAO;
import com.primefaces.hibernate.criteria.dao.DepartmentDAO;
import com.primefaces.hibernate.criteria.dao.EmployeesDAO;
import com.primefaces.hibernate.model.Address;
import com.primefaces.hibernate.model.City;
import com.primefaces.hibernate.model.Country;
import com.primefaces.hibernate.model.Department;
import com.primefaces.hibernate.model.Employees;
import com.primefaces.hibernate.model.Users;
import com.primefaces.hibernate.util.HttpSessionUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "employeeBean")
@ViewScoped
public class EmployeeBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Country selectedCountry = new Country();
    private City selectedCity = new City();
    private Address selectedAddress = new Address();
    private Employees selectedEmployee = new Employees();
    private Users selectedUser = new Users();
    private Department selectedDepartment = new Department();
    private List<Employees> empsList = new ArrayList<>();
    private String deptName;
    private String password;
    private String newPassword;
    private String repeatedPassword;
    
    private AddressDAO addressDAO;
    private CityDAO cityDAO;
    private CountryDAO countryDAO;
    private DepartmentDAO departmentDAO;
    private EmployeesDAO employeesDAO;
    
    public EmployeeBean() {
    }
    
    @PostConstruct
    public void init() {
        HttpSession session = HttpSessionUtil.getSession(false);
        selectedUser = (Users) session.getAttribute("user");
        
        selectedEmployee = selectedUser.getEmployee();
        
        addressDAO = new AddressDAO();
        cityDAO = new CityDAO();
        countryDAO = new CountryDAO();
        departmentDAO = new DepartmentDAO();
        employeesDAO = new EmployeesDAO();
        
        selectedDepartment = departmentDAO.findOfEmployee(selectedEmployee);
        selectedAddress = addressDAO.findOfEmployee(selectedEmployee);
        selectedCity = cityDAO.findFromAddress(selectedAddress);
        selectedCountry = countryDAO.findFromCity(selectedCity);
    }
    
    public void showEmployees() {
        deptName = "( " + selectedDepartment.getDepartmentName() + " )";
        empsList = employeesDAO.findFromDepartment(selectedDepartment);
    }
    
    public void reset() {
        deptName = null;
        empsList.clear();
    }

    public Country getSelectedCountry() {
        return selectedCountry;
    }

    public void setSelectedCountry(Country selectedCountry) {
        this.selectedCountry = selectedCountry;
    }

    public City getSelectedCity() {
        return selectedCity;
    }

    public void setSelectedCity(City selectedCity) {
        this.selectedCity = selectedCity;
    }

    public Address getSelectedAddress() {
        return selectedAddress;
    }

    public void setSelectedAddress(Address selectedAddress) {
        this.selectedAddress = selectedAddress;
    }
    
    public Employees getSelectedEmployee() {
        return selectedEmployee;
    }

    public void setSelectedEmployee(Employees selectedEmployee) {
        this.selectedEmployee = selectedEmployee;
    }

    public Users getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(Users selectedUser) {
        this.selectedUser = selectedUser;
    }

    public Department getSelectedDepartment() {
        return selectedDepartment;
    }

    public void setSelectedDepartment(Department selectedDepartment) {
        this.selectedDepartment = selectedDepartment;
    }

    public List<Employees> getEmpsList() {
        return empsList;
    }

    public void setEmpsList(List<Employees> empsList) {
        this.empsList = empsList;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
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

    public EmployeesDAO getEmployeesDAO() {
        return employeesDAO;
    }
}
