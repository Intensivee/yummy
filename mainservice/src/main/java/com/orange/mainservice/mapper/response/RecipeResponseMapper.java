package com.orange.mainservice.mapper.response;

import com.orange.mainservice.entity.Recipe;
import com.orange.mainservice.rate.RateFacade;
import com.orange.mainservice.response.RecipeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class RecipeResponseMapper {

    private final RateFacade rateFacade;

    @Autowired
    public RecipeResponseMapper(@Lazy RateFacade rateService) {
        this.rateFacade = rateService;
    }

    public RecipeResponse recipeToResponse(Recipe recipe) {
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
