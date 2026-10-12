package com.Yazan.demo.controllers;

import com.Yazan.demo.abstracts.EmployeeService;
import com.Yazan.demo.entities.Employee;
import com.Yazan.demo.shared.GlobalResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

//    @PostMapping("/create")
//    public ResponseEntity<GlobalResponse<Employee>> create(@Valid @RequestBody Employee e) {
//        e.setId(UUID.randomUUID());
//        e.setDepartmentId(UUID.randomUUID());
//        employees.add(e);
//        return ResponseEntity
//                .status(HttpStatus.CREATED)
//                .body(GlobalResponse.success(e, "created successfully"));
//    }

}
