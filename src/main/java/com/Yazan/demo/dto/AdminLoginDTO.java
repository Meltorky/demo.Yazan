package com.Yazan.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AdminLoginDTO(
        @NotBlank(message = "Username cannot be blank")
        @Size(max = 100, message = "Username must be at most 100 characters")
        @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Username must contain only letters and numbers")
        String userName,

        @NotBlank(message = "Password cannot be blank")
        @Size(max = 100, message = "Password must be at most 100 characters")
        @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Password must contain only letters and numbers")
        String password
) {
}