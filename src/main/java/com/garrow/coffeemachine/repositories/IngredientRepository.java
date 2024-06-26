package com.garrow.coffeemachine.repositories;

import com.garrow.coffeemachine.models.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IngredientRepository extends JpaRepository<Ingredient, UUID> {
}
