package ru.smaginv.debtmanager.util.exception;

public class AccountActiveException extends RuntimeException {

    public AccountActiveException(String message) {
        super(message);
    }
}
