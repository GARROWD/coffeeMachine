package com.garrow.coffeemachine.dto;

import com.garrow.coffeemachine.utils.enums.BeverageOrderStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class BeverageOrderDto {

    private UUID id;

    private BeverageOrderStatus status;

    private BeverageDto beverage;

    private LocalDateTime created;

    @Data
    public static class Create {

        @Valid
        @NotNull(message = "{validation.nullElement}")
        private IdDto beverage;

    }
}