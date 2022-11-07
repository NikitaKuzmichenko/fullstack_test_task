package com.example.demo.presentation.exception.handler;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
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

    private static final String NOT_FOUND_EXCEPTION_MSG_TEMPLATE = "%s entity with %s not found";
    private static final String ENDPOINT_HANDLER_NOT_FOUND_EXCEPTION_MSG_TEMPLATE = "Handler for %s url not found";
    private static final String CONSTRAIN_VALIDATED_EXCEPTION_MSG = "Entity constrains violated";
    private static final String REFRESH_TOKEN_EXPIRED_EXCEPTION_MSG = "Refresh token expired";
    private static final String JWT_TOKEN_EXPIRED_EXCEPTION_MSG = "JWT token expired";
    private static final String JWT_TOKEN_DECODE_EXCEPTION_MSG = "JWT token can't be decoded";
    private static final String JWT_TOKEN_SIGNATURE_EXCEPTION_MSG = "JWT token can't be verified";

    @ExceptionHandler(EntityNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<ExceptionWrapper> entityNotExistsExceptionHandler(EntityNotExistException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).
                body(new ExceptionWrapper(
                        String.format(NOT_FOUND_EXCEPTION_MSG_TEMPLATE,e.getClassName(),e.getReason()),
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
                        String.format(ENDPOINT_HANDLER_NOT_FOUND_EXCEPTION_MSG_TEMPLATE,e.getRequestURL()),
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
                        CONSTRAIN_VALIDATED_EXCEPTION_MSG,
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
                        REFRESH_TOKEN_EXPIRED_EXCEPTION_MSG,
                        HttpStatus.BAD_REQUEST.value(),
                        ZonedDateTime.now())
                );
    }

    @ExceptionHandler(JWTDecodeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ExceptionWrapper> notValidTokenExceptionHandler(Exception exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                body(new ExceptionWrapper(
                        JWT_TOKEN_DECODE_EXCEPTION_MSG,
                        HttpStatus.BAD_REQUEST.value(),
                        ZonedDateTime.now())
                );
    }

    @ExceptionHandler(TokenExpiredException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ExceptionWrapper> tokenExpiredExceptionHandler(Exception exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                body(new ExceptionWrapper(
                        JWT_TOKEN_EXPIRED_EXCEPTION_MSG,
                        HttpStatus.BAD_REQUEST.value(),
                        ZonedDateTime.now())
                );
    }

    @ExceptionHandler(SignatureVerificationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ExceptionWrapper> invalidTokenSignatureExceptionHandler(Exception exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                body(new ExceptionWrapper(
                        JWT_TOKEN_SIGNATURE_EXCEPTION_MSG,
                        HttpStatus.BAD_REQUEST.value(),
                        ZonedDateTime.now())
                );
    }

}
