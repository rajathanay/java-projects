package com.student.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.student.exception.StudentNotFoundException;
import com.student.model.Grade;
import com.student.model.Student;
import com.student.repository.StudentStore;

public class StudentService {

    private final StudentStore<Student> store =
            new StudentStore<>();

    public void addStudent(Student student) {

        if (store.findByRollNumber(
                student.getRollNumber()) != null) {

            throw new IllegalArgumentException(
                    "Roll number already exists."
            );
        }

        validateMarks(student.getMarks());

        store.add(student);
    }

    public Student searchStudent(int rollNumber)
            throws StudentNotFoundException {

        Student student =
                store.findByRollNumber(
                        rollNumber
                );

        if (student == null) {
            throw new StudentNotFoundException(
                    "Student not found for roll number: "
                            + rollNumber
            );
        }

        return student;
    }

    public void updateStudent(
            int rollNumber,
            String name,
            double marks)
            throws StudentNotFoundException {

        validateMarks(marks);

        Student student =
                searchStudent(rollNumber);

        student.setName(name);
        student.setMarks(marks);
    }

    public void deleteStudent(int rollNumber)
            throws StudentNotFoundException {

        Student student =
                searchStudent(rollNumber);

        store.remove(student);
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(
                store.getAll()
        );
    }

    public List<Student> sortByRollNumber() {

        return store.getAll()
                .stream()
                .sorted()
                .toList();
    }

    public List<Student> sortByName() {

        return store.getAll()
                .stream()
                .sorted(
                        Comparator.comparing(
                                Student::getName,
                                String.CASE_INSENSITIVE_ORDER
                        )
                )
                .toList();
    }

    public List<Student> sortByMarks() {

        return store.getAll()
                .stream()
                .sorted(
                        Comparator.comparingDouble(
                                Student::getMarks
                        ).reversed()
                )
                .toList();
    }

    public List<Student> sortByGrade() {

        return store.getAll()
                .stream()
                .sorted(
                        Comparator.comparing(
                                Student::getGrade
                        )
                )
                .toList();
    }

    public List<Student> filterByGrade(
            Grade grade) {

        return store.getAll()
                .stream()
                .filter(
                        student ->
                                student.getGrade()
                                        == grade
                )
                .toList();
    }

    public List<String> getStudentNames() {

        return store.getAll()
                .stream()
                .map(Student::getName)
                .toList();
    }

    public double getClassAverage() {

        return store.getAll()
                .stream()
                .map(Student::getMarks)
                .reduce(
                        0.0,
                        Double::sum
                )
                / Math.max(
                        store.getAll().size(),
                        1
                );
    }

    public List<Student> getTopFiveStudents() {

        return store.getAll()
                .stream()
                .sorted(
                        Comparator.comparingDouble(
                                Student::getMarks
                        ).reversed()
                )
                .limit(5)
                .toList();
    }

    public List<Student> getFailedStudents() {

        return store.getAll()
                .stream()
                .filter(
                        student ->
                                student.getGrade()
                                        == Grade.F
                )
                .toList();
    }

    public Map<Grade, List<Student>>
            groupStudentsByGrade() {

        return store.getAll()
                .stream()
                .collect(
                        Collectors.groupingBy(
                                Student::getGrade
                        )
                );
    }

    public void replaceStudents(
            List<Student> students) {

        store.addAll(students);
    }

    private void validateMarks(double marks) {

        if (marks < 0 || marks > 100) {
            throw new IllegalArgumentException(
                    "Marks must be between 0 and 100."
            );
        }
    }
}