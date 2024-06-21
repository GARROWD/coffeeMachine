package com.garrow.coffeemachine.services.interfaces;

import com.garrow.coffeemachine.models.Action;

import java.util.List;

public interface ActionExecutorService {

    void perform(Action action);

    void perform(List<Action> actions);

}
