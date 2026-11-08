package com.chat.server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.Socket;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.chat.model.ChatMessage;
import com.chat.model.MessageType;

public class ClientHandler implements Runnable {

    private static final Pattern PRIVATE_MESSAGE =
            Pattern.compile(
                    "^/msg\\s+(\\S+)\\s+(.+)$"
            );

    private final Socket socket;
    private final ChatServer server;

    private ObjectOutputStream output;
    private ObjectInputStream input;

    private String username;

    public ClientHandler(
            Socket socket,
            ChatServer server) {

        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {

        try {

            output =
                    new ObjectOutputStream(
                            socket.getOutputStream()
                    );

            output.flush();

            input =
                    new ObjectInputStream(
                            socket.getInputStream()
                    );

            username =
                    input.readUTF()
                            .trim();

            if (
                username.isBlank()
                || !username.matches(
                        "[A-Za-z0-9_]{3,20}"
                )
            ) {

                output.writeBoolean(false);

                output.writeObject(
                        ChatServer.systemMessage(
                                "Invalid username."
                        )
                );

                output.flush();

                return;
            }

            if (
                !server.addClient(
                        username,
                        this
                )
            ) {

                output.writeBoolean(false);

                output.writeObject(
                        ChatServer.systemMessage(
                                "Username already in use."
                        )
                );

                output.flush();

                return;
            }

            output.writeBoolean(true);
            output.flush();

            sendMessage(
                    ChatServer.systemMessage(
                            "Connected successfully."
                    )
            );

            sendMessage(
                    ChatServer.systemMessage(
                            "Commands: /users, /msg username message, /exit"
                    )
            );

            server.broadcast(
                    ChatServer.systemMessage(
                            username
                                    + " joined the chat."
                    ),
                    username
            );

            System.out.println(
                    username
                            + " connected."
            );

            receiveMessages();

        } catch (EOFException e) {

            System.out.println(
                    username
                            + " disconnected."
            );

        } catch (IOException e) {

            System.out.println(
                    "Client connection error: "
                            + e.getMessage()
            );

        } finally {

            disconnect();
        }
    }

    private void receiveMessages()
            throws IOException {

        while (true) {

            String text =
                    input.readUTF()
                            .trim();

            if (text.isBlank()) {
                continue;
            }

            if (
                text.equalsIgnoreCase(
                        "/exit"
                )
            ) {
                break;
            }

            if (
                text.equalsIgnoreCase(
                        "/users"
                )
            ) {

                sendMessage(
                        ChatServer.systemMessage(
                                "Online users: "
                                        + server
                                            .getOnlineUsers()
                        )
                );

                continue;
            }

            if (
                text.startsWith("/msg")
            ) {

                handlePrivateMessage(text);

                continue;
            }

            if (text.startsWith("/")) {

                sendMessage(
                        ChatServer.systemMessage(
                                "Unknown command."
                        )
                );

                continue;
            }

            ChatMessage message =
                    new ChatMessage(
                            username,
                            null,
                            text,
                            MessageType.CHAT
                    );

            server.broadcast(
                    message,
                    null
            );
        }
    }

    private void handlePrivateMessage(
            String text) {

        Matcher matcher =
                PRIVATE_MESSAGE.matcher(text);

        if (!matcher.matches()) {

            sendMessage(
                    ChatServer.systemMessage(
                            "Use: /msg username message"
                    )
            );

            return;
        }

        String receiver =
                matcher.group(1);

        String messageText =
                matcher.group(2);

        if (
            receiver.equalsIgnoreCase(
                    username
            )
        ) {

            sendMessage(
                    ChatServer.systemMessage(
                            "You cannot send a private message to yourself."
                    )
            );

            return;
        }

        ChatMessage message =
                new ChatMessage(
                        username,
                        receiver,
                        messageText,
                        MessageType.PRIVATE
                );

        boolean sent =
                server.sendPrivateMessage(
                        receiver,
                        message
                );

        if (!sent) {

            sendMessage(
                    ChatServer.systemMessage(
                            receiver
                                    + " is not online."
                    )
            );

            return;
        }

        ChatMessage confirmation =
                new ChatMessage(
                        username,
                        receiver,
                        messageText,
                        MessageType.PRIVATE
                );

        sendMessage(confirmation);
    }

    public synchronized void sendMessage(
            ChatMessage message) {

        try {

            output.writeObject(message);
            output.flush();

        } catch (IOException e) {

            System.out.println(
                    "Unable to send message to "
                            + username
            );
        }
    }

    private void disconnect() {

        if (username != null) {

            server.removeClient(
                    username
            );

            server.broadcast(
                    ChatServer.systemMessage(
                            username
                                    + " left the chat."
                    ),
                    username
            );

            System.out.println(
                    username
                            + " disconnected."
            );
        }

        try {
            socket.close();
        } catch (IOException ignored) {
        }
    }
}