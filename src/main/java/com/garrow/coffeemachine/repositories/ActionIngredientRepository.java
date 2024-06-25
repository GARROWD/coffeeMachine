package com.garrow.coffeemachine.repositories;

import com.garrow.coffeemachine.models.ActionIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ActionIngredientRepository extends JpaRepository<ActionIngredient, UUID> {
}
