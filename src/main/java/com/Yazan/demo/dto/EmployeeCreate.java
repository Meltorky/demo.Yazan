package com.Yazan.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;


public record EmployeeCreate(
        @NotNull(message = "First name is Required")
        String firstName,

        @NotNull(message = "Last name is Required")
        String lastName,

        @Email(message = "Email is Required")
        String email,

        String phoneNumber,

        @PastOrPresent() // not future date
        LocalDate hireDate,

        String position
) {
}
