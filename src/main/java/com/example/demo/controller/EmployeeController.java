package com.example.demo.controller;

import com.example.demo.entity.Employee;
import com.example.demo.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Employee>> getEmployees(){
        return ResponseEntity.ok(employeeService.getAllEmployee());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long employeeId){
        Employee employee = employeeService.getEmployeeById(employeeId).orElse(null);
        if(employee==null){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok().body(employee);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee){
        return ResponseEntity.ok().body(employeeService.createEmployee(employee));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee){
        Employee updatedEmployee = employeeService.updateEmployee(id,employee).orElse(null);
        if(updatedEmployee==null){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok().body(updatedEmployee);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id){
        if(employeeService.deleteEmployeeById(id)){
            return ResponseEntity.ok().body("The employee has been deleted successfully.");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The employee is not present in our database.");
        }
    }


}
