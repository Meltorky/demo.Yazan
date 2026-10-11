package com.Yazan.demo.controllers;

import com.Yazan.demo.entities.Employee;
import com.Yazan.demo.shared.CustomResponseException;
import com.Yazan.demo.shared.GlobalResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// http://localhost:8080/

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    List<Employee> employees = new ArrayList<>(
            List.of(
                    new Employee(
                            UUID.fromString("550e8400-e29b-41d4-a716-446655440001"),
                            "John",
                            "Smith",
                            "john.smith@example.com",
                            "+1-202-555-0101",
                            LocalDate.of(2022, 3, 15),
                            "Software Engineer",
                            UUID.fromString("550e8400-e29b-41d4-a716-446655440101")
                    ),
                    new Employee(
                            UUID.fromString("550e8400-e29b-41d4-a716-446655440002"),
                            "Sarah",
                            "Johnson",
                            "sarah.johnson@example.com",
                            "+1-202-555-0102",
                            LocalDate.of(2021, 7, 20),
                            "Project Manager",
                            UUID.fromString("550e8400-e29b-41d4-a716-446655440102")
                    ),
                    new Employee(
                            UUID.fromString("550e8400-e29b-41d4-a716-446655440003"),
                            "Michael",
                            "Brown",
                            "michael.brown@example.com",
                            "+1-202-555-0103",
                            LocalDate.of(2023, 1, 10),
                            "UI/UX Designer",
                            UUID.fromString("550e8400-e29b-41d4-a716-446655440103")
                    )));


    @GetMapping("/get/{empId}")
    public ResponseEntity<GlobalResponse<Employee>> getEmployee(
            @PathVariable UUID empId) {

        Employee emp = employees.stream()
                .filter(e -> e.getId().equals(empId))
                .findFirst()
                .orElseThrow(() ->
                        CustomResponseException.resourceMisMatch101(
                                "Employee with ID " + empId + " not found"
                        )
                );

        return ResponseEntity.ok(
                GlobalResponse.success(
                        emp, "Employee retrieved successfully"
                )
        );
    }

    @PostMapping("/create")
    public ResponseEntity<GlobalResponse<Employee>> create(@Valid @RequestBody Employee e) {
        e.setId(UUID.randomUUID());
        e.setDepartmentId(UUID.randomUUID());
        employees.add(e);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(GlobalResponse.success(e, "created successfully"));
    }

}
