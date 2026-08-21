import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {

    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {

            boolean playAgain = true;

            System.out.println("=================================");
            System.out.println("      NUMBER GUESSING GAME");
            System.out.println("=================================");

            while (playAgain) {

                System.out.println("\nChoose Difficulty Level");
                System.out.println("1. Easy");
                System.out.println("2. Medium");
                System.out.println("3. Hard");

                System.out.print("Enter your choice: ");

                int level;

                try {
                    level = scanner.nextInt();
                } catch (InputMismatchException e) {

                    System.out.println("Invalid input. Please enter 1, 2, or 3.");

                    scanner.nextLine();
                    continue;
                }


                int maximumNumber;
                int maximumAttempts;


                switch (level) {

                    case 1:
                        maximumNumber = 50;
                        maximumAttempts = 5;

                        System.out.println(
                                "\nEasy Level: Guess a number between 1 and 50."
                        );
                        break;


                    case 2:
                        maximumNumber = 100;
                        maximumAttempts = 3;

                        System.out.println(
                                "\nMedium Level: Guess a number between 1 and 100."
                        );
                        break;


                    case 3:
                        maximumNumber = 200;
                        maximumAttempts = 2;

                        System.out.println(
                                "\nHard Level: Guess a number between 1 and 200."
                        );
                        break;


                    default:
                        System.out.println(
                                "Invalid level. Please choose 1, 2, or 3."
                        );
                        continue;
                }


                Random random = new Random();

                int secretNumber =
                        random.nextInt(maximumNumber) + 1;

                boolean guessedCorrectly = false;
                int attemptsUsed = 0;


                for (
                        int attempt = 1;
                        attempt <= maximumAttempts;
                        attempt++
                ) {

                    System.out.println(
                            "\nAttempt "
                            + attempt
                            + " of "
                            + maximumAttempts
                    );

                    System.out.print("Enter your guess: ");

                    int guess;

                    try {

                        guess = scanner.nextInt();

                    } catch (InputMismatchException e) {

                        System.out.println(
                                "Invalid input. Please enter a whole number."
                        );

                        scanner.nextLine();

                        attempt--;

                        continue;
                    }


                    if (
                            guess < 1
                            || guess > maximumNumber
                    ) {

                        System.out.println(
                                "Please enter a number between 1 and "
                                + maximumNumber
                                + "."
                        );

                        attempt--;

                        continue;
                    }


                    attemptsUsed = attempt;


                    if (guess == secretNumber) {

                        guessedCorrectly = true;

                        System.out.println(
                                "\nCorrect! You guessed the number."
                        );

                        break;

                    } else if (guess < secretNumber) {

                        System.out.println(
                                "Too low. Try a higher number."
                        );

                    } else {

                        System.out.println(
                                "Too high. Try a lower number."
                        );
                    }
                }


                if (guessedCorrectly) {

                    int score =
                            calculateScore(
                                    maximumAttempts,
                                    attemptsUsed,
                                    level
                            );

                    System.out.println(
                            "Attempts used: " + attemptsUsed
                    );

                    System.out.println(
                            "Your score: " + score
                    );

                } else {

                    System.out.println(
                            "\nYou have used all your attempts."
                    );

                    System.out.println(
                            "The correct number was: "
                            + secretNumber
                    );

                    System.out.println(
                            "Your score: 0"
                    );
                }


                System.out.print(
                        "\nDo you want to play again? (y/n): "
                );

                char choice =
                        scanner.next()
                                .toLowerCase()
                                .charAt(0);


                if (choice != 'y') {

                    playAgain = false;
                }
            }


            System.out.println(
                    "\nThanks for playing the Number Guessing Game!"
            );
        }
    }


    public static int calculateScore(
            int maximumAttempts,
            int attemptsUsed,
            int level
    ) {

        int remainingAttempts =
                maximumAttempts - attemptsUsed;

        int baseScore = 100;

        int difficultyBonus =
                level * 50;

        int attemptBonus =
                remainingAttempts * 5;

        return baseScore
                + difficultyBonus
                + attemptBonus;
    }
}