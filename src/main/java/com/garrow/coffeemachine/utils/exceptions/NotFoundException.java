package com.garrow.coffeemachine.utils.exceptions;

import com.garrow.coffeemachine.utils.exceptions.generics.GenericException;

import java.util.UUID;

public class NotFoundException extends GenericException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(UUID id) {
        super(id.toString());
    }
}