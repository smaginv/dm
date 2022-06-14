package ru.smaginv.debtmanager.dm.util.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static ru.smaginv.debtmanager.dm.util.AppUtil.VALIDATION_FAILED;

@RestControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {
            NotFoundException.class, EntityStatusException.class, AccountOperationException.class
    })
    public ResponseEntity<ErrorInfo> handleAppException(AppException ex, WebRequest request) {
        ErrorInfo errorInfo = createErrorInfo(ex, request);
        return ResponseEntity.status(ex.getHttpStatus()).body(errorInfo);
    }

    @ExceptionHandler(value = {
            IllegalArgumentException.class
    })
    public ResponseEntity<ErrorInfo> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorInfo errorInfo = createErrorInfo(status, ex, request);
        return ResponseEntity.status(status).body(errorInfo);
    }

    @ExceptionHandler(value = {
            ValidationException.class
    })
    public ResponseEntity<ErrorInfo> handleValidationException(ValidationException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorInfo errorInfo = createErrorInfo(status, ex, request);
        return ResponseEntity.status(status).body(errorInfo);
    }

    @ExceptionHandler(value = {
            ConstraintViolationException.class
    })
    public ResponseEntity<ErrorInfo> handleConstraintViolationException(ConstraintViolationException ex,
                                                                        WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorInfo errorInfo = new ErrorInfo(LocalDateTime.now(), status, VALIDATION_FAILED,
                getDetails(ex), getPath(request));
        return ResponseEntity.status(status).body(errorInfo);
    }

    @ExceptionHandler(value = {
            DataIntegrityViolationException.class
    })
    public ResponseEntity<ErrorInfo> handleDataIntegrityViolationException(DataIntegrityViolationException ex,
                                                                           WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        ErrorInfo errorInfo = createErrorInfo(status, Objects.requireNonNull(ex.getRootCause()).getMessage(), request);
        return ResponseEntity.status(status).body(errorInfo);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        ErrorInfo errorInfo = new ErrorInfo(LocalDateTime.now(), status, VALIDATION_FAILED,
                getDetails(ex), getPath(request));
        return ResponseEntity.status(status).body(errorInfo);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                         HttpHeaders headers,
                                                                         HttpStatus status,
                                                                         WebRequest request) {
        ErrorInfo errorInfo = createErrorInfo(status, ex, request);
        return ResponseEntity.status(status).body(errorInfo);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
                                                                     HttpHeaders headers,
                                                                     HttpStatus status,
                                                                     WebRequest request) {
        ErrorInfo errorInfo = createErrorInfo(status, ex, request);
        return ResponseEntity.status(status).body(errorInfo);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        ErrorInfo errorInfo = createErrorInfo(status, ex, request);
        return ResponseEntity.status(status).body(errorInfo);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        ErrorInfo errorInfo = createErrorInfo(status, ex, request);
        return ResponseEntity.status(status).body(errorInfo);
    }

    private ErrorInfo createErrorInfo(AppException ex, WebRequest request) {
        return new ErrorInfo(ex.getTimestamp(), ex.getHttpStatus(), ex.getMessage(),
                Collections.emptyList(), getPath(request));
    }

    private ErrorInfo createErrorInfo(HttpStatus status, Exception ex, WebRequest request) {
        return new ErrorInfo(LocalDateTime.now(), status, ex.getMessage(),
                Collections.emptyList(), getPath(request));
    }

    private ErrorInfo createErrorInfo(HttpStatus status, String message, WebRequest request) {
        return new ErrorInfo(LocalDateTime.now(), status, message, Collections.emptyList(), getPath(request));
    }

    private String getPath(WebRequest request) {
        StringBuilder sb = new StringBuilder(request.getDescription(false));
        return sb.substring(sb.indexOf("/"));
    }

    private List<String> getDetails(BindException ex) {
        return ex.getBindingResult().getFieldErrors().stream()
                .map(error -> String.format("'%s' %s", error.getField(), error.getDefaultMessage()))
                .toList();
    }

    private List<String> getDetails(ConstraintViolationException ex) {
        return ex.getConstraintViolations().stream()
                .map(violation -> String.format("'%s' %s", violation.getPropertyPath(), violation.getMessage()))
                .toList();
    }
}
