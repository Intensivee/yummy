package com.orange.mainservice.ingredient;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    Set<Ingredient> findByRecipeId(Long id);
}
