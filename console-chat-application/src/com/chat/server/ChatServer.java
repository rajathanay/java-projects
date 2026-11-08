package com.chat.server;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.chat.model.ChatMessage;
import com.chat.model.MessageType;

public class ChatServer {

    private static final int PORT = 5000;

    private final Map<String, ClientHandler> clients =
            new ConcurrentHashMap<>();

    private final ExecutorService threadPool =
            Executors.newFixedThreadPool(20);

    public void start() {

        System.out.println(
                "Chat server starting..."
        );

        try (
            ServerSocket serverSocket =
                    new ServerSocket(PORT)
        ) {

            System.out.println(
                    "Server running on port "
                            + PORT
            );

            while (true) {

                Socket socket =
                        serverSocket.accept();

                ClientHandler handler =
                        new ClientHandler(
                                socket,
                                this
                        );

                threadPool.execute(handler);
            }

        } catch (IOException e) {

            System.out.println(
                    "Server error: "
                            + e.getMessage()
            );

        } finally {

            threadPool.shutdown();
        }
    }

    public boolean addClient(
            String username,
            ClientHandler handler) {

        return clients.putIfAbsent(
                username,
                handler
        ) == null;
    }

    public void removeClient(
            String username) {

        if (username != null) {
            clients.remove(username);
        }
    }

    public void broadcast(
            ChatMessage message,
            String excludeUsername) {

        for (
            Map.Entry<String, ClientHandler>
                    entry : clients.entrySet()
        ) {

            if (
                !entry.getKey()
                        .equals(excludeUsername)
            ) {

                entry.getValue()
                        .sendMessage(message);
            }
        }
    }

    public boolean sendPrivateMessage(
            String receiver,
            ChatMessage message) {

        ClientHandler client =
                clients.get(receiver);

        if (client == null) {
            return false;
        }

        client.sendMessage(message);

        return true;
    }

    public String getOnlineUsers() {

        if (clients.isEmpty()) {
            return "No users online.";
        }

        return String.join(
                ", ",
                clients.keySet()
        );
    }

    public static ChatMessage systemMessage(
            String message) {

        return new ChatMessage(
                "SERVER",
                null,
                message,
                MessageType.SYSTEM
        );
    }

    public static void main(String[] args) {

        ChatServer server =
                new ChatServer();

        server.start();
    }
}