package com.garrow.coffeemachine.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Unit {
    GRAM("g"),
    KILOGRAM("kg"),
    LITER("l"),
    MILLILITER("ml");

    private final String uint;
}
