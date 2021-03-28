package com.orange.mainservice.mapper.response;

import com.orange.mainservice.entity.RecipeCategory;
import com.orange.mainservice.response.RecipeCategoryResponse;
import org.springframework.stereotype.Component;

@Component
public class RecipeCategoryResponseMapper {

    public RecipeCategoryResponse categoryToResponse(RecipeCategory category){
        return new RecipeCategoryResponse(
                category.getId(),
                category.getName(),
                category.getImgUrl()
        );
    }
}
