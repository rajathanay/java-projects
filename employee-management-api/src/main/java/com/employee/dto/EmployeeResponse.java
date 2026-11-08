package com.employee.dto;

public record EmployeeResponse(

        Long id,
        String firstName,
        String lastName,
        String email,
        String department,
        Double salary

) {
}