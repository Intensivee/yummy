package com.orange.mainservice.response;

import com.orange.mainservice.entity.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class IngredientResponse {

    private final Long ingredientId;
    private final Double amount;
    private final Ingredient.AmountType amountType;
    private final String componentName;
}
