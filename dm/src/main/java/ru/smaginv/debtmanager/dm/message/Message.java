package ru.smaginv.debtmanager.dm.message;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class Message {

    private final LocalDateTime timestamp;

    private final String username;

    private final String requestURI;

    private final String httpMethod;

    private final Object requestBody;

    private final Object responseBody;

    public Message(String username, String requestURI, String httpMethod, Object requestBody, Object responseBody) {
        this.timestamp = LocalDateTime.now();
        this.username = username;
        this.requestURI = requestURI;
        this.httpMethod = httpMethod;
        this.requestBody = requestBody;
        this.responseBody = responseBody;
    }
}
