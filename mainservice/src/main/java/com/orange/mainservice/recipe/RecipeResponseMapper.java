package com.orange.mainservice.recipe;

import com.orange.mainservice.rate.RateFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class RecipeResponseMapper {

    @Lazy
    private final RateFacade rateFacade;

    RecipeResponse recipeToResponse(Recipe recipe) {
        return new RecipeResponse(
                recipe.getId(),
                recipe.getTimeType(),
                recipe.getTitle(),
                recipe.getImgUrl(),
                rateFacade.getRecipeAverageRate(recipe.getId()),
                recipe.getUser().getUsername()
        );
    }
}
