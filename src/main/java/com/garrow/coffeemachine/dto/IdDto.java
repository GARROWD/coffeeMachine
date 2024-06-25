package com.garrow.coffeemachine.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class IdDto {

    @NotNull(message = "{validation.nullElement}")
    private UUID id;

}
