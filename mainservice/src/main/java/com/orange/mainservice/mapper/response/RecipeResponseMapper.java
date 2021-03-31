package com.orange.mainservice.mapper.response;

import com.orange.mainservice.entity.Recipe;
import com.orange.mainservice.response.RecipeResponse;
import com.orange.mainservice.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class RecipeResponseMapper {


    private final RateService rateService;

    @Autowired
    public RecipeResponseMapper(@Lazy RateService rateService) {
        this.rateService = rateService;
    }

    public RecipeResponse recipeToResponse(Recipe recipe){
        return new RecipeResponse(
                recipe.getId(),
                recipe.getTimeType(),
                recipe.getTitle(),
                recipe.getImgUrl(),
                rateService.getRecipeAvgRate(recipe.getId())
        );
    }
}
