package ru.smaginv.debtmanager.util.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class AccountActiveException extends RuntimeException implements AppException {

    private final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    private final LocalDateTime timestamp = LocalDateTime.now();

    public AccountActiveException(String message) {
        super(message);
    }
}
