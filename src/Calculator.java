import java.util.InputMismatchException;
import java.util.Scanner;

public class Calculator {

    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {

            System.out.println("=");
            System.out.println("     SIMPLE CALCULATOR");
            System.out.println("=");

            System.out.println("+  Addition");
            System.out.println("-  Subtraction");
            System.out.println("*  Multiplication");
            System.out.println("/  Division");
            System.out.println("%  Modulus");
            System.out.println("^  Power");
            System.out.println("s  Square Root");

            System.out.print("\nChoose operation: ");
            char operator = scanner.next().charAt(0);

            // Validate the operator before asking for numbers
            if (
                operator != '+' &&
                operator != '-' &&
                operator != '*' &&
                operator != '/' &&
                operator != '%' &&
                operator != '^' &&
                operator != 's'
            ) {
                System.out.println("Invalid operation.");
                return;
            }

            double result;

            // Square root requires only one number
            if (operator == 's') {

                System.out.print("Enter number: ");
                double number = scanner.nextDouble();

                if (number >= 0) {

                    result = squareRoot(number);

                    System.out.println("Result: " + result);

                } else {

                    System.out.println(
                        "Cannot calculate square root of a negative number."
                    );
                }

            } else {

                // All other operations require two numbers
                System.out.print("Enter first number: ");
                double num1 = scanner.nextDouble();

                System.out.print("Enter second number: ");
                double num2 = scanner.nextDouble();

                switch (operator) {

                    case '+':
                        result = add(num1, num2);
                        System.out.println("Result: " + result);
                        break;

                    case '-':
                        result = subtract(num1, num2);
                        System.out.println("Result: " + result);
                        break;

                    case '*':
                        result = multiply(num1, num2);
                        System.out.println("Result: " + result);
                        break;

                    case '/':
                        if (num2 != 0) {

                            result = divide(num1, num2);

                            System.out.println("Result: " + result);

                        } else {

                            System.out.println("Cannot divide by zero.");
                        }
                        break;

                    case '%':
                        if (num2 != 0) {

                            result = modulus(num1, num2);

                            System.out.println("Result: " + result);

                        } else {

                            System.out.println(
                                "Cannot perform modulus by zero."
                            );
                        }
                        break;

                    case '^':
                        result = power(num1, num2);
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
    }


    // Addition
    public static double add(double a, double b) {
        return a + b;
    }


    // Subtraction
    public static double subtract(double a, double b) {
        return a - b;
    }


    // Multiplication
    public static double multiply(double a, double b) {
        return a * b;
    }


    // Division
    public static double divide(double a, double b) {
        return a / b;
    }


    // Modulus
    public static double modulus(double a, double b) {
        return a % b;
    }


    // Power
    public static double power(double a, double b) {
        return Math.pow(a, b);
    }


    // Square Root
    public static double squareRoot(double number) {
        return Math.sqrt(number);
    }
}