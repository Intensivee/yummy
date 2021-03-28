package com.orange.mainservice.response;

import com.orange.mainservice.entity.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IngredientResponse {

    private Long ingredientId;
    private Double amount;
    private Ingredient.AmountType amountType;
    private String componentName;
}
