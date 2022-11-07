package com.example.demo.presentation.exception.handler;

import com.example.demo.presentation.exception.RefreshTokenExpiredException;
import com.example.demo.presentation.exception.wrapper.ExceptionWrapper;
import com.example.demo.service.exception.EntityNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;
import java.time.ZonedDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String NOT_FOUND_ERROR_MSG_TEMPLATE = "%s entity with id = %d not found";
    private static final String ENDPOINT_HANDLER_NOT_FOUND_ERROR_MSG_TEMPLATE = "Handler for %s url not found";
    private static final String CONSTRAIN_VALIDATED_ERROR_MSG = "Entity constrains violated";
    private static final String REFRESH_TOKEN_EXPIRED_ERROR_MSG = "Refresh token expired";

    @ExceptionHandler(EntityNotExistException.class)
    @ResponseBody
    public ResponseEntity<ExceptionWrapper> entityNotExistsExceptionHandler(EntityNotExistException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).
                body(new ExceptionWrapper(
                        String.format(NOT_FOUND_ERROR_MSG_TEMPLATE,e.getClassName(),e.getEntityId()),
                                HttpStatus.NOT_FOUND.value(),
                                ZonedDateTime.now())
                        );
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<ExceptionWrapper> handlerNotFoundExceptionHandler(NoHandlerFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).
                body(new ExceptionWrapper(
                        String.format(ENDPOINT_HANDLER_NOT_FOUND_ERROR_MSG_TEMPLATE,e.getRequestURL()),
                        HttpStatus.NOT_FOUND.value(),
                        ZonedDateTime.now())
                );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ExceptionWrapper> notValidEntityExceptionHandler(ConstraintViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                body(new ExceptionWrapper(
                        CONSTRAIN_VALIDATED_ERROR_MSG,
                        HttpStatus.BAD_REQUEST.value(),
                        ZonedDateTime.now())
                );
    }

    @ExceptionHandler(RefreshTokenExpiredException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ExceptionWrapper> notValidEntityExceptionHandler(RefreshTokenExpiredException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                body(new ExceptionWrapper(
                        REFRESH_TOKEN_EXPIRED_ERROR_MSG,
                        HttpStatus.BAD_REQUEST.value(),
                        ZonedDateTime.now())
                );
    }

}
