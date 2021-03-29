package com.orange.mainservice.request;

import com.orange.mainservice.entity.Recipe;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class RecipeRequest {

    private final Long recipeId;
    private final Recipe.TimeType timeType;
    private final String title;
    private final String img;
    private final Long userId; // TODO: userId from session
}
