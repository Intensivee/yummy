package com.orange.mainservice.response;

import com.orange.mainservice.entity.enums.TimeType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class RecipeResponse {

    private final Long recipeId;
    private final TimeType timeType;
    private final String title;
    private final String img;
    private final Double avgRate;
}
