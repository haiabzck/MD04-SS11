package com.example.session11.controller;

import com.example.session11.dto.EmployeeCreateDTO;
import com.example.session11.dto.EmployeeUpdateDTO;
import com.example.session11.entity.Employee;
import com.example.session11.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }
    @PostMapping
    public ResponseEntity<?> createEmployee(@Valid @ModelAttribute EmployeeCreateDTO employeeCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.addEmployee(employeeCreateDTO));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable int id, @Valid @ModelAttribute EmployeeUpdateDTO employeeUpdateDTO) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, employeeUpdateDTO));
    }
}
