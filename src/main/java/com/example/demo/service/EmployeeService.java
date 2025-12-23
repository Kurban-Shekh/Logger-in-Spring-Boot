package com.example.demo.service;

import com.example.demo.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService{
    List<Employee> getAllEmployee();
    Optional<Employee> getEmployeeById(Long employeeId);
    Employee createEmployee(Employee employee);
    Optional<Employee> updateEmployee(Long employeeId,Employee employee);
    boolean deleteEmployeeById(Long employeeId);
}
