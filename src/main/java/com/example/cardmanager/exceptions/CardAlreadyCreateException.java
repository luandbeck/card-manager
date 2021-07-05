package com.example.cardmanager.exceptions;

import com.example.cardmanager.exceptions.enums.Errors;
import org.springframework.http.HttpStatus;

public class CardAlreadyCreateException extends BaseBusinessException {

    public CardAlreadyCreateException() {
        super(HttpStatus.PRECONDITION_FAILED, Errors.CAR003, false);
    }

}
