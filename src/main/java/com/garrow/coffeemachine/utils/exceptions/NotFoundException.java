package com.garrow.coffeemachine.utils.exceptions;

import com.garrow.coffeemachine.utils.exceptions.generics.GenericException;

public class NotFoundException
        extends GenericException {
    public NotFoundException(String message){
        super(message);
    }
}