package com.student.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.student.model.Student;

public class StudentStore<T extends Student> {

    private final List<T> students = new ArrayList<>();
    private final Map<Integer, T> studentMap = new HashMap<>();

    public void add(T student) {
        students.add(student);
        studentMap.put(
                student.getRollNumber(),
                student
        );
    }

    public T findByRollNumber(int rollNumber) {
        return studentMap.get(rollNumber);
    }

    public void remove(T student) {
        students.remove(student);
        studentMap.remove(
                student.getRollNumber()
        );
    }

    public List<T> getAll() {
        return students;
    }

    public void clear() {
        students.clear();
        studentMap.clear();
    }

    public void addAll(List<T> newStudents) {
        clear();

        for (T student : newStudents) {
            add(student);
        }
    }
}