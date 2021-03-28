package com.orange.mainservice.response;

import com.orange.mainservice.entity.Recipe;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class RecipeResponse {

    private final Long recipeId;
    private final Recipe.TimeType timeType;
    private final String title;
    private final String img;
    private final Double avgRate;
}
