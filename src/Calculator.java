import java.util.InputMismatchException;
import java.util.Scanner;

public class Calculator {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        try {

            System.out.println("Simple Calculator");

            System.out.print(
                "Enter operation (+, -, *, /, %, ^, s for square root): "
            );

            char operator = scanner.next().charAt(0);

            double result;

            if (operator == 's') {

                System.out.print("Enter number: ");
                double number = scanner.nextDouble();

                if (number >= 0) {
                    result = Math.sqrt(number);
                    System.out.println("Result: " + result);
                } else {
                    System.out.println(
                        "Cannot calculate square root of negative number."
                    );
                }

            } else {

                System.out.print("Enter first number: ");
                double num1 = scanner.nextDouble();

                System.out.print("Enter second number: ");
                double num2 = scanner.nextDouble();

                switch (operator) {

                    case '+':
                        result = num1 + num2;
                        System.out.println("Result: " + result);
                        break;

                    case '-':
                        result = num1 - num2;
                        System.out.println("Result: " + result);
                        break;

                    case '*':
                        result = num1 * num2;
                        System.out.println("Result: " + result);
                        break;

                    case '/':
                        if (num2 != 0) {
                            result = num1 / num2;
                            System.out.println("Result: " + result);
                        } else {
                            System.out.println("Cannot divide by zero.");
                        }
                        break;

                    case '%':
                        if (num2 != 0) {
                            result = num1 % num2;
                            System.out.println("Result: " + result);
                        } else {
                            System.out.println(
                                "Cannot perform modulus by zero."
                            );
                        }
                        break;

                    case '^':
                        result = Math.pow(num1, num2);
                        System.out.println("Result: " + result);
                        break;

                    default:
                        System.out.println("Invalid operation.");
                }
            }

        } catch (InputMismatchException e) {

            System.out.println(
                "Invalid input. Please enter a valid number."
            );

        }

        scanner.close();
    }
}