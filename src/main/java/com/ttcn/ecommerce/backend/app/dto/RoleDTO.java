package com.ttcn.ecommerce.backend.app.dto;

import com.ttcn.ecommerce.backend.app.entity.Customer;
import com.ttcn.ecommerce.backend.app.entity.Employee;

import java.util.ArrayList;
import java.util.List;

public class RoleDTO extends AbstractDTO{

    private String code;

    private String name;

    private List<Employee> employees = new ArrayList<>();

    private List<Customer> customers = new ArrayList<>();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
}
