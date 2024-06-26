package com.garrow.coffeemachine.dto;

import com.garrow.coffeemachine.utils.enums.BeverageOrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Schema(name = "BeverageOrderDto")
public class BeverageOrderDto {

    private UUID id;

    private BeverageOrderStatus status;

    private BeverageDto beverage;

    private LocalDateTime created;

    @Data
    @Schema(name = "BeverageOrderDto.Create")
    public static class Create {

        @Valid
        @NotNull(message = "{validation.nullElement}")
        private IdDto beverage;

    }
}