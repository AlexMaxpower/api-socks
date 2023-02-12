package ru.coolspot.apisocks.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.coolspot.apisocks.exception.NotAvailableException;
import ru.coolspot.apisocks.exception.NotFoundException;

import java.time.Instant;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(NotFoundException.class)
    public ErrorResponse handleNotFoundException(final NotFoundException e) {
        return ErrorResponse.builder(e, HttpStatus.NOT_FOUND, e.getMessage())
                .property("timestamp", Instant.now())
                .build();
    }

    @ExceptionHandler(NotAvailableException.class)
    public ErrorResponse handleNotAvailableException(final NotAvailableException e) {
        return ErrorResponse.builder(e, HttpStatus.BAD_REQUEST, e.getMessage())
                .property("timestamp", Instant.now())
                .build();
    }
}