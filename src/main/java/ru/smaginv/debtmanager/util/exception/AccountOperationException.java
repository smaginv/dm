package ru.smaginv.debtmanager.util.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class AccountOperationException extends RuntimeException implements AppException {

    private final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    private final LocalDateTime timestamp = LocalDateTime.now();

    public AccountOperationException(String message) {
        super(message);
    }
}
