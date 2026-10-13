package com.Yazan.demo.abstracts;

import com.Yazan.demo.dto.EmployeeCreate;
import com.Yazan.demo.entities.Employee;

import java.util.UUID;

public interface EmployeeService {
    Employee findOne(UUID employeeId);

    Employee Create(EmployeeCreate employeeCreate);
}
