package com.orange.mainservice.service;

import com.orange.mainservice.entity.ComponentCategory;
import com.orange.mainservice.exception.PathNotMatchBodyException;
import com.orange.mainservice.exception.ResourceCreateException;
import com.orange.mainservice.exception.ResourceNotFoundException;
import com.orange.mainservice.mapper.response.ComponentCategoryResponseMapper;
import com.orange.mainservice.repository.ComponentCategoryRepository;
import com.orange.mainservice.request.ComponentCategoryRequest;
import com.orange.mainservice.response.ComponentCategoryResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ComponentCategoryService {

    private final ComponentCategoryRepository categoryRepository;
    private final ComponentCategoryResponseMapper responseMapper;

    public Set<ComponentCategoryResponse> getAll() {
        return categoryRepository.findAll().stream()
                .map(responseMapper::categoryToResponse)
                .collect(Collectors.toSet());
    }

    public ComponentCategoryResponse getResponseById(Long id){
        return responseMapper.categoryToResponse(getById(id));
    }

    public ComponentCategory getById(Long id){
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ComponentCategory", "id", id));
    }

    public ComponentCategoryResponse add(ComponentCategoryRequest request){
        validateCreateRequest(request);
        ComponentCategory categoryToAdd = createEntityFromRequest(request);
        ComponentCategory addedCategory = categoryRepository.save(categoryToAdd);
        return responseMapper.categoryToResponse(addedCategory);
    }

    public ComponentCategoryResponse edit(Long id, ComponentCategoryRequest request) {
        validateEditRequest(id, request);
        ComponentCategory categoryToEdit = createEntityFromRequest(request);
        ComponentCategory editedCategory = categoryRepository.save(categoryToEdit);
        return responseMapper.categoryToResponse(editedCategory);
    }

    public void delete(Long id) {
        categoryRepository.delete(getById(id));
    }

    private boolean idNotPresentORNotMatching(Long pathId, ComponentCategoryRequest request){
        return !isIdInRequest(request) || !request.getId().equals(pathId);
    }

    private void validateCreateRequest(ComponentCategoryRequest request){
        if(isIdInRequest(request)){
            throw new ResourceCreateException(request.getId());
        }
    }

    private boolean isIdInRequest(ComponentCategoryRequest request){
        return request.getId() != null;
    }

    private ComponentCategory createEntityFromRequest(ComponentCategoryRequest request) {
        return new ComponentCategory(
                request.getId(),
                request.getName()
        );
    }

    private void validateEditRequest(Long id, ComponentCategoryRequest request){
        if(idNotPresentORNotMatching(id, request)){
            throw new PathNotMatchBodyException(id, request.getId());
        }
        if(!categoryRepository.existsById(id)){
            throw new ResourceNotFoundException("ComponentCategory", "id", id);
        }
    }
}
