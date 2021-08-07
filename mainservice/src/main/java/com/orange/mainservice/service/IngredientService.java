package com.orange.mainservice.service;

import com.orange.mainservice.component.ComponentFacade;
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

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final IngredientResponseMapper responseMapper;
    private final RecipeService recipeService;
    private final ComponentFacade componentFacade;

    public IngredientResponse getResponseById(Long id){
        return responseMapper.ingredientToResponse(getById(id));
    }

    public Ingredient getById(Long id){
        return ingredientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient", "id", id));
    }

    public IngredientResponse add(IngredientRequest request){
        validateCreateRequest(request);
        Ingredient ingredientToAdd = createEntityFromRequest(request);
        Ingredient addedIngredient = ingredientRepository.save(ingredientToAdd);
        return responseMapper.ingredientToResponse(addedIngredient);
    }

    public IngredientResponse edit(Long id, IngredientRequest request){
        validateEditInput(id, request);
        Ingredient ingredientToEdit = createEntityFromRequest(request);
        Ingredient editedIngredient = ingredientRepository.save(ingredientToEdit);
        return responseMapper.ingredientToResponse(editedIngredient);
    }

    public void delete(Long id) {
        ingredientRepository.delete(getById(id));
    }

    public Set<IngredientResponse> getByRecipeId(Long id) {
        return ingredientRepository.findByRecipeId(id).stream()
                .map(responseMapper::ingredientToResponse)
                .collect(Collectors.toSet());
    }

    private void validateEditInput(Long id, IngredientRequest request){
        if(idNotPresentORNotMatching(id, request)){
            throw new PathNotMatchBodyException(id, request.getId());
        }
        if(!ingredientRepository.existsById(id)){
            throw new ResourceNotFoundException("ComponentCategory", "id", id);
        }
    }

    private boolean idNotPresentORNotMatching(Long pathId, IngredientRequest request){
        return !isIdInRequest(request) || !request.getId().equals(pathId);
    }

    private void validateCreateRequest(IngredientRequest request){
        if(isIdInRequest(request)){
            throw new ResourceCreateException(request.getId());
        }
    }

    private boolean isIdInRequest(IngredientRequest request){
        return request.getId() != null;
    }

    private Ingredient createEntityFromRequest(IngredientRequest request){
        return new Ingredient(
                request.getId(),
                request.getAmount(),
                request.getAmountType(),
                recipeService.getById(request.getRecipeId()),
                componentFacade.getById(request.getComponentId())
        );
    }
}
