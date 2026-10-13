package com.Yazan.demo.services;

import com.Yazan.demo.abstracts.EmployeeService;
import com.Yazan.demo.dto.EmployeeCreate;
import com.Yazan.demo.entities.Employee;
import com.Yazan.demo.repositories.EmployeeRepo;
import com.Yazan.demo.shared.CustomResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EmployeeServiceImp implements EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;

    public Employee findOne(UUID employeeId) {
        return employeeRepo.findById(employeeId)
                .orElseThrow(() ->
                        CustomResponseException.resourceNotFound101(
                                "Employee with ID " + employeeId + " not found"
                        )
                );
    }

    public Employee Create(EmployeeCreate employeeCreate) {

        Employee employee = new Employee();

        employee.setFirstName(employeeCreate.firstName());
        employee.setLastName(employeeCreate.lastName());
        employee.setPosition(employeeCreate.position());
        employee.setHireDate(employeeCreate.hireDate());
        employee.setPhoneNumber(employeeCreate.phoneNumber());
        employee.setEmail(employeeCreate.email());

        return employeeRepo.save(employee);
    }
}
