package com.example.task.exception;

public class MissingIdObjectException extends RuntimeException {
    public MissingIdObjectException(String message) {
        super(message);
    }
}
