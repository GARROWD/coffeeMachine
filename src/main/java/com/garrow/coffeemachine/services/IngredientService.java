package com.garrow.coffeemachine.services;

import com.garrow.coffeemachine.models.Ingredient;
import com.garrow.coffeemachine.repositories.IngredientRepository;
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
public class IngredientService implements JpaService<Ingredient, UUID> {
    private final IngredientRepository ingredientRepository;

    @Override
    public Ingredient findById(UUID id) throws NotFoundException {
        Optional<Ingredient> foundEntity = ingredientRepository.findById(id);

        return foundEntity.orElseThrow(() -> new NotFoundException(id));
    }

    @Override
    public void existsById(UUID id) throws NotFoundException {
        ingredientRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    @Override
    public Ingredient create(Ingredient entity) {
        ingredientRepository.save(entity);

        log.info("Ingredient with ID {} is created", entity.getId());

        return entity;
    }

    @Override
    public Ingredient update(UUID id, Ingredient entity) throws NotFoundException {
        existsById(id);
        ingredientRepository.save(entity);

        log.info("Ingredient with ID {} is updated", id);

        return null;
    }

    @Override
    public void deleteById(UUID id) throws NotFoundException {
        existsById(id);
        ingredientRepository.deleteById(id);

        log.info("Ingredient with ID {} is deleted", id);
    }
}
