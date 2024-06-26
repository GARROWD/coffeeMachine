package com.garrow.coffeemachine.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(name = "ActionArgumentDto")
public class ActionArgumentDto {

    private UUID id;

    private String name;

    private Float value;

    @Data
    @Schema(name = "ActionArgumentDto.Create")
    public static class Create {

        @NotBlank(message = "{validation.blankElement}")
        private String name;

        @NotNull(message = "{validation.nullElement}")
        private Float value;

    }

    @Data
    @Schema(name = "ActionArgumentDto.Update")
    public static class Update {

        @NotBlank(message = "{validation.blankElement}")
        private String name;

        @NotNull(message = "{validation.nullElement}")
        private Float value;

    }
}