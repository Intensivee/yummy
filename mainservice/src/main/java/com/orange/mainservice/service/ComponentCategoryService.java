package com.orange.mainservice.service;

import com.orange.mainservice.entity.ComponentCategory;
import com.orange.mainservice.exception.ResourceCreateException;
import com.orange.mainservice.exception.ResourceNotFoundException;
import com.orange.mainservice.mapper.response.ComponentCategoryResponseMapper;
import com.orange.mainservice.repository.ComponentCategoryRepository;
import com.orange.mainservice.request.ComponentCategoryRequest;
import com.orange.mainservice.response.ComponentCategoryResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ComponentCategoryService {

    private final ComponentCategoryRepository categoryRepository;
    private final ComponentCategoryResponseMapper responseMapper;

    public ComponentCategoryResponse getResponseById(Long id){
        return responseMapper.categoryToResponse(getById(id));
    }

    public ComponentCategory getById(Long id){
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ComponentCategory", "id", id));
    }

    public ComponentCategoryResponse add(ComponentCategoryRequest request){
        if(request.getCategoryId() != null){
            throw new ResourceCreateException(request.getCategoryId());
        }
        ComponentCategory category = createEntityFromRequest(request);
        return responseMapper.categoryToResponse(categoryRepository.save(category));
    }

    public ComponentCategoryResponse edit(Long id, ComponentCategoryRequest request) {
        validateEditInput(id, request);

        ComponentCategory category = createEntityFromRequest(request);
        return responseMapper.categoryToResponse(categoryRepository.save(category));
    }

    private ComponentCategory createEntityFromRequest(ComponentCategoryRequest request) {
        return new ComponentCategory(
                request.getCategoryId(),
                request.getName()
        );
    }

    private void validateEditInput(Long id, ComponentCategoryRequest request){
        if(idNotPresentORNotMatching(id, request)){
            throw new ResourceCreateException(id); // TODO : custom exception
        }
        if(!categoryRepository.existsById(id)){
            throw new ResourceNotFoundException("ComponentCategory", "id", id);
        }
    }

    private boolean idNotPresentORNotMatching(Long pathId, ComponentCategoryRequest request){
        return request.getCategoryId() == null || !request.getCategoryId().equals(pathId);
    }
}
