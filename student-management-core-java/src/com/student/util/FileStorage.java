package com.student.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import com.student.model.Department;
import com.student.model.Student;

public class FileStorage {

    private static final Path DATA_FOLDER =
            Paths.get("data");

    private static final Path CSV_FILE =
            DATA_FOLDER.resolve("students.csv");

    private static final Path JSON_FILE =
            DATA_FOLDER.resolve("students.json");

    private static final Path SERIALIZED_FILE =
            DATA_FOLDER.resolve("students.dat");

    public static void saveCsv(
            List<Student> students)
            throws IOException {

        createDataFolder();

        List<String> lines =
                new ArrayList<>();

        lines.add(
                "rollNumber,name,dateOfBirth,department,marks"
        );

        for (Student student : students) {

            lines.add(
                    student.getRollNumber()
                            + ","
                            + student.getName()
                            + ","
                            + student.getDateOfBirth()
                            + ","
                            + student.getDepartment()
                            + ","
                            + student.getMarks()
            );
        }

        Files.write(
                CSV_FILE,
                lines
        );
    }

    public static List<Student> loadCsv()
            throws IOException {

        List<Student> students =
                new ArrayList<>();

        if (!Files.exists(CSV_FILE)) {
            return students;
        }

        List<String> lines =
                Files.readAllLines(
                        CSV_FILE
                );

        for (int i = 1;
             i < lines.size();
             i++) {

            String[] values =
                    lines.get(i)
                            .split(",");

            Student student =
                    new Student(
                            Integer.parseInt(
                                    values[0]
                            ),
                            values[1],
                            LocalDate.parse(
                                    values[2]
                            ),
                            Department.valueOf(
                                    values[3]
                            ),
                            Double.parseDouble(
                                    values[4]
                            )
                    );

            students.add(student);
        }

        return students;
    }

    public static void saveJson(
            List<Student> students)
            throws IOException {

        createDataFolder();

        StringBuilder json =
                new StringBuilder();

        json.append("[\n");

        for (int i = 0;
             i < students.size();
             i++) {

            Student student =
                    students.get(i);

            json.append("  {\n");

            json.append(
                    "    \"rollNumber\": "
                            + student.getRollNumber()
                            + ",\n"
            );

            json.append(
                    "    \"name\": \""
                            + escape(
                                    student.getName()
                            )
                            + "\",\n"
            );

            json.append(
                    "    \"dateOfBirth\": \""
                            + student.getDateOfBirth()
                            + "\",\n"
            );

            json.append(
                    "    \"department\": \""
                            + student.getDepartment()
                            + "\",\n"
            );

            json.append(
                    "    \"marks\": "
                            + student.getMarks()
                            + "\n"
            );

            json.append("  }");

            if (i < students.size() - 1) {
                json.append(",");
            }

            json.append("\n");
        }

        json.append("]");

        Files.writeString(
                JSON_FILE,
                json.toString()
        );
    }

    public static void saveSerialized(
            List<Student> students)
            throws IOException {

        createDataFolder();

        try (
            ObjectOutputStream output =
                    new ObjectOutputStream(
                            Files.newOutputStream(
                                    SERIALIZED_FILE
                            )
                    )
        ) {

            output.writeObject(
                    new ArrayList<>(students)
            );
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Student>
            loadSerialized()
            throws IOException,
                   ClassNotFoundException {

        if (!Files.exists(
                SERIALIZED_FILE)) {

            return new ArrayList<>();
        }

        try (
            ObjectInputStream input =
                    new ObjectInputStream(
                            Files.newInputStream(
                                    SERIALIZED_FILE
                            )
                    )
        ) {

            return (List<Student>)
                    input.readObject();
        }
    }

    private static void createDataFolder()
            throws IOException {

        Files.createDirectories(
                DATA_FOLDER
        );
    }

    private static String escape(
            String text) {

        return text
                .replace("\\", "\\\\")
                .replace("\"", "\\\"");
    }
}