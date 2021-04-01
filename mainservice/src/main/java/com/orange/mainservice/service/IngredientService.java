package com.orange.mainservice.service;

import com.orange.mainservice.entity.Ingredient;
import com.orange.mainservice.exception.PathNotMatchBodyException;
import com.orange.mainservice.exception.ResourceCreateException;
import com.orange.mainservice.exception.ResourceNotFoundException;
import com.orange.mainservice.mapper.response.IngredientResponseMapper;
import com.orange.mainservice.repository.IngredientRepository;
import com.orange.mainservice.request.IngredientRequest;
import com.orange.mainservice.response.IngredientResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final IngredientResponseMapper responseMapper;
    private final RecipeService recipeService;
    private final ComponentService componentService;

    public IngredientResponse getResponseById(Long id){
        return responseMapper.ingredientToResponse(getById(id));
    }

    public IngredientResponse add(IngredientRequest request){
        if(request.getIngredientId() != null){
            throw new ResourceCreateException(request.getIngredientId());
        }
        Ingredient ingredient = createEntityFromRequest(request);
        return responseMapper.ingredientToResponse(ingredientRepository.save(ingredient));
    }

    public IngredientResponse edit(Long id, IngredientRequest request){
        validateEditInput(id, request);
        Ingredient ingredient = createEntityFromRequest(request);
        return responseMapper.ingredientToResponse(ingredientRepository.save(ingredient));
    }

    public void delete(Long id) {
        ingredientRepository.delete(getById(id));
    }

    private Ingredient getById(Long id){
        return ingredientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient", "id", id));
    }

    private Ingredient createEntityFromRequest(IngredientRequest request){
        return new Ingredient(
                request.getIngredientId(),
                request.getAmount(),
                request.getAmountType(),
                recipeService.getById(request.getRecipeId()),
                componentService.getById(request.getComponentId())
        );
    }

    private void validateEditInput(Long id, IngredientRequest request){
        if(idNotPresentORNotMatching(id, request)){
            throw new PathNotMatchBodyException(id, request.getIngredientId());
        }
        if(!ingredientRepository.existsById(id)){
            throw new ResourceNotFoundException("ComponentCategory", "id", id);
        }
    }

    private boolean idNotPresentORNotMatching(Long pathId, IngredientRequest request){
        return request.getIngredientId() == null || !request.getIngredientId().equals(pathId);
    }
}
