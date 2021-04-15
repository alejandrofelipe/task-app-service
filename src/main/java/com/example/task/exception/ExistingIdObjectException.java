package com.example.task.exception;

public class ExistingIdObjectException extends RuntimeException {
    public ExistingIdObjectException(String message) {
        super(message);
    }
}
