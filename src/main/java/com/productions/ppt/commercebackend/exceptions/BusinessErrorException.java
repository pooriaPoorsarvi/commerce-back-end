package com.productions.ppt.commercebackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class BusinessErrorException extends RuntimeException {
    public BusinessErrorException(String message) {
        super(message);
    }
}
