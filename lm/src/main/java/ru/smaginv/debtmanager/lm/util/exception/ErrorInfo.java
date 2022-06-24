package ru.smaginv.debtmanager.lm.util.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ErrorInfo {

    private LocalDateTime timestamp;

    private HttpStatus httpStatus;

    private String message;

    private List<String> details;

    private String path;
}
