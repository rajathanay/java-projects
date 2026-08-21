import java.util.InputMismatchException;
import java.util.Scanner;

public class TicTacToe {

    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {

            boolean playAgain = true;


            System.out.println("=========================");
            System.out.println("TIC-TAC-TOE");
            System.out.println("=========================");


            while (playAgain) {

                char[][] board = {
                        {'1', '2', '3'},
                        {'4', '5', '6'},
                        {'7', '8', '9'}
                };


                char currentPlayer = 'X';

                boolean gameFinished = false;


                while (!gameFinished) {

                    printBoard(board);


                    System.out.println(
                            "\nPlayer "
                            + currentPlayer
                            + "'s turn."
                    );

                    System.out.print(
                            "Choose a position (1-9): "
                    );


                    int position;

                    try {

                        position = scanner.nextInt();

                    } catch (InputMismatchException e) {

                        System.out.println(
                                "Invalid input. Enter a number from 1 to 9."
                        );

                        scanner.nextLine();

                        continue;
                    }


                    if (
                            position < 1
                            || position > 9
                    ) {

                        System.out.println(
                                "Position must be between 1 and 9."
                        );

                        continue;
                    }


                    if (!placeMove(
                            board,
                            position,
                            currentPlayer
                    )) {

                        System.out.println(
                                "That position is already occupied."
                        );

                        continue;
                    }


                    if (checkWinner(
                            board,
                            currentPlayer
                    )) {

                        printBoard(board);

                        System.out.println(
                                "\nPlayer "
                                + currentPlayer
                                + " wins!"
                        );

                        gameFinished = true;

                    } else if (isBoardFull(board)) {

                        printBoard(board);

                        System.out.println(
                                "\nThe game is a draw."
                        );

                        gameFinished = true;

                    } else {

                        currentPlayer =
                                switchPlayer(
                                        currentPlayer
                                );
                    }
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
                    "\nThanks for playing Tic-Tac-Toe!"
            );
        }
    }


    // Display the board
    public static void printBoard(
            char[][] board
    ) {

        System.out.println();

        System.out.println(
                " " + board[0][0]
                + " | " + board[0][1]
                + " | " + board[0][2]
        );

        System.out.println("---+---+---");

        System.out.println(
                " " + board[1][0]
                + " | " + board[1][1]
                + " | " + board[1][2]
        );

        System.out.println("---+---+---");

        System.out.println(
                " " + board[2][0]
                + " | " + board[2][1]
                + " | " + board[2][2]
        );
    }


    // Place X or O on the board
    public static boolean placeMove(
            char[][] board,
            int position,
            char player
    ) {

        int row =
                (position - 1) / 3;

        int column =
                (position - 1) % 3;


        if (
                board[row][column] == 'X'
                || board[row][column] == 'O'
        ) {

            return false;
        }


        board[row][column] = player;

        return true;
    }


    // Check all winning combinations
    public static boolean checkWinner(
            char[][] board,
            char player
    ) {

        // Rows
        for (int row = 0; row < 3; row++) {

            if (
                    board[row][0] == player
                    && board[row][1] == player
                    && board[row][2] == player
            ) {

                return true;
            }
        }


        // Columns
        for (int column = 0; column < 3; column++) {

            if (
                    board[0][column] == player
                    && board[1][column] == player
                    && board[2][column] == player
            ) {

                return true;
            }
        }


        // Check both diagonals
        return (
                board[0][0] == player
                && board[1][1] == player
                && board[2][2] == player
        ) || (
                board[0][2] == player
                && board[1][1] == player
                && board[2][0] == player
        );
    }


    // Check if all positions are occupied
    public static boolean isBoardFull(
            char[][] board
    ) {

        for (int row = 0; row < 3; row++) {

            for (
                    int column = 0;
                    column < 3;
                    column++
            ) {

                if (
                        board[row][column] != 'X'
                        && board[row][column] != 'O'
                ) {

                    return false;
                }
            }
        }


        return true;
    }


    // Change player X to O or O to X
    public static char switchPlayer(
            char currentPlayer
    ) {

        if (currentPlayer == 'X') {

            return 'O';

        } else {

            return 'X';
        }
    }
}