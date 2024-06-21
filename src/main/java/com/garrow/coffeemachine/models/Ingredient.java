package com.garrow.coffeemachine.models;

import com.garrow.coffeemachine.utils.enums.Unit;

import java.util.UUID;

public interface Ingredient {

    UUID getId();

    String getName();

    Float getAmount();

    Unit getUnit();
}
