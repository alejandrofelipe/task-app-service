package com.example.task.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionResponse {
    private Date time = new Date();
    private Integer status;
    private String error;
    private String message;
    private Map<String, String> errors;

    public ExceptionResponse(Exception ex, HttpStatus hs) {
        this.status = hs.value();
        this.error = hs.name();
        this.message = ex.getLocalizedMessage();
    }

    public ExceptionResponse(List<ObjectError> errorList, HttpStatus hs) {
        this.status = hs.value();
        this.error = hs.name();
        this.errors = new HashMap<>();
        errorList.forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            this.errors.put(fieldName, errorMessage);
        });
    }
}
