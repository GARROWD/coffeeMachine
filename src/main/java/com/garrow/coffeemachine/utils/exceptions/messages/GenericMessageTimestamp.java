package com.garrow.coffeemachine.utils.exceptions.messages;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(name = "GenericMessageTimestamp")
public class GenericMessageTimestamp {

    private String message;

    private LocalDateTime timeStamp;

    private String details;

    public GenericMessageTimestamp(String message, String details) {
        this.message = message;
        this.details = details;
        timeStamp = LocalDateTime.now();
    }

    public GenericMessageTimestamp(String message) {
        this.message = message;
        this.details = null;
        timeStamp = LocalDateTime.now();
    }
}
