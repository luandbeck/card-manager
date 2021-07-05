package com.example.cardmanager.exceptions;

import com.example.cardmanager.exceptions.enums.Errors;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BaseBusinessException {

    public UserNotFoundException() {
        super(HttpStatus.NOT_FOUND, Errors.CAR002, false);
    }

}
