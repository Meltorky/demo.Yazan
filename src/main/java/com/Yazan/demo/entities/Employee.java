package com.Yazan.demo.entities;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    private UUID id;

    @NotNull(message = "First name is Required")
    private String firstName;

    @NotNull(message = "Last name is Required")
    private String lastName;

    @Email(message = "Email is Required")
    private String email;
    private String phoneNumber;

    @PastOrPresent() // not future date
    private LocalDate hireDate;
    private String position;
    private UUID departmentId;

}
