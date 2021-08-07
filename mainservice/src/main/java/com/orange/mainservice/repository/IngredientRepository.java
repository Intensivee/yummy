package com.orange.mainservice.repository;

import com.orange.mainservice.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    Set<Ingredient> findByRecipeId(Long id);
}
