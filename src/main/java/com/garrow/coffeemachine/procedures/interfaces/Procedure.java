package com.garrow.coffeemachine.procedures.interfaces;

import com.garrow.coffeemachine.models.ActionArgument;
import com.garrow.coffeemachine.models.ActionIngredient;

import java.util.List;

public interface Procedure {

    void execute(List<ActionIngredient> actionIngredients, List<ActionArgument> actionArguments);

}
