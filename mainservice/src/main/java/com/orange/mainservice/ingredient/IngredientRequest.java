package com.orange.mainservice.ingredient;

import com.orange.mainservice.entity.enums.AmountType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@AllArgsConstructor
final class IngredientRequest {

    private final Long id;
    @NotNull
    @Positive
    private final Double amount;
    @NotNull
    private final AmountType amountType;
    @NotNull
    private final Long recipeId;
    @NotNull
    private final Long componentId;
}
