package com.employee.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record EmployeeRequest(

        @NotBlank(message = "First name is required")
        @Size(
                min = 2,
                max = 100,
                message = "First name must be between 2 and 100 characters"
        )
        String firstName,

        @NotBlank(message = "Last name is required")
        @Size(
                min = 2,
                max = 100,
                message = "Last name must be between 2 and 100 characters"
        )
        String lastName,

        @NotBlank(message = "Email is required")
        @Email(message = "Enter a valid email address")
        String email,

        @NotBlank(message = "Department is required")
        String department,

        @NotNull(message = "Salary is required")
        @Positive(message = "Salary must be greater than zero")
        Double salary

) {
}