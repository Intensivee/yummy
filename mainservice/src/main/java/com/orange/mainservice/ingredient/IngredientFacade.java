package com.orange.mainservice.ingredient;

import com.orange.mainservice.recipe.Recipe;
import com.orange.mainservice.recipe.RecipeIngredientsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredientFacade {

    private final IngredientService ingredientService;

    public void createIngredients(List<RecipeIngredientsDto> ingredients, Recipe recipe) {
        ingredientService.createIngredients(ingredients, recipe);
    }

    public void replaceIngredients(List<RecipeIngredientsDto> ingredients, Recipe recipe) {
        ingredientService.replaceIngredients(ingredients, recipe);
    }
}
