package com.orange.mainservice.response;

import com.orange.mainservice.entity.enums.AmountType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class IngredientResponse {

    private final Long id;
    private final Double amount;
    private final AmountType amountType;
    private final String componentName;
}
