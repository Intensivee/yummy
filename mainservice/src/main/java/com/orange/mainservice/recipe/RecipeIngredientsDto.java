package com.orange.mainservice.recipe;

import com.orange.mainservice.ingredient.AmountType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public class RecipeIngredientsDto {

    @NotBlank
    @Size(max = 40)
    private final String componentName;
    @NotNull
    @Positive
    private final Double amount;
    @NonNull
    private final AmountType amountType;
}
