package com.orange.mainservice.recipecategory;

import org.springframework.stereotype.Component;

@Component
class RecipeCategoryResponseMapper {

    RecipeCategoryResponse categoryToResponse(RecipeCategory category) {
        return new RecipeCategoryResponse(
                category.getId(),
                category.getName(),
                category.getImgUrl()
        );
    }
}
