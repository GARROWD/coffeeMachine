package com.garrow.coffeemachine.models;

import java.util.List;
import java.util.UUID;

public interface Recipe {

    UUID getId();

    List<Action> getActions();

    List<Ingredient> getIngredients();
}
