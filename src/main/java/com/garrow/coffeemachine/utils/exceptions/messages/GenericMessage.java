package com.garrow.coffeemachine.utils.exceptions.messages;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@Schema(name = "GenericMessage")
public class GenericMessage {

    private String message;

    private String details;
}
