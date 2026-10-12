package com.Yazan.demo.services;

import com.Yazan.demo.abstracts.EmployeeService;
import com.Yazan.demo.entities.Employee;
import com.Yazan.demo.shared.CustomResponseException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeeServiceImp implements EmployeeService {

    List<Employee> employees = new ArrayList<>(
            List.of(
                    new Employee(
                            java.util.UUID.fromString("550e8400-e29b-41d4-a716-446655440001"),
                            "John",
                            "Smith",
                            "john.smith@example.com",
                            "+1-202-555-0101",
                            LocalDate.of(2022, 3, 15),
                            "Software Engineer",
                            java.util.UUID.fromString("550e8400-e29b-41d4-a716-446655440101")
                    ),
                    new Employee(
                            java.util.UUID.fromString("550e8400-e29b-41d4-a716-446655440002"),
                            "Sarah",
                            "Johnson",
                            "sarah.johnson@example.com",
                            "+1-202-555-0102",
                            LocalDate.of(2021, 7, 20),
                            "Project Manager",
                            java.util.UUID.fromString("550e8400-e29b-41d4-a716-446655440102")
                    ),
                    new Employee(
                            java.util.UUID.fromString("550e8400-e29b-41d4-a716-446655440003"),
                            "Michael",
                            "Brown",
                            "michael.brown@example.com",
                            "+1-202-555-0103",
                            LocalDate.of(2023, 1, 10),
                            "UI/UX Designer",
                            java.util.UUID.fromString("550e8400-e29b-41d4-a716-446655440103")
                    )));

    public Employee findOne(UUID employeeId) {
        return employees.stream()
                .filter(e -> e.getId().equals(employeeId))
                .findFirst()
                .orElseThrow(() ->
                        CustomResponseException.resourceMisMatch101(
                                "Employee with ID " + employeeId + " not found"
                        )
                );
    }
}
