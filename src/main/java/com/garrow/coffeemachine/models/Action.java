package com.garrow.coffeemachine.models;

import java.util.UUID;

public interface Action {

    UUID getId();

    void execute();
}
