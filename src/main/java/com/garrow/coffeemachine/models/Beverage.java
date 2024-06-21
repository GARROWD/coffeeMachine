package com.garrow.coffeemachine.models;

import java.util.UUID;

public interface Beverage {

    UUID getId();

    String getName();

    String getDescription();

    Recipe getRecipe();
}
