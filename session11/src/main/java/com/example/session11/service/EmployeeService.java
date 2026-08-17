package com.example.session11.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.session11.dto.EmployeeCreateDTO;
import com.example.session11.dto.EmployeeUpdateDTO;
import com.example.session11.entity.Employee;
import com.example.session11.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final Cloudinary cloudinary;
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee addEmployee(EmployeeCreateDTO dto) {
        try {
            Map upload = cloudinary.uploader().upload(dto.getAvatarUrl().getBytes(), ObjectUtils.emptyMap());
            Employee employee = new Employee();
            employee.setFullName(dto.getFullName());
            employee.setEmail(dto.getEmail());
            employee.setDepartment(dto.getDepartment());
            employee.setAvatarUrl( upload.get("url").toString());
            return employeeRepository.save(employee);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Employee updateEmployee(int id,EmployeeUpdateDTO dto){
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));
        employee.setFullName(dto.getFullName());
        employee.setEmail(dto.getEmail());
        if(dto.getAvatarUrl() != null && !dto.getAvatarUrl().isEmpty()) {
            try {
                Map upload = cloudinary.uploader().upload(dto.getAvatarUrl().getBytes(), ObjectUtils.emptyMap());
                employee.setAvatarUrl(upload.get("url").toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return employeeRepository.save(employee);
    }
}
