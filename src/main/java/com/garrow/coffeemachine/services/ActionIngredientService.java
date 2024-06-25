package com.garrow.coffeemachine.services;

import com.garrow.coffeemachine.models.ActionIngredient;
import com.garrow.coffeemachine.repositories.ActionIngredientRepository;
import com.garrow.coffeemachine.services.interfaces.JpaService;
import com.garrow.coffeemachine.utils.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ActionIngredientService implements JpaService<ActionIngredient, UUID> {

    private final ActionIngredientRepository actionIngredientRepository;

    @Override
    public ActionIngredient findById(UUID id) throws NotFoundException {
        Optional<ActionIngredient> foundEntity = actionIngredientRepository.findById(id);

        return foundEntity.orElseThrow(() -> new NotFoundException(id));
    }

    @Override
    public void existsById(UUID id) throws NotFoundException {
        actionIngredientRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    @Override
    public ActionIngredient create(ActionIngredient entity) {
        actionIngredientRepository.save(entity);

        log.info("ActionIngredient with ID {} is created", entity.getId());

        return entity;
    }

    @Override
    public ActionIngredient update(UUID id, ActionIngredient entity) throws NotFoundException {
        existsById(id);
        entity.setId(id);
        actionIngredientRepository.save(entity);

        log.info("ActionIngredient with ID {} is updated", id);

        return entity;
    }

    @Override
    public void deleteById(UUID id) throws NotFoundException {
        existsById(id);
        actionIngredientRepository.deleteById(id);

        log.info("ActionIngredient with ID {} is deleted", id);
    }
}
