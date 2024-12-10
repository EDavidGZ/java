package com.edgz.authudemy.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidTokenError extends ResponseStatusException {
    public InvalidTokenError() {
        super(HttpStatus.BAD_REQUEST, "Invalid Token Error Exception");
    }
}
