package com.orange.mainservice.recipe;

import com.orange.mainservice.component.Component;
import com.orange.mainservice.component.ComponentFacade;
import com.orange.mainservice.direction.DirectionFacade;
import com.orange.mainservice.exception.PathNotMatchBodyException;
import com.orange.mainservice.exception.ResourceCreateException;
import com.orange.mainservice.exception.ResourceNotFoundException;
import com.orange.mainservice.ingredient.Ingredient;
import com.orange.mainservice.ingredient.IngredientFacade;
import com.orange.mainservice.print.RecipePdfUtils;
import com.orange.mainservice.recipecategory.RecipeCategoryFacade;
import com.orange.mainservice.security.AuthenticationFacade;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class RecipeService {

    @Lazy
    private final IngredientFacade ingredientFacade;
    @Lazy
    private final DirectionFacade directionFacade;
    private final ComponentFacade componentFacade;
    private final AuthenticationFacade authenticationFacade;
    private final RecipeCategoryFacade categoryFacade;
    private final RecipeRepository recipeRepository;
    private final RecipeResponseMapper responseMapper;

    @Transactional(rollbackFor = Exception.class)
    public RecipeResponse createRecipe(RecipeRequest recipeRequest) {
        validateRecipeCreateRequest(recipeRequest);

        var createdRecipe = recipeRepository.save(createEntityFromRequest(recipeRequest));
        directionFacade.createDirections(recipeRequest.getDirections(), createdRecipe);
        ingredientFacade.createIngredients(recipeRequest.getIngredients(), createdRecipe);
        return responseMapper.recipeToResponse(createdRecipe);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteRecipe(Long recipeId) {
        Set<Long> notAcceptedComponentsIds = getById(recipeId).getIngredients()
                .stream()
                .map(Ingredient::getComponent)
                .filter(componentFacade::isNotAcceptedAndReferencedInJustOneIngredient)
                .map(Component::getId)
                .collect(Collectors.toSet());
        recipeRepository.deleteById(recipeId);
        notAcceptedComponentsIds.forEach(componentFacade::deleteById);
    }

    @Transactional(rollbackFor = Exception.class)
    public RecipeResponse editRecipe(Long id, RecipeRequest request) {
        validateRecipeEditRequest(id, request);

        var editedRecipe = recipeRepository.save(createEntityFromRequest(request));
        directionFacade.replaceDirections(request.getDirections(), editedRecipe);
        ingredientFacade.replaceIngredients(request.getIngredients(), editedRecipe);
        return responseMapper.recipeToResponse(editedRecipe);
    }

    Recipe getById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe", "id", id));
    }

    Page<RecipeResponse> getAllPaged(Pageable pageable) {
        return recipeRepository.findAll(pageable)
                .map(responseMapper::recipeToResponse);
    }

    Page<RecipeResponse> getByUserIdPaged(Long id, Pageable pageable) {
        return recipeRepository.findAllByUserId(id, pageable)
                .map(responseMapper::recipeToResponse);
    }

    Page<RecipeResponse> getByUsernamePaged(String username, Pageable pageable) {
        return recipeRepository.findAllByUser_Username(username, pageable)
                .map(responseMapper::recipeToResponse);
    }

    Page<RecipeResponse> getByCategoryName(String category, Pageable pageable) {
        return recipeRepository.findAllByCategories_Name(category, pageable)
                .map(responseMapper::recipeToResponse);
    }

    Page<RecipeResponse> getByComponentName(String component, Pageable pageable) {
        return recipeRepository.findAllDistinctByIngredients_Component_name(component, pageable)
                .map(responseMapper::recipeToResponse);
    }

    Page<RecipeResponse> getBySearchKey(String searchKey, Pageable pageable) {
        return recipeRepository.findByTitleIgnoreCaseContaining(searchKey, pageable)
                .map(responseMapper::recipeToResponse);
    }

    Page<RecipeResponse> getRecipesByTimeType(TimeType timeType, Pageable pageable) {
        return recipeRepository.findByTimeType(pageable, timeType)
                .map(responseMapper::recipeToResponse);
    }

    List<RecipeResponse> getTop3RatedRecipes() {
        return recipeRepository.getRecipesByRateDesc(PageRequest.of(0, 3))
                .stream()
                .map(responseMapper::recipeToResponse)
                .collect(Collectors.toList());
    }

    RecipeResponse getResponseById(Long id) {
        return responseMapper.recipeToResponse(getById(id));
    }

    private void validateRecipeEditRequest(Long id, RecipeRequest request) {
        if (isIdNotPresentOrNotMatching(id, request)) {
            throw new PathNotMatchBodyException(id, request.getId());
        }
        if (!recipeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Recipe", "id", id);
        }
    }

    private boolean isIdNotPresentOrNotMatching(Long pathId, RecipeRequest request) {
        return !isIdInRequest(request) || !request.getId().equals(pathId);
    }

    private void validateRecipeCreateRequest(RecipeRequest request) {
        if (isIdInRequest(request)) {
            throw new ResourceCreateException(request.getId());
        }
    }

    private boolean isIdInRequest(RecipeRequest request) {
        return Objects.nonNull(request.getId());
    }

    private Recipe createEntityFromRequest(RecipeRequest request) {
        return new Recipe(
                request.getId(),
                request.getTimeType(),
                request.getTitle(),
                request.getImg(),
                authenticationFacade.getCurrentUser(),
                request.getCategoriesIds().stream()
                        .map(categoryFacade::getById)
                        .collect(Collectors.toSet())
        );
    }

    public InputStreamResource generatePDFFileForRecipe(Long recipeId, RecipePdfRequest pdfRequest) {
        return RecipePdfUtils.generatePDFFileForRecipe(getById(recipeId), pdfRequest);
    }
}
