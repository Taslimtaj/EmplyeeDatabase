package com.example.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateEmployeeRequest {

    @NotBlank
    private String employeeId;

    @NotBlank
    private String employeeName;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
}
