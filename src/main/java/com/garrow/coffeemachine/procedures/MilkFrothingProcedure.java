package com.garrow.coffeemachine.procedures;

import com.garrow.coffeemachine.models.ActionArgument;
import com.garrow.coffeemachine.models.ActionIngredient;
import com.garrow.coffeemachine.procedures.interfaces.Procedure;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class MilkFrothingProcedure implements Procedure {

    @Override
    public void execute(List<ActionIngredient> actionIngredients, List<ActionArgument> actionArguments) {
        // Какая-то реальная логика по процедуре, которая обращается к реальной кофемашине
        log.info("MilkFrothingProcedure started with actionIngredients: {} and actionArguments: {}", 1, 1);
    }

}
