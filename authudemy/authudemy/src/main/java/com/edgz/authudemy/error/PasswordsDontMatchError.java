package com.edgz.authudemy.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PasswordsDontMatchError extends ResponseStatusException {
    public PasswordsDontMatchError() {
        super(HttpStatus.BAD_REQUEST, "password do not match");
    }
}
