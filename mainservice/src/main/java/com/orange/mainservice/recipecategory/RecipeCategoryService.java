package com.orange.mainservice.recipecategory;

import com.orange.mainservice.exception.PathNotMatchBodyException;
import com.orange.mainservice.exception.ResourceCreateException;
import com.orange.mainservice.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class RecipeCategoryService {

    private final RecipeCategoryRepository categoryRepository;
    private final RecipeCategoryResponseMapper responseMapper;

    List<RecipeCategoryResponse> getAllOrderByName() {
        return categoryRepository.findAllByOrderByNameAsc().stream()
                .map(responseMapper::categoryToResponse)
                .collect(Collectors.toList());
    }

    List<RecipeCategoryResponse> getAllWithNonNullImage(int limit) {
        return categoryRepository.findAllByImgUrlIsNotNull(PageRequest.of(0, limit)).stream()
                .map(responseMapper::categoryToResponse)
                .collect(Collectors.toList());
    }

    RecipeCategoryResponse getResponseById(Long id) {
        return responseMapper.categoryToResponse(getById(id));
    }

    RecipeCategory getById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RecipeCategory", "id", id));
    }

    RecipeCategoryResponse createCategory(RecipeCategoryRequest request) {
        validateCreateRequest(request);
        var addedRecipeCategory = categoryRepository.save(createEntityFromRequest(request));
        return responseMapper.categoryToResponse(addedRecipeCategory);
    }

    RecipeCategoryResponse editCategory(Long id, RecipeCategoryRequest request) {
        validateEditInput(id, request);
        var editedRecipeCategory = categoryRepository.save(createEntityFromRequest(request));
        return responseMapper.categoryToResponse(editedRecipeCategory);
    }

    void deleteCategory(Long id) {
        categoryRepository.delete(getById(id));
    }

    private void validateEditInput(Long id, RecipeCategoryRequest request){
        if (isIdNotPresentOrNotMatching(id, request)) {
            throw new PathNotMatchBodyException(id, request.getId());
        }
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("ComponentCategory", "id", id);
        }
    }

    private boolean isIdNotPresentOrNotMatching(Long pathId, RecipeCategoryRequest request) {
        return !isIdInRequest(request) || !request.getId().equals(pathId);
    }

    private void validateCreateRequest(RecipeCategoryRequest request){
        if(isIdInRequest(request)){
            throw new ResourceCreateException(request.getId());
        }
    }

    private boolean isIdInRequest(RecipeCategoryRequest request){
        return Objects.nonNull(request.getId());
    }

    private RecipeCategory createEntityFromRequest(RecipeCategoryRequest request){
        return new RecipeCategory(
                request.getId(),
                request.getName(),
                request.getImg()
        );
    }
}
