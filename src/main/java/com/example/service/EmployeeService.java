package com.example.service;

import com.example.exception.DuplicateEmployeeException;
import com.example.exception.EmployeeNotFoundException;
import com.example.model.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class EmployeeService {

    private final Map<String, Employee> employees = new ConcurrentHashMap<>();

    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees.values());
    }

    public Employee getEmployeeById(String employeeId) {
        Employee employee = employees.get(employeeId);
        if (employee == null) {
            throw new EmployeeNotFoundException(employeeId);
        }
        return employee;
    }

    public Employee createEmployee(String employeeId, String employeeName) {
        if (employees.containsKey(employeeId)) {
            throw new DuplicateEmployeeException(employeeId);
        }
        Employee employee = new Employee(employeeId, employeeName);
        employees.put(employeeId, employee);
        return employee;
    }

    public Employee updateEmployee(String employeeId, String employeeName) {
        if (!employees.containsKey(employeeId)) {
            throw new EmployeeNotFoundException(employeeId);
        }
        Employee employee = new Employee(employeeId, employeeName);
        employees.put(employeeId, employee);
        return employee;
    }

    public void deleteEmployee(String employeeId) {
        if (employees.remove(employeeId) == null) {
            throw new EmployeeNotFoundException(employeeId);
        }
    }
}
