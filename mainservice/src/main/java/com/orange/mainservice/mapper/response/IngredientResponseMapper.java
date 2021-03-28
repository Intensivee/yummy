package com.orange.mainservice.mapper.response;

import com.orange.mainservice.entity.Ingredient;
import com.orange.mainservice.response.IngredientResponse;
import org.springframework.stereotype.Component;

@Component
public class IngredientResponseMapper {

    public IngredientResponse ingredientToResponse(Ingredient ingredient){
        return new IngredientResponse(
                ingredient.getId(),
                ingredient.getAmount(),
                ingredient.getAmountType(),
                ingredient.getComponent().getName()
        );
    }
}
