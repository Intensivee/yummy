package com.orange.mainservice.recipecategory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecipeCategoryFacade {

    private final RecipeCategoryService categoryService;

    public RecipeCategory getById(Long id) {
        return categoryService.getById(id);
    }
}
