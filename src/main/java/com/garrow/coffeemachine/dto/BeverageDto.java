package com.garrow.coffeemachine.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class BeverageDto {

    private UUID id;

    private String name;

    private String description;

    private List<ActionDto> actions;

    // Сюда прям напрашивается private List<ActionIngredientDto> actionsIngredients;, но это же дублирование информации

    @Data
    public static class Create {

        @NotBlank(message = "{validation.blankElement}")
        private String name;

        @NotBlank(message = "{validation.blankElement}")
        private String description;

        @Valid
        @NotNull(message = "{validation.nullElement}")
        private List<ActionDto.Create> actions;

    }

    @Data
    public static class Update {

        @NotBlank(message = "{validation.blankElement}")
        private String name;

        @NotBlank(message = "{validation.blankElement}")
        private String description;

        @Valid
        @NotNull(message = "{validation.nullElement}")
        private List<ActionDto.Update> actions;

    }
}