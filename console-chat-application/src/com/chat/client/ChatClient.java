package com.chat.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.Socket;

import java.util.Scanner;

import com.chat.model.ChatMessage;

public class ChatClient {

    private static final String HOST =
            "localhost";

    private static final int PORT =
            5000;

    private Socket socket;

    private ObjectOutputStream output;
    private ObjectInputStream input;

    private final Scanner scanner =
            new Scanner(System.in);

    private volatile boolean running =
            true;

    public void start() {

        try {

            socket =
                    new Socket(
                            HOST,
                            PORT
                    );

            output =
                    new ObjectOutputStream(
                            socket.getOutputStream()
                    );

            output.flush();

            input =
                    new ObjectInputStream(
                            socket.getInputStream()
                    );

            System.out.print(
                    "Enter username: "
            );

            String username =
                    scanner.nextLine()
                            .trim();

            output.writeUTF(username);
            output.flush();

            boolean accepted =
                    input.readBoolean();

            if (!accepted) {

                ChatMessage message =
                        (ChatMessage)
                                input.readObject();

                System.out.println(
                        message
                );

                return;
            }

            Thread receiverThread =
                    new Thread(
                            this::receiveMessages
                    );

            receiverThread.start();

            sendMessages();

        } catch (
                IOException
                | ClassNotFoundException e
        ) {

            System.out.println(
                    "Unable to connect: "
                            + e.getMessage()
            );

        } finally {

            closeConnection();
        }
    }

    private void sendMessages() {

        while (running) {

            try {

                String message =
                        scanner.nextLine();

                output.writeUTF(message);
                output.flush();

                if (
                    message.equalsIgnoreCase(
                            "/exit"
                    )
                ) {

                    running = false;
                    break;
                }

            } catch (IOException e) {

                running = false;

                System.out.println(
                        "Connection closed."
                );
            }
        }
    }

    private void receiveMessages() {

        while (running) {

            try {

                ChatMessage message =
                        (ChatMessage)
                                input.readObject();

                System.out.println(message);

            } catch (
                    IOException
                    | ClassNotFoundException e
            ) {

                if (running) {

                    System.out.println(
                            "Disconnected from server."
                    );
                }

                running = false;
                break;
            }
        }
    }

    private void closeConnection() {

        running = false;

        try {

            if (socket != null) {
                socket.close();
            }

        } catch (IOException ignored) {
        }

        scanner.close();
    }

    public static void main(String[] args) {

        ChatClient client =
                new ChatClient();

        client.start();
    }
}