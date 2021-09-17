package com.orange.mainservice.rate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
final class RateResponse {

    private final Long id;
    private final Integer value;
    private final Long userId;
    private final Long recipeId;
}
