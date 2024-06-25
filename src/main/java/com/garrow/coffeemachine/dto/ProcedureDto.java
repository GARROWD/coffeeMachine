package com.garrow.coffeemachine.dto;

import com.garrow.coffeemachine.utils.enums.ProcedureType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ProcedureDto {

    private String name;

    private String description;

    private ProcedureType procedureType;
}