package com.orange.mainservice.service;

import com.orange.mainservice.entity.Recipe;
import com.orange.mainservice.exception.ResourceCreateException;
import com.orange.mainservice.exception.ResourceNotFoundException;
import com.orange.mainservice.mapper.response.RecipeResponseMapper;
import com.orange.mainservice.repository.RecipeRepository;
import com.orange.mainservice.request.RecipeRequest;
import com.orange.mainservice.response.RecipeResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeResponseMapper responseMapper;
    private final UserService userService;
    private final RecipeCategoryService categoryService;

    public RecipeResponse getResponseById(Long id){
        return responseMapper.recipeToResponse(getById(id));
    }

    public RecipeResponse add(RecipeRequest request){
        if(request.getRecipeId() != null){
            throw new ResourceCreateException(request.getRecipeId());
        }
        Recipe recipe = createEntityFromRequest(request);
        recipe = recipeRepository.save(recipe);
        // TODO: create other relations

        return responseMapper.recipeToResponse(recipe);
    }

    public Recipe getById(Long id){
        return recipeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe", "id", id));
    }

    private Recipe createEntityFromRequest(RecipeRequest request){
        return new Recipe(
                request.getRecipeId(),
                request.getTimeType(),
                request.getTitle(),
                request.getImg(),
                userService.getById(request.getUserId()),
                request.getCategoriesIds().stream()
                        .map(categoryService::getById)
                        .collect(Collectors.toSet())
        );
    }
}
