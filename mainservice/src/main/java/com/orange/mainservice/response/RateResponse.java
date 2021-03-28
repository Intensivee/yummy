package com.orange.mainservice.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class RateResponse {

    private final Long rateId;
    private final Integer value;
    private final Long userId;
    private final Long recipeId;
}
