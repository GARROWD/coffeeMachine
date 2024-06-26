package com.garrow.coffeemachine.dto;

import com.garrow.coffeemachine.utils.enums.ProcedureType;
import lombok.Data;

@Data
public class ProcedureDto {

    private String name;

    private String description;

    private ProcedureType procedureType;
}