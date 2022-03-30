package com.orange.mainservice.ingredient;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
final class IngredientResponse {

    private final Long id;
    private final Long componentId;
    private final Double amount;
    private final AmountType amountType;
    private final String componentName;
}
