package com.employee.mapper;

import org.springframework.stereotype.Component;

import com.employee.dto.EmployeeRequest;
import com.employee.dto.EmployeeResponse;
import com.employee.entity.Employee;

@Component
public class EmployeeMapper {

    public Employee toEntity(
            EmployeeRequest request) {

        Employee employee =
                new Employee();

        employee.setFirstName(
                request.firstName()
        );

        employee.setLastName(
                request.lastName()
        );

        employee.setEmail(
                request.email()
        );

        employee.setDepartment(
                request.department()
        );

        employee.setSalary(
                request.salary()
        );

        return employee;
    }

    public EmployeeResponse toResponse(
            Employee employee) {

        return new EmployeeResponse(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getDepartment(),
                employee.getSalary()
        );
    }

    public void updateEntity(
            Employee employee,
            EmployeeRequest request) {

        employee.setFirstName(
                request.firstName()
        );

        employee.setLastName(
                request.lastName()
        );

        employee.setEmail(
                request.email()
        );

        employee.setDepartment(
                request.department()
        );

        employee.setSalary(
                request.salary()
        );
    }
}