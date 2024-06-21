package com.garrow.coffeemachine.repositories;

import com.garrow.coffeemachine.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RecipeRepository extends JpaRepository<Recipe, UUID> {
}
