package com.example.task.exception;

import lombok.Data;
import lombok.Getter;
import org.springframework.validation.ObjectError;

import java.util.List;

public class ValidationErrorException extends RuntimeException {
    @Getter
    private final List<ObjectError> allErrors;

    public ValidationErrorException(List<ObjectError> allErrors) {
        this.allErrors = allErrors;
    }
}
