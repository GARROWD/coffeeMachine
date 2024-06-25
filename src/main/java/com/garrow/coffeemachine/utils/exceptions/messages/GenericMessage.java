package com.garrow.coffeemachine.utils.exceptions.messages;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class GenericMessage {

    private String message;

    private String details;

}
