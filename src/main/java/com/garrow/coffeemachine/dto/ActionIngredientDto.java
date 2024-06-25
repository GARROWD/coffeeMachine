package com.garrow.coffeemachine.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.UUID;

@Data
public class ActionIngredientDto {

    private UUID id;

    private Float quantity;

    private IngredientDto ingredient;

    @Data
    public static class Create {

        @NotNull(message = "{validation.nullElement}")
        @Positive(message = "{validation.notPositiveElement}")
        private Float quantity;

        @Valid
        @NotNull(message = "{validation.nullElement}")
        private IdDto ingredient;

    }

    @Data
    public static class Update {

        @NotNull(message = "{validation.nullElement}")
        @Positive(message = "{validation.notPositiveElement}")
        private Float quantity;

        @Valid
        @NotNull(message = "{validation.nullElement}")
        private IdDto ingredient;

    }
}
