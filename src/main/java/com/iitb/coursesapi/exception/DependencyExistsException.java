package com.iitb.coursesapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DependencyExistsException extends RuntimeException {
    public DependencyExistsException(String message) {
        super(message);
    }
}
