package com.garrow.coffeemachine.procedures;

import com.garrow.coffeemachine.models.ActionArgument;
import com.garrow.coffeemachine.models.ActionIngredient;
import com.garrow.coffeemachine.procedures.interfaces.Procedure;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class MixProcedure implements Procedure {

    @Override
    public void execute(List<ActionIngredient> actionIngredients, List<ActionArgument> actionArguments) {
        log.warn("AAAAAAAAAAA");
    }

}