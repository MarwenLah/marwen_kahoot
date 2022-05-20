package com.example.demo.rest.exception;

public class BadInputException extends RuntimeException {
    public BadInputException(String message) {
        super(message);
    }
}
