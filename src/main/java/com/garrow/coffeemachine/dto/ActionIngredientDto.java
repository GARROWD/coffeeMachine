package com.garrow.coffeemachine.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(name = "ActionIngredientDto")
public class ActionIngredientDto {

    private UUID id;

    private Float quantity;

    private IngredientDto ingredient;

    @Data
    @Schema(name = "ActionIngredientDto.Create")
    public static class Create {

        @NotNull(message = "{validation.nullElement}")
        @Positive(message = "{validation.notPositiveElement}")
        private Float quantity;

        @Valid
        @NotNull(message = "{validation.nullElement}")
        private IdDto ingredient;

    }

    @Data
    @Schema(name = "ActionIngredientDto.Update")
    public static class Update {

        @NotNull(message = "{validation.nullElement}")
        @Positive(message = "{validation.notPositiveElement}")
        private Float quantity;

        @Valid
        @NotNull(message = "{validation.nullElement}")
        private IdDto ingredient;

    }
}
