package com.chat.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String sender;
    private final String receiver;
    private final String message;
    private final MessageType type;
    private final LocalDateTime timestamp;

    public ChatMessage(
            String sender,
            String receiver,
            String message,
            MessageType type) {

        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.type = type;
        this.timestamp = LocalDateTime.now();
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getMessage() {
        return message;
    }

    public MessageType getType() {
        return type;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getFormattedTime() {

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(
                        "HH:mm:ss"
                );

        return timestamp.format(formatter);
    }

    @Override
    public String toString() {

        if (type == MessageType.SYSTEM) {
            return "[" + getFormattedTime()
                    + "] "
                    + message;
        }

        if (type == MessageType.PRIVATE) {
            return "[" + getFormattedTime()
                    + "] [Private] "
                    + sender
                    + ": "
                    + message;
        }

        return "[" + getFormattedTime()
                + "] "
                + sender
                + ": "
                + message;
    }
}