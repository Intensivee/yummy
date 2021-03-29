package com.orange.mainservice.request;

import com.orange.mainservice.entity.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class IngredientRequest {

    private final Long ingredientId;
    private final Double amount;
    private final Ingredient.AmountType amountType;
    private final Long recipeId;
    private final Long componentId;
}
