package com.orange.mainservice.recipe;

import com.orange.mainservice.entity.enums.TimeType;
import com.orange.mainservice.exception.PathNotMatchBodyException;
import com.orange.mainservice.exception.ResourceCreateException;
import com.orange.mainservice.exception.ResourceNotFoundException;
import com.orange.mainservice.recipecategory.RecipeCategoryFacade;
import com.orange.mainservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeResponseMapper responseMapper;
    private final UserService userService;
    private final RecipeCategoryFacade categoryFacade;

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
        return recipeRepository.getRecipesByRateDesc(PageRequest.of(0, 3)).stream()
                .map(responseMapper::recipeToResponse)
                .collect(Collectors.toList());
    }

    RecipeResponse getResponseById(Long id) {
        return responseMapper.recipeToResponse(getById(id));
    }

    RecipeResponse add(RecipeRequest request) {
        validateCreateRequest(request);
        Recipe recipeToAdd = createEntityFromRequest(request);
        Recipe addedRecipe = recipeRepository.save(recipeToAdd);
        return responseMapper.recipeToResponse(addedRecipe);
    }

    RecipeResponse edit(Long id, RecipeRequest request) {
        validateEditInput(id, request);
        Recipe recipeToEdit = createEntityFromRequest(request);
        Recipe editedRecipe = recipeRepository.save(recipeToEdit);
        return responseMapper.recipeToResponse(editedRecipe);
    }

    void delete(Long id) {
        recipeRepository.delete(getById(id));
    }

    private void validateEditInput(Long id, RecipeRequest request){
        if (isIdNotPresentOrNotMatching(id, request)) {
            throw new PathNotMatchBodyException(id, request.getId());
        }
        if (!recipeRepository.existsById(id)) {
            throw new ResourceNotFoundException("ComponentCategory", "id", id);
        }
    }

    private boolean isIdNotPresentOrNotMatching(Long pathId, RecipeRequest request) {
        return !isIdInRequest(request) || !request.getId().equals(pathId);
    }

    private void validateCreateRequest(RecipeRequest request){
        if(isIdInRequest(request)){
            throw new ResourceCreateException(request.getId());
        }
    }

    private boolean isIdInRequest(RecipeRequest request){
        return request.getId() != null;
    }

    private Recipe createEntityFromRequest(RecipeRequest request){
        return new Recipe(
                request.getId(),
                request.getTimeType(),
                request.getTitle(),
                request.getImg(),
                userService.getById(request.getUserId()),
                request.getCategoriesIds().stream()
                        .map(categoryFacade::getById)
                        .collect(Collectors.toSet())
        );
    }
}
