package ru.smaginv.debtmanager.util.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public interface AppException {

    HttpStatus getHttpStatus();

    LocalDateTime getTimestamp();

    String getMessage();
}
