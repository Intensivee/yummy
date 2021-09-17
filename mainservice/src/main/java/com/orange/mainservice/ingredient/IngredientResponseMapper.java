package com.orange.mainservice.ingredient;

import org.springframework.stereotype.Component;

@Component
class IngredientResponseMapper {

    IngredientResponse ingredientToResponse(Ingredient ingredient) {
        return new IngredientResponse(
                ingredient.getId(),
                ingredient.getComponent().getId(),
                ingredient.getAmount(),
                ingredient.getAmountType(),
                ingredient.getComponent().getName()
        );
    }
}
