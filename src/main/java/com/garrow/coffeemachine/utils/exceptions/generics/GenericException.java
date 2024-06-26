package com.garrow.coffeemachine.utils.exceptions.generics;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GenericException extends RuntimeException {

    public GenericException(String message) {
        super(message);
    }
}