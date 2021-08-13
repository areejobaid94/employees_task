package com.example.demo.models;

import com.example.demo.entities.Department;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class SaveAndUpdateEmployeeRequest {
    private String  firstName, lastName;
    private float salary;
    private boolean isActive;
    private Integer departmentId;

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    private Integer employeeId ;

    public SaveAndUpdateEmployeeRequest(String first_name, String last_name, float salary, boolean is_active, Integer departmentId, Integer employeeId) {
        this.firstName = first_name;
        this.lastName = last_name;
        this.salary = salary;
        this.isActive = is_active;
        this.departmentId = departmentId;
        this.employeeId = employeeId;
    }

    public SaveAndUpdateEmployeeRequest() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }
}
