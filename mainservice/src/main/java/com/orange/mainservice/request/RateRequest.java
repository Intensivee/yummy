package com.orange.mainservice.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class RateRequest {

    private final Long rateId;
    private final Integer value;
    private final Long userId; // TODO: userId from session
    private final Long recipeId;
}
