package com.productions.ppt.commercebackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class AuthenticationError extends RuntimeException {
    public AuthenticationError(String message){
        super(message);
    }
}
