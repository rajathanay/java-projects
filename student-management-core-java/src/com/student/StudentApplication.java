package com.student;

import com.student.exception.StudentNotFoundException;
import com.student.model.Department;
import com.student.model.Grade;
import com.student.model.Student;
import com.student.service.StudentService;
import com.student.util.FileStorage;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class StudentApplication {

    private static final Scanner scanner =
            new Scanner(System.in);

    private static final StudentService service =
            new StudentService();

    public static void main(String[] args) {

        loadExistingData();

        boolean running = true;

        while (running) {

            showMenu();

            try {

                int choice =
                        Integer.parseInt(
                                scanner.nextLine()
                        );

                switch (choice) {

                    case 1 -> addStudent();

                    case 2 -> updateStudent();

                    case 3 -> deleteStudent();

                    case 4 -> searchStudent();

                    case 5 -> displayStudents(
                            service.getAllStudents()
                    );

                    case 6 -> showSortedStudents();

                    case 7 -> filterByGrade();

                    case 8 -> showReports();

                    case 9 -> saveFiles();

                    case 10 -> loadFromCsv();

                    case 11 -> loadSerializedData();

                    case 12 -> {
                        saveFiles();
                        running = false;
                        System.out.println(
                                "Application closed."
                        );
                    }

                    default ->
                        System.out.println(
                                "Invalid menu option."
                        );
                }

            } catch (
                    NumberFormatException
                    | InputMismatchException e) {

                System.out.println(
                        "Please enter a valid number."
                );

            } catch (Exception e) {

                System.out.println(
                        "Error: "
                                + e.getMessage()
                );
            }
        }

        scanner.close();
    }

    private static void showMenu() {

        System.out.println(
                "\n========== STUDENT MANAGEMENT =========="
        );

        System.out.println(
                "1. Add Student"
        );

        System.out.println(
                "2. Update Student"
        );

        System.out.println(
                "3. Delete Student"
        );

        System.out.println(
                "4. Search Student"
        );

        System.out.println(
                "5. Display All Students"
        );

        System.out.println(
                "6. Sort Students"
        );

        System.out.println(
                "7. Filter by Grade"
        );

        System.out.println(
                "8. Reports"
        );

        System.out.println(
                "9. Save CSV / JSON / Serialized"
        );

        System.out.println(
                "10. Load from CSV"
        );

        System.out.println(
                "11. Load Serialized Data"
        );

        System.out.println(
                "12. Exit"
        );

        System.out.print(
                "Choose option: "
        );
    }

    private static void addStudent() {

        System.out.print(
                "Roll number: "
        );

        int rollNumber =
                Integer.parseInt(
                        scanner.nextLine()
                );

        System.out.print(
                "Name: "
        );

        String name =
                scanner.nextLine();

        System.out.print(
                "Date of birth (yyyy-MM-dd): "
        );

        LocalDate dateOfBirth =
                LocalDate.parse(
                        scanner.nextLine()
                );

        Department department =
                readDepartment();

        System.out.print(
                "Marks: "
        );

        double marks =
                Double.parseDouble(
                        scanner.nextLine()
                );

        Student student =
                new Student(
                        rollNumber,
                        name,
                        dateOfBirth,
                        department,
                        marks
                );

        service.addStudent(
                student
        );

        System.out.println(
                "Student added successfully."
        );
    }

    private static void updateStudent()
            throws StudentNotFoundException {

        System.out.print(
                "Roll number to update: "
        );

        int rollNumber =
                Integer.parseInt(
                        scanner.nextLine()
                );

        Student current =
                service.searchStudent(
                        rollNumber
                );

        System.out.println(
                "Current student:"
        );

        displayStudents(
                List.of(current)
        );

        System.out.print(
                "New name: "
        );

        String name =
                scanner.nextLine();

        System.out.print(
                "New marks: "
        );

        double marks =
                Double.parseDouble(
                        scanner.nextLine()
                );

        service.updateStudent(
                rollNumber,
                name,
                marks
        );

        System.out.println(
                "Student updated successfully."
        );
    }

    private static void deleteStudent()
            throws StudentNotFoundException {

        System.out.print(
                "Roll number to delete: "
        );

        int rollNumber =
                Integer.parseInt(
                        scanner.nextLine()
                );

        service.deleteStudent(
                rollNumber
        );

        System.out.println(
                "Student deleted successfully."
        );
    }

    private static void searchStudent()
            throws StudentNotFoundException {

        System.out.print(
                "Enter roll number: "
        );

        int rollNumber =
                Integer.parseInt(
                        scanner.nextLine()
                );

        Student student =
                service.searchStudent(
                        rollNumber
                );

        displayStudents(
                List.of(student)
        );
    }

    private static void showSortedStudents() {

        System.out.println(
                "\n1. Roll Number"
        );

        System.out.println(
                "2. Name"
        );

        System.out.println(
                "3. Marks"
        );

        System.out.println(
                "4. Grade"
        );

        System.out.print(
                "Sort by: "
        );

        int choice =
                Integer.parseInt(
                        scanner.nextLine()
                );

        List<Student> students =
                switch (choice) {

                    case 1 ->
                        service.sortByRollNumber();

                    case 2 ->
                        service.sortByName();

                    case 3 ->
                        service.sortByMarks();

                    case 4 ->
                        service.sortByGrade();

                    default ->
                        service.getAllStudents();
                };

        displayStudents(students);
    }

    private static void filterByGrade() {

        System.out.print(
                "Grade (A/B/C/F): "
        );

        Grade grade =
                Grade.valueOf(
                        scanner.nextLine()
                                .trim()
                                .toUpperCase()
                );

        displayStudents(
                service.filterByGrade(
                        grade
                )
        );
    }

    private static void showReports() {

        System.out.println(
                "\n--- Top 5 Students ---"
        );

        displayStudents(
                service.getTopFiveStudents()
        );

        System.out.println(
                "\n--- Failed Students ---"
        );

        displayStudents(
                service.getFailedStudents()
        );

        System.out.printf(
                "%nClass Average: %.2f%n",
                service.getClassAverage()
        );

        System.out.println(
                "\nStudent Names:"
        );

        service.getStudentNames()
                .forEach(
                        System.out::println
                );

        System.out.println(
                "\nStudents Grouped by Grade:"
        );

        Map<Grade, List<Student>> groups =
                service.groupStudentsByGrade();

        groups.forEach(
                (grade, students) -> {

                    System.out.println(
                            grade
                                    + " = "
                                    + students.size()
                    );
                }
        );
    }

    private static Department
            readDepartment() {

        System.out.println(
                "\nDepartments:"
        );

        Department[] departments =
                Department.values();

        for (int i = 0;
             i < departments.length;
             i++) {

            System.out.println(
                    (i + 1)
                            + ". "
                            + departments[i]
            );
        }

        System.out.print(
                "Choose department: "
        );

        int choice =
                Integer.parseInt(
                        scanner.nextLine()
                );

        if (
            choice < 1
            || choice > departments.length
        ) {

            throw new IllegalArgumentException(
                    "Invalid department."
            );
        }

        return departments[
                choice - 1
        ];
    }

    private static void displayStudents(
            List<Student> students) {

        if (students.isEmpty()) {

            System.out.println(
                    "No students found."
            );

            return;
        }

        System.out.println();

        System.out.printf(
                "%-6s %-20s %-5s %-25s %-8s %-5s%n",
                "Roll",
                "Name",
                "Age",
                "Department",
                "Marks",
                "Grade"
        );

        System.out.println(
                "--------------------------------------------------------------------------"
        );

        students.forEach(
                System.out::println
        );
    }

    private static void saveFiles() {

        try {

            List<Student> students =
                    service.getAllStudents();

            FileStorage.saveCsv(
                    students
            );

            FileStorage.saveJson(
                    students
            );

            FileStorage.saveSerialized(
                    students
            );

            System.out.println(
                    "Student data saved."
            );

        } catch (Exception e) {

            System.out.println(
                    "Unable to save data: "
                            + e.getMessage()
            );
        }
    }

    private static void loadFromCsv() {

        try {

            service.replaceStudents(
                    FileStorage.loadCsv()
            );

            System.out.println(
                    "CSV data loaded."
            );

        } catch (Exception e) {

            System.out.println(
                    "Unable to load CSV: "
                            + e.getMessage()
            );
        }
    }

    private static void loadSerializedData() {

        try {

            service.replaceStudents(
                    FileStorage.loadSerialized()
            );

            System.out.println(
                    "Serialized data loaded."
            );

        } catch (Exception e) {

            System.out.println(
                    "Unable to load data: "
                            + e.getMessage()
            );
        }
    }

    private static void loadExistingData() {

        try {

            List<Student> students =
                    FileStorage.loadSerialized();

            service.replaceStudents(
                    students
            );

            if (!students.isEmpty()) {

                System.out.println(
                        students.size()
                                + " student records loaded."
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "Starting with empty student data."
            );
        }
    }
}