package com.orange.mainservice.service;

import com.orange.mainservice.entity.Recipe;
import com.orange.mainservice.exception.PathNotMatchBodyException;
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
        return responseMapper.recipeToResponse(recipeRepository.save(recipe));
    }

    public RecipeResponse edit(Long id, RecipeRequest request){
        validateEditInput(id, request);
        Recipe recipe = createEntityFromRequest(request);
        return responseMapper.recipeToResponse(recipeRepository.save(recipe));
    }

    public void delete(Long id) {
        recipeRepository.delete(getById(id));
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

    private void validateEditInput(Long id, RecipeRequest request){
        if(idNotPresentORNotMatching(id, request)){
            throw new PathNotMatchBodyException(id, request.getRecipeId());
        }
        if(!recipeRepository.existsById(id)){
            throw new ResourceNotFoundException("ComponentCategory", "id", id);
        }
    }

    private boolean idNotPresentORNotMatching(Long pathId, RecipeRequest request){
        return request.getRecipeId() == null || !request.getRecipeId().equals(pathId);
    }
}
