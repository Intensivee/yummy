package com.orange.mainservice.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public final class RateRequest {

    private final Long id;
    @Min(0)
    @Max(5)
    @NotNull
    private final Integer value;
    @NotNull
    private final Long userId; // TODO: userId from session
    @NotNull
    private final Long recipeId;
}
