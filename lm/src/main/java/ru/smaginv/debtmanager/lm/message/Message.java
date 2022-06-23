package ru.smaginv.debtmanager.lm.message;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class Message {

    private LocalDateTime timestamp;

    private String username;

    private String requestURI;

    private String httpMethod;

    private Object requestBody;

    private Object responseBody;
}
