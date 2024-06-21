package com.garrow.coffeemachine.services;

import com.garrow.coffeemachine.models.Recipe;
import com.garrow.coffeemachine.repositories.RecipeRepository;
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
public class RecipeService implements JpaService<Recipe, UUID> {
    private final RecipeRepository recipeRepository;

    @Override
    public Recipe findById(UUID id) throws NotFoundException {
        Optional<Recipe> foundEntity = recipeRepository.findById(id);

        return foundEntity.orElseThrow(() -> new NotFoundException(id));
    }

    @Override
    public void existsById(UUID id) throws NotFoundException {
        recipeRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    @Override
    public Recipe create(Recipe entity) {
        recipeRepository.save(entity);

        log.info("Recipe with ID {} is created", entity.getId());

        return entity;
    }

    @Override
    public Recipe update(UUID id, Recipe entity) throws NotFoundException {
        existsById(id);
        recipeRepository.save(entity);

        log.info("Recipe with ID {} is updated", id);

        return null;
    }

    @Override
    public void deleteById(UUID id) throws NotFoundException {
        existsById(id);
        recipeRepository.deleteById(id);

        log.info("Recipe with ID {} is deleted", id);
    }
}
