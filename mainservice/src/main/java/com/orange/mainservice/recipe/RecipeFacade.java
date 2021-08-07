package com.orange.mainservice.recipe;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecipeFacade {

    private final RecipeService recipeService;

    public Recipe getById(Long id) {
        return recipeService.getById(id);
    }
}
