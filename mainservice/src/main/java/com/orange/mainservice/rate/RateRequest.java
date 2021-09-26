package com.orange.mainservice.rate;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
final class RateRequest {

    @Min(0)
    @Max(5)
    @NotNull
    private final Integer value;
    @NotNull
    private final Long recipeId;
}
