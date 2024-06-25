package com.garrow.coffeemachine.services;

import com.garrow.coffeemachine.models.Action;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class BeverageActionService {

    // Я не знаю как лучше выходить из таких ситуаций, не переделывая всю архитектуру.
    // BeverageRepository использовать в ActionService (или наоборот) не хочется.
    // Создавать BeverageActionService не хочется.
    // Держать логику совмещающую Action и Beverage в Controller тоже не хочется.

    private final ActionService actionService;
    private final BeverageService beverageService;
    private final IngredientService ingredientService;

    @Transactional
    public Action create(Action entity) {
        entity.setBeverage(beverageService.findById(entity.getBeverage().getId()));
        entity.getActionIngredients().forEach(actionIngredient ->
                actionIngredient.setIngredient(ingredientService.findById(actionIngredient.getIngredient().getId())));
        actionService.create(entity);

        return entity;
    }

    public Page<Action> findAllByBeverage(UUID id, Pageable pageable) {
        return actionService.findAllByBeverage(id, pageable);
    }
}
