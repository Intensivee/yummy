package com.orange.mainservice.response;

import com.orange.mainservice.entity.Recipe;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RecipeResponse {

    private Long recipeId;
    private Recipe.TimeType timeType;
    private String title;
    private String img;
    private Double avgRate;
}
