package com.garrow.coffeemachine.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class ActionArgumentDto {

    private UUID id;

    private String name;

    private Float value;

    @Data
    public static class Create {

        @NotBlank(message = "{validation.blankElement}")
        private String name;

        @NotNull(message = "{validation.nullElement}")
        private Float value;

    }

    @Data
    public static class Update {

        @NotBlank(message = "{validation.blankElement}")
        private String name;

        @NotNull(message = "{validation.nullElement}")
        private Float value;

    }
}