package com.orange.mainservice.request;

import com.orange.mainservice.entity.Recipe;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@AllArgsConstructor
public final class RecipeRequest {

    private final Long recipeId;
    @NotNull
    private final Recipe.TimeType timeType;
    @NotBlank
    @Size(max = 40)
    private final String title;
    private final String img;
    @NotNull
    private final Long userId; // TODO: userId from session
    @NotEmpty
    private final Set<DirectionRequest> directions;
    @NotEmpty
    private final Set<IngredientRequest> ingredients;
    @NotEmpty
    private final Set<RecipeCategoryRequest> categories;
}
