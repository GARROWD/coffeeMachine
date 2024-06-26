package com.garrow.coffeemachine.services;

import com.garrow.coffeemachine.models.Ingredient;
import com.garrow.coffeemachine.repositories.IngredientRepository;
import com.garrow.coffeemachine.services.interfaces.JpaService;
import com.garrow.coffeemachine.utils.exceptions.InsufficientQuantityException;
import com.garrow.coffeemachine.utils.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class IngredientService implements JpaService<Ingredient, UUID> {

    private final IngredientRepository ingredientRepository;

    public void increaseQuantity(UUID id, float quantityToIncrease) throws NotFoundException {
        Ingredient ingredient = findById(id);
        float currentQuantity = ingredient.getCurrentQuantity();

        ingredient.setCurrentQuantity(currentQuantity + quantityToIncrease);
        ingredientRepository.save(ingredient);

        log.info("Ingredient with ID {} has been increased by {} units", id, quantityToIncrease);
    }

    public void decreaseQuantity(UUID id, float quantityToDecrease) throws NotFoundException, InsufficientQuantityException {
        Ingredient ingredient = findById(id);
        float currentQuantity = ingredient.getCurrentQuantity();

        if (currentQuantity < quantityToDecrease) {
            throw new InsufficientQuantityException(quantityToDecrease);
        }

        ingredient.setCurrentQuantity(currentQuantity - quantityToDecrease);
        ingredientRepository.save(ingredient);

        log.info("Ingredient with ID {} has been decreased by {} units", id, quantityToDecrease);
    }

    public void checkQuantity(UUID id, float quantityToDecrease) throws InsufficientQuantityException {
        Ingredient ingredient = findById(id);
        float currentQuantity = ingredient.getCurrentQuantity();

        if (currentQuantity < quantityToDecrease) {
            throw new InsufficientQuantityException(quantityToDecrease);
        }

        ingredientRepository.save(ingredient);
    }

    public List<Ingredient> findAllWithLowQuantity() {
        return ingredientRepository.findAll().stream()
                .filter(ingredient -> ingredient.getCurrentQuantity() <= 0.1 * ingredient.getCapacity()).toList();
    }

    @Override
    public Ingredient findById(UUID id) throws NotFoundException {
        Optional<Ingredient> foundEntity = ingredientRepository.findById(id);

        return foundEntity.orElseThrow(() -> new NotFoundException(id));
    }

    @Override
    public void existsById(UUID id) throws NotFoundException {
        ingredientRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Page<Ingredient> findAll(Pageable pageable) {
        return ingredientRepository.findAll(pageable);
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
        entity.setId(id);
        ingredientRepository.save(entity);

        log.info("Ingredient with ID {} is updated", id);

        return entity;
    }

    @Override
    public void deleteById(UUID id) throws NotFoundException {
        existsById(id);
        ingredientRepository.deleteById(id);

        log.info("Ingredient with ID {} is deleted", id);
    }
}
