package com.garrow.coffeemachine.procedures.interfaces;

import com.garrow.coffeemachine.models.ActionArgument;
import com.garrow.coffeemachine.models.ActionIngredient;

import java.util.List;

/**
 * The Procedure interface defines the contract for an action that can be performed by a coffee machine.
 * Each procedure can utilize the provided ingredients and arguments to execute the action.
 */
public interface Procedure {

    /**
     * Executes the procedure using the provided ingredients and arguments.
     *
     * @param actionIngredients the list of ingredients to be used by the procedure
     * @param actionArguments   the list of arguments to be used by the procedure
     */
    void execute(List<ActionIngredient> actionIngredients, List<ActionArgument> actionArguments);
}
