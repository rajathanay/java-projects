package com.student.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

public class Student implements Comparable<Student>, Serializable {

    private static final long serialVersionUID = 1L;

    private int rollNumber;
    private String name;
    private LocalDate dateOfBirth;
    private Department department;
    private double marks;

    public Student(
            int rollNumber,
            String name,
            LocalDate dateOfBirth,
            Department department,
            double marks) {

        this.rollNumber = rollNumber;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.department = department;
        this.marks = marks;
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Department getDepartment() {
        return department;
    }

    public double getMarks() {
        return marks;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public void setMarks(double marks) {
        this.marks = marks;
    }

    public int getAge() {
        return Period.between(
                dateOfBirth,
                LocalDate.now()
        ).getYears();
    }

    public Grade getGrade() {

        if (marks >= 80) {
            return Grade.A;
        }

        if (marks >= 70) {
            return Grade.B;
        }

        if (marks >= 60) {
            return Grade.C;
        }

        return Grade.F;
    }

    @Override
    public int compareTo(Student other) {
        return Integer.compare(
                this.rollNumber,
                other.rollNumber
        );
    }

    @Override
    public String toString() {
        return String.format(
                "%-6d %-20s %-5d %-25s %-8.2f %-5s",
                rollNumber,
                name,
                getAge(),
                department,
                marks,
                getGrade()
        );
    }
}