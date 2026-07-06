package com.example.exception;

public class DuplicateEmployeeException extends RuntimeException {

    public DuplicateEmployeeException(String employeeId) {
        super("Employee already exists with id: " + employeeId);
    }
}
