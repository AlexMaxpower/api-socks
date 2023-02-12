package ru.coolspot.apisocks.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NotAvailableException extends IllegalArgumentException {
    public NotAvailableException(String message) {
        super(message);
        log.error(message);
    }
}