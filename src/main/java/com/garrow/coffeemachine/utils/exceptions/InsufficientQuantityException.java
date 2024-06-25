package com.garrow.coffeemachine.utils.exceptions;

import com.garrow.coffeemachine.utils.exceptions.generics.GenericException;

public class InsufficientQuantityException extends GenericException {

    public InsufficientQuantityException(String message) {
        super(message);
    }

    public InsufficientQuantityException(Number number) {
        super(number.toString());
    }
}
