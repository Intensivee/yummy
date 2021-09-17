package com.orange.mainservice.recipecategory;

import com.orange.mainservice.exception.PathNotMatchBodyException;
import com.orange.mainservice.exception.ResourceCreateException;
import com.orange.mainservice.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class RecipeCategoryService {

    private final RecipeCategoryRepository categoryRepository;
    private final RecipeCategoryResponseMapper responseMapper;

    Set<RecipeCategoryResponse> getAll() {
        return categoryRepository.findAll().stream()
                .map(responseMapper::categoryToResponse)
                .collect(Collectors.toSet());
    }

    RecipeCategoryResponse getResponseById(Long id) {
        return responseMapper.categoryToResponse(getById(id));
    }

    RecipeCategory getById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RecipeCategory", "id", id));
    }

    RecipeCategoryResponse add(RecipeCategoryRequest request) {
        validateCreateRequest(request);
        RecipeCategory categoryToAdd = createEntityFromRequest(request);
        RecipeCategory addedCategory = categoryRepository.save(categoryToAdd);
        return responseMapper.categoryToResponse(addedCategory);
    }

    RecipeCategoryResponse edit(Long id, RecipeCategoryRequest request) {
        validateEditInput(id, request);
        RecipeCategory categoryToEdit = createEntityFromRequest(request);
        RecipeCategory editedCategory = categoryRepository.save(categoryToEdit);
        return responseMapper.categoryToResponse(editedCategory);
    }

    void delete(Long id) {
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
