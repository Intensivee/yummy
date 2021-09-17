package com.orange.mainservice.ingredient;

import com.orange.mainservice.component.ComponentFacade;
import com.orange.mainservice.exception.PathNotMatchBodyException;
import com.orange.mainservice.exception.ResourceCreateException;
import com.orange.mainservice.exception.ResourceNotFoundException;
import com.orange.mainservice.recipe.RecipeFacade;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final IngredientResponseMapper responseMapper;
    private final RecipeFacade recipeFacade;
    private final ComponentFacade componentFacade;

    IngredientResponse getResponseById(Long id) {
        return responseMapper.ingredientToResponse(getById(id));
    }

    IngredientResponse add(IngredientRequest request) {
        validateCreateRequest(request);
        Ingredient ingredientToAdd = createEntityFromRequest(request);
        Ingredient addedIngredient = ingredientRepository.save(ingredientToAdd);
        return responseMapper.ingredientToResponse(addedIngredient);
    }

    IngredientResponse edit(Long id, IngredientRequest request) {
        validateEditInput(id, request);
        Ingredient ingredientToEdit = createEntityFromRequest(request);
        Ingredient editedIngredient = ingredientRepository.save(ingredientToEdit);
        return responseMapper.ingredientToResponse(editedIngredient);
    }

    void delete(Long id) {
        ingredientRepository.delete(getById(id));
    }

    Set<IngredientResponse> getByRecipeId(Long id) {
        return ingredientRepository.findByRecipeId(id).stream()
                .map(responseMapper::ingredientToResponse)
                .collect(Collectors.toSet());
    }

    private Ingredient getById(Long id) {
        return ingredientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient", "id", id));
    }

    private void validateEditInput(Long id, IngredientRequest request) {
        if (isIdNotPresentOrNotMatching(id, request)) {
            throw new PathNotMatchBodyException(id, request.getId());
        }
        if (!ingredientRepository.existsById(id)) {
            throw new ResourceNotFoundException("ComponentCategory", "id", id);
        }
    }

    private boolean isIdNotPresentOrNotMatching(Long pathId, IngredientRequest request) {
        return !isIdInRequest(request) || !request.getId().equals(pathId);
    }

    private void validateCreateRequest(IngredientRequest request){
        if(isIdInRequest(request)){
            throw new ResourceCreateException(request.getId());
        }
    }

    private boolean isIdInRequest(IngredientRequest request){
        return Objects.nonNull(request.getId());
    }

    private Ingredient createEntityFromRequest(IngredientRequest request){
        return new Ingredient(
                request.getId(),
                request.getAmount(),
                request.getAmountType(),
                recipeFacade.getById(request.getRecipeId()),
                componentFacade.getById(request.getComponentId())
        );
    }
}
