package com.garrow.coffeemachine.dto;

import com.garrow.coffeemachine.utils.enums.ProcedureType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Schema(name = "ActionDto")
public class ActionDto {

    private UUID id;

    private ProcedureType procedureType;

    private Integer orderIndex;

    private List<ActionIngredientDto> actionIngredients;

    private List<ActionArgumentDto> actionArguments;


    @Data
    @Schema(name = "ActionDto.Create")
    public static class Create {

        @NotNull(message = "{validation.nullElement}")
        private ProcedureType procedureType;

        @NotNull(message = "{validation.nullElement}")
        private Integer orderIndex;

        @Valid
        @NotNull(message = "{validation.nullElement}")
        private IdDto beverage;

        @Valid
        @NotNull(message = "{validation.nullElement}")
        private List<ActionIngredientDto.Create> actionIngredients;

        @Valid
        @NotNull(message = "{validation.nullElement}")
        private List<ActionArgumentDto.Create> actionArguments;

    }

    @Data
    @Schema(name = "ActionDto.Update")
    public static class Update {

        @NotNull(message = "{validation.nullElement}")
        private ProcedureType procedureType;

        @NotNull(message = "{validation.nullElement}")
        private Integer orderIndex;

        // TODO Это же не работает так, о чем я думал... Нужен id, но с ним может случиться следующая ситуация:
        // Сущности с таким id нет и просто создастся новый, что очень непредсказуемое поведение
        /*@Valid
        @NotNull(message = "{validation.nullElement}")
        private List<ActionIngredientDto.Update> actionIngredients;

        @Valid
        @NotNull(message = "{validation.nullElement}")
        private List<ActionArgumentDto.Update> actionArguments;*/

    }
}