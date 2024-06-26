package com.garrow.coffeemachine.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Schema(name = "BeverageDto")
public class BeverageDto {

    private UUID id;

    private String name;

    private String description;

    private List<ActionDto> actions;

    @Data
    @Schema(name = "BeverageDto.Create")
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
    @Schema(name = "BeverageDto.Update")
    public static class Update {

        @NotBlank(message = "{validation.blankElement}")
        private String name;

        @NotBlank(message = "{validation.blankElement}")
        private String description;

        // Если вы хотите обновлять действия по их ID, можно использовать список с ID
        // Однако это может требовать дополнительной логики в вашем сервисе обновления
        // private List<UUID> actionIdsToUpdate;

        // Или можно передавать полные данные ActionDto.Update для обновления каждого действия
        // Но это может создать новые записи, если ID не существует
        // @Valid
        // @NotNull(message = "{validation.nullElement}")
        // private List<ActionDto.Update> actions;

    }
}