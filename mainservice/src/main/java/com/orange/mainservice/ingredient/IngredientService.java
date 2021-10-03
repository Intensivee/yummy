package com.orange.mainservice.ingredient;

import com.orange.mainservice.component.ComponentFacade;
import com.orange.mainservice.entity.enums.AmountType;
import com.orange.mainservice.exception.PathNotMatchBodyException;
import com.orange.mainservice.exception.ResourceCreateException;
import com.orange.mainservice.exception.ResourceNotFoundException;
import com.orange.mainservice.recipe.Recipe;
import com.orange.mainservice.recipe.RecipeFacade;
import com.orange.mainservice.recipe.RecipeIngredientsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final IngredientResponseMapper responseMapper;
    private final RecipeFacade recipeFacade;
    private final ComponentFacade componentFacade;

    @Transactional(rollbackFor = Exception.class)
    public void replaceIngredients(List<RecipeIngredientsDto> ingredients, Recipe recipe) {
        ingredientRepository.findByRecipeId(recipe.getId()).stream()
                .map(Ingredient::getId)
                .forEach(this::deleteIngredient);
        createIngredients(ingredients, recipe);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteIngredient(Long ingredientId) {
        var component = getIngredientById(ingredientId).getComponent();
        boolean shouldComponentBeDeleted = componentFacade.isNotAcceptedAndReferencedInJustOneIngredient(component);

        ingredientRepository.deleteById(ingredientId);
        if (shouldComponentBeDeleted) {
            componentFacade.deleteById(component.getId());
        }
    }

    void createIngredients(List<RecipeIngredientsDto> ingredientsDtos, Recipe recipe) {
        List<Ingredient> ingredients = ingredientsDtos.stream()
                .map(dto -> createEntityFromRequest(dto, recipe))
                .collect(Collectors.toList());
        this.ingredientRepository.saveAll(ingredients);
    }

    IngredientResponse getResponseById(Long id) {
        return responseMapper.ingredientToResponse(getIngredientById(id));
    }

    IngredientResponse createIngredient(IngredientRequest request) {
        validateCreateRequest(request);
        var createdIngredient = ingredientRepository.save(createEntityFromRequest(request));
        return responseMapper.ingredientToResponse(createdIngredient);
    }

    IngredientResponse editIngredient(Long id, IngredientRequest request) {
        validateEditInput(id, request);
        var editedIngredient = ingredientRepository.save(createEntityFromRequest(request));
        return responseMapper.ingredientToResponse(editedIngredient);
    }

    Set<IngredientResponse> getByRecipeId(Long id) {
        return ingredientRepository.findByRecipeId(id).stream()
                .map(responseMapper::ingredientToResponse)
                .collect(Collectors.toSet());
    }

    List<AmountType> getAmountTypes() {
        return Arrays.asList(AmountType.values());
    }

    private Ingredient getIngredientById(Long id) {
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

    private Ingredient createEntityFromRequest(IngredientRequest request) {
        return new Ingredient(
                request.getId(),
                request.getAmount(),
                request.getAmountType(),
                recipeFacade.getById(request.getRecipeId()),
                componentFacade.getById(request.getComponentId())
        );
    }

    private Ingredient createEntityFromRequest(RecipeIngredientsDto recipeIngredientsDto, Recipe recipe) {
        return new Ingredient(
                recipeIngredientsDto.getAmount(),
                recipeIngredientsDto.getAmountType(),
                recipe,
                componentFacade.getOrCreateComponentByName(recipeIngredientsDto.getComponentName())
        );
    }
}
