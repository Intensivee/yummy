package com.orange.mainservice.mapper.response;

import com.orange.mainservice.entity.Recipe;
import com.orange.mainservice.response.RecipeResponse;
import com.orange.mainservice.service.RateService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RecipeResponseMapper {

    private final RateService rateService;

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
