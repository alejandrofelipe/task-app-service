package com.example.task.handler;

import com.example.task.exception.ExistingIdObjectException;
import com.example.task.exception.MissingIdObjectException;
import com.example.task.exception.ObjectNotFoundException;
import com.example.task.exception.ValidationErrorException;
import com.example.task.util.ExceptionResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ValidationErrorException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse genericError(ValidationErrorException ex) {
        return new ExceptionResponse(ex.getAllErrors(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            DataIntegrityViolationException.class,
            ExistingIdObjectException.class,
            MissingIdObjectException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse errorBadRequest(Exception ex) {
        return new ExceptionResponse(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            ObjectNotFoundException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse errorNotFound(Exception ex) {
        return new ExceptionResponse(ex, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler({
            UsernameNotFoundException.class,
            BadCredentialsException.class,
            ExpiredJwtException.class
    })
    public ExceptionResponse errorUnauthorized(Exception ex) {
        return new ExceptionResponse(ex, HttpStatus.UNAUTHORIZED);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(ex.getBindingResult().getAllErrors(), status);
        response.setMessage("Invalid fields.");
        return ResponseEntity.status(status).body(response);
    }
}
