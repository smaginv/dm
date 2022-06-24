package ru.smaginv.debtmanager.lm.util.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class NotFoundException extends RuntimeException implements AppException {

    private final HttpStatus httpStatus = HttpStatus.NOT_FOUND;

    private final LocalDateTime timestamp = LocalDateTime.now();

    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }
}
