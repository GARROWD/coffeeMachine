package com.garrow.coffeemachine.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(name = "IdDto")
public class IdDto {

    @NotNull(message = "{validation.nullElement}")
    private UUID id;

}
