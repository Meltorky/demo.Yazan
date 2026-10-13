package com.Yazan.demo.controllers;

import com.Yazan.demo.abstracts.EmployeeService;
import com.Yazan.demo.dto.EmployeeCreate;
import com.Yazan.demo.entities.Employee;
import com.Yazan.demo.shared.GlobalResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

// http://localhost:8080/

@RestController
@RequestMapping("/employees")
public class EmployeeController {


    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/get/{empId}")
    public ResponseEntity<GlobalResponse<Employee>> getEmployee(@PathVariable UUID empId) {
        return ResponseEntity.ok(
                GlobalResponse.success(
                        employeeService.findOne(empId), "Employee retrieved successfully"
                )
        );
    }

    @PostMapping("/create")
    public ResponseEntity<GlobalResponse<Employee>> create(@Valid @RequestBody EmployeeCreate e) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(GlobalResponse.success(employeeService.Create(e), "created successfully"));
    }

}
