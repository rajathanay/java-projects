package com.employee.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.employee.dto.EmployeeRequest;
import com.employee.dto.EmployeeResponse;
import com.employee.entity.Employee;
import com.employee.exception.EmployeeNotFoundException;
import com.employee.mapper.EmployeeMapper;
import com.employee.repository.EmployeeRepository;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeService(
            EmployeeRepository employeeRepository,
            EmployeeMapper employeeMapper) {

        this.employeeRepository =
                employeeRepository;

        this.employeeMapper =
                employeeMapper;
    }

    public EmployeeResponse createEmployee(
            EmployeeRequest request) {

        employeeRepository
                .findByEmail(request.email())
                .ifPresent(employee -> {

                    throw new IllegalArgumentException(
                            "Employee email already exists."
                    );
                });

        Employee employee =
                employeeMapper.toEntity(
                        request
                );

        Employee savedEmployee =
                employeeRepository.save(
                        employee
                );

        return employeeMapper.toResponse(
                savedEmployee
        );
    }

    public List<EmployeeResponse>
            getAllEmployees() {

        return employeeRepository
                .findAll()
                .stream()
                .map(
                        employeeMapper::toResponse
                )
                .toList();
    }

    public EmployeeResponse getEmployeeById(
            Long id) {

        Employee employee =
                findEmployee(id);

        return employeeMapper.toResponse(
                employee
        );
    }

    public EmployeeResponse updateEmployee(
            Long id,
            EmployeeRequest request) {

        Employee employee =
                findEmployee(id);

        employeeRepository
                .findByEmail(request.email())
                .filter(existing ->
                        !existing.getId().equals(id)
                )
                .ifPresent(existing -> {

                    throw new IllegalArgumentException(
                            "Employee email already exists."
                    );
                });

        employeeMapper.updateEntity(
                employee,
                request
        );

        Employee updatedEmployee =
                employeeRepository.save(
                        employee
                );

        return employeeMapper.toResponse(
                updatedEmployee
        );
    }

    public void deleteEmployee(
            Long id) {

        Employee employee =
                findEmployee(id);

        employeeRepository.delete(
                employee
        );
    }

    private Employee findEmployee(
            Long id) {

        return employeeRepository
                .findById(id)
                .orElseThrow(
                        () ->
                            new EmployeeNotFoundException(
                                    "Employee not found with id: "
                                            + id
                            )
                );
    }
}