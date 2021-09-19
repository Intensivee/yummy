package com.orange.mainservice.recipe;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@AllArgsConstructor
public class RecipeCreateRequest {

    private final RecipeRequest recipeRequest;
    private final List<RecipeIngredientsDto> ingredients;
    @NotEmpty
    private final List<String> directions;
}
