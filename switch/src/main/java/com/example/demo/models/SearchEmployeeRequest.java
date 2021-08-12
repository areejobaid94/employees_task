package com.example.demo.models;

public class SearchEmployeeRequest {
    private String  firstName, lastName;
    private Integer depId;

    public SearchEmployeeRequest(String firstName, String lastName, Integer depId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.depId = depId;
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

    public Integer getDepId() {
        return depId;
    }

    public void setDepId(int depId) {
        this.depId = depId;
    }
}
