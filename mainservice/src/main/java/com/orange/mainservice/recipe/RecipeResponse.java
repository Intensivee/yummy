package com.orange.mainservice.recipe;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
final class RecipeResponse {

    private final Long id;
    private final TimeType timeType;
    private final String title;
    private final String img;
    private final Double avgRate;
    private final String username;
}
