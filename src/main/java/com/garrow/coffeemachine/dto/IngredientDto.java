package com.garrow.coffeemachine.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(name = "IngredientDto")
public class IngredientDto {

    private UUID id;

    private String name;

    private Float currentQuantity;

    private Float capacity;

    @Data
    @Schema(name = "IngredientDto.Create")
    public static class Create {

        @NotBlank(message = "{validation.blankElement}")
        private String name;

        @NotNull(message = "{validation.nullElement}")
        @Positive(message = "{validation.notPositiveElement}")
        private Float currentQuantity;

        @NotNull(message = "{validation.nullElement}")
        @Positive(message = "{validation.notPositiveElement}")
        private Float capacity;
    }

    @Data
    @Schema(name = "IngredientDto.Update")
    public static class Update {

        @NotBlank(message = "{validation.blankElement}")
        private String name;

        @NotNull(message = "{validation.nullElement}")
        @Positive(message = "{validation.notPositiveElement}")
        private Float currentQuantity;

        @NotNull(message = "{validation.nullElement}")
        @Positive(message = "{validation.notPositiveElement}")
        private Float capacity;
    }
}
