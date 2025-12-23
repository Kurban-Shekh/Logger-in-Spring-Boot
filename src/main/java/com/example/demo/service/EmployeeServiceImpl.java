package com.example.demo.service;

import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> getAllEmployee() {
        log.info("Fetching all the employees from the database.");
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> getEmployeeById(Long employeeId) {
        log.info("Fetching the employee with employeeId {}", employeeId);
        return employeeRepository.findById(employeeId);
    }

    @Override
    public Employee createEmployee(Employee employee) {
        log.info("inserting a new employee in the database.");
        return employeeRepository.save(employee);
    }

    @Override
    public Optional<Employee> updateEmployee(Long employeeId, Employee employee) {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        if(employeeOptional.isEmpty()){
            log.error("The employee with employeeId {} is not present in our database, failed to update",employeeId);
            return Optional.empty();
        }

        Employee existingEmployee = employeeOptional.get();
        existingEmployee.setName(employee.getName());
        existingEmployee.setSalary(employee.getSalary());
        log.info("The employee with employeeId {} has successfully been updated in the database",employeeId);
        return Optional.of(employeeRepository.save(existingEmployee));
    }

    @Override
    public boolean deleteEmployeeById(Long employeeId) {
        if(employeeRepository.existsById(employeeId)){
            log.info("The employee with employeeId {} has successfully been deleted from the database.",employeeId);
            employeeRepository.deleteById(employeeId);
            return true;
        }

        log.error("The employee with employeeId {} is not present in our database, failed deletion",employeeId);
        return false;
    }
}
