package com.garrow.coffeemachine.utils.enums;

import com.garrow.coffeemachine.procedures.CoffeeExtractionProcedure;
import com.garrow.coffeemachine.procedures.MilkFrothingProcedure;
import com.garrow.coffeemachine.procedures.MixProcedure;
import com.garrow.coffeemachine.procedures.interfaces.Procedure;
import lombok.Getter;

@Getter
public enum ProcedureType {

    MIX(MixProcedure.class),
    COFFEE_EXTRACTION(CoffeeExtractionProcedure.class),
    MILK_FROTHING(MilkFrothingProcedure.class);

    private final Class<? extends Procedure> procedureClass;

    ProcedureType(Class<? extends Procedure> procedureClass) {
        this.procedureClass = procedureClass;
    }
}
