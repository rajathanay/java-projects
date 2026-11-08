package com.employee.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.employee.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(
            EmployeeNotFoundException.class
    )
    public ResponseEntity<ErrorResponse>
            handleEmployeeNotFound(

            EmployeeNotFoundException exception) {

        ErrorResponse response =
                new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.NOT_FOUND.value(),
                        "Not Found",
                        exception.getMessage()
                );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(
            IllegalArgumentException.class
    )
    public ResponseEntity<ErrorResponse>
            handleIllegalArgument(

            IllegalArgumentException exception) {

        ErrorResponse response =
                new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        "Bad Request",
                        exception.getMessage()
                );

        return ResponseEntity
                .badRequest()
                .body(response);
    }

    @ExceptionHandler(
            MethodArgumentNotValidException.class
    )
    public ResponseEntity<Map<String, String>>
            handleValidation(

            MethodArgumentNotValidException exception) {

        Map<String, String> errors =
                new LinkedHashMap<>();

        exception
                .getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        return ResponseEntity
                .badRequest()
                .body(errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse>
            handleGeneralException(

            Exception exception) {

        ErrorResponse response =
                new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Internal Server Error",
                        "Unexpected error occurred."
                );

        return ResponseEntity
                .status(
                        HttpStatus.INTERNAL_SERVER_ERROR
                )
                .body(response);
    }
}