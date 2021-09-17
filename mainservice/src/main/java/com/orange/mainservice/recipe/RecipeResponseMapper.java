package com.orange.mainservice.recipe;

import com.orange.mainservice.rate.RateFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
class RecipeResponseMapper {

    private final RateFacade rateFacade;

    @Autowired
    public RecipeResponseMapper(@Lazy RateFacade rateService) {
        this.rateFacade = rateService;
    }

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
