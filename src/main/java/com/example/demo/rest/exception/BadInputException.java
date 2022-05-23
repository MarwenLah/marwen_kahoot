package com.example.demo.rest.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BadInputException extends RuntimeException {
    public BadInputException(String message) {
        super(message);
        log.error(message);
    }
}
