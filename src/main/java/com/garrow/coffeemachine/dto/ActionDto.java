package com.garrow.coffeemachine.dto;

import com.garrow.coffeemachine.utils.enums.ProcedureType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ActionDto {

    private UUID id;

    private ProcedureType procedureType;

    private List<ActionIngredientDto> actionIngredients;

    @Data
    public static class Create {

        @NotNull(message = "{validation.nullElement}")
        private ProcedureType procedureType;

        @Valid
        @NotNull(message = "{validation.nullElement}")
        private IdDto beverage;

        @Valid
        @NotNull(message = "{validation.nullElement}")
        private List<ActionIngredientDto.Create> actionIngredients;

    }

    @Data
    public static class Update {

        @NotNull(message = "{validation.nullElement}")
        private ProcedureType procedureType;

        @Valid
        @NotNull(message = "{validation.nullElement}")
        private List<ActionIngredientDto.Update> actionIngredients;

    }
}