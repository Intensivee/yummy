package com.orange.mainservice.componentcategory;

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
class ComponentCategoryService {

    private final ComponentCategoryRepository categoryRepository;
    private final ComponentCategoryResponseMapper responseMapper;

    ComponentCategory getById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ComponentCategory", "id", id));
    }

    Set<ComponentCategoryResponse> getAll() {
        return categoryRepository.findAll().stream()
                .map(responseMapper::categoryToResponse)
                .collect(Collectors.toSet());
    }

    ComponentCategoryResponse getResponseById(Long id) {
        return responseMapper.categoryToResponse(getById(id));
    }

    ComponentCategoryResponse add(ComponentCategoryRequest request) {
        validateCreateRequest(request);
        ComponentCategory categoryToAdd = createEntityFromRequest(request);
        ComponentCategory addedCategory = categoryRepository.save(categoryToAdd);
        return responseMapper.categoryToResponse(addedCategory);
    }

    ComponentCategoryResponse edit(Long id, ComponentCategoryRequest request) {
        validateEditRequest(id, request);
        ComponentCategory categoryToEdit = createEntityFromRequest(request);
        ComponentCategory editedCategory = categoryRepository.save(categoryToEdit);
        return responseMapper.categoryToResponse(editedCategory);
    }

    void delete(Long id) {
        categoryRepository.delete(getById(id));
    }

    private boolean isIdNotPresentOrNotMatching(Long pathId, ComponentCategoryRequest request) {
        return !isIdInRequest(request) || !request.getId().equals(pathId);
    }

    private void validateCreateRequest(ComponentCategoryRequest request){
        if(isIdInRequest(request)){
            throw new ResourceCreateException(request.getId());
        }
    }

    private boolean isIdInRequest(ComponentCategoryRequest request){
        return Objects.nonNull(request.getId());
    }

    private ComponentCategory createEntityFromRequest(ComponentCategoryRequest request) {
        return new ComponentCategory(
                request.getId(),
                request.getName()
        );
    }

    private void validateEditRequest(Long id, ComponentCategoryRequest request){
        if (isIdNotPresentOrNotMatching(id, request)) {
            throw new PathNotMatchBodyException(id, request.getId());
        }
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("ComponentCategory", "id", id);
        }
    }
}
