package com.Yazan.demo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CarlosCreate(

        @NotNull(message = "First name is required")
        @Size(max = 30, message = "First name must be 30 characters or less")
        String firstName,

        @NotNull(message = "Last name is required")
        @Size(max = 30, message = "Last name must be 30 characters or less")
        String lastName,

        @Size(min = 11, max = 11, message = "Phone number must be exactly 11 characters")
        @Pattern(regexp = "^01\\d{9}$", message = "Phone number must start with '01' and be exactly 11 digits long.")
        String phoneNumber
) {
}
