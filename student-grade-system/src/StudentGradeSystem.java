import java.util.Scanner;

public class StudentGradeSystem {

    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {

            System.out.println("   STUDENT GRADE MANAGEMENT");

            
            System.out.print("No of students do you want to enter? ");
            int numberOfStudents = scanner.nextInt();

            int[] rollNumbers = new int[numberOfStudents];

            String[] names = new String[numberOfStudents];

            double[] dbaMarks = new double[numberOfStudents];
            double[] dsaMarks = new double[numberOfStudents];
            double[] osMarks = new double[numberOfStudents];

            double[] totals = new double[numberOfStudents];
            double[] averages = new double[numberOfStudents];

            String[] grades = new String[numberOfStudents];
             //Enter student details
            for (int i = 0; i < numberOfStudents; i++) {

                System.out.println("\nEnter details for Student " + (i + 1));

                System.out.print("Roll Number: ");
                rollNumbers[i] = scanner.nextInt();

                scanner.nextLine();

                System.out.print("Name: ");
                names[i] = scanner.nextLine();

                System.out.print("DBA Marks: ");
                dbaMarks[i] = scanner.nextDouble();

                System.out.print("DSA Marks: ");
                dsaMarks[i] = scanner.nextDouble();

                System.out.print("OS Marks: ");
                osMarks[i] = scanner.nextDouble();

                totals[i] = dbaMarks[i] + dsaMarks[i] + osMarks[i];
                averages[i] = totals[i] / 3;


            if (averages[i] >= 90) {
                  grades[i] = "A";

            } else if (averages[i] >= 80) {
                       grades[i] = "B";

            } else if (averages[i] >= 70) {
                       grades[i] = "C";

            } else if (averages[i] <= 50) {
                       grades[i] = "F";
}
            }

            System.out.println("\nStudent data entered successfully.");
            System.out.println("\n===== STUDENT FINAL RESULTS =====");

            for (int i = 0; i < numberOfStudents; i++) {

                System.out.println("\nStudent " + (i + 1));

                System.out.println("Roll Number: " + rollNumbers[i]);

                System.out.println("Name: " + names[i]);

                System.out.println("Total Marks: " + totals[i]);

                System.out.printf("Average Marks: %.2f%n", averages[i]);
                
                System.out.println("Grade: " + grades[i]);
            }

            System.out.println("\n===== SEARCH STUDENT =====");

System.out.print("Enter roll number to search: ");
int searchRollNumber = scanner.nextInt();

boolean found = false;

for (int i = 0; i < numberOfStudents; i++) {

    if (rollNumbers[i] == searchRollNumber) {

        System.out.println("\nStudent Found");
        System.out.println("----------------------");
        System.out.println("Roll Number: " + rollNumbers[i]);
        System.out.println("Name: " + names[i]);
        System.out.println("DBA Marks: " + dbaMarks[i]);
        System.out.println("DSA Marks: " + dsaMarks[i]);
        System.out.println("OS Marks: " + osMarks[i]);
        System.out.println("Total Marks: " + totals[i]);
        System.out.printf("Average Marks: %.2f%n", averages[i]);
        System.out.println("Grade: " + grades[i]);

        found = true;
        break;
    }
}

if (!found) {
    System.out.println("\nStudent not found.");
}
        }
    }
}