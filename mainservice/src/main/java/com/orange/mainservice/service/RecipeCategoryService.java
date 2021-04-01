package com.orange.mainservice.service;

import com.orange.mainservice.entity.RecipeCategory;
import com.orange.mainservice.exception.ResourceCreateException;
import com.orange.mainservice.exception.ResourceNotFoundException;
import com.orange.mainservice.mapper.response.RecipeCategoryResponseMapper;
import com.orange.mainservice.repository.RecipeCategoryRepository;
import com.orange.mainservice.request.RecipeCategoryRequest;
import com.orange.mainservice.response.RecipeCategoryResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RecipeCategoryService {

    private final RecipeCategoryRepository categoryRepository;
    private final RecipeCategoryResponseMapper responseMapper;

    public RecipeCategoryResponse getResponseById(Long id){
        return responseMapper.categoryToResponse(getById(id));
    }

    public RecipeCategory getById(Long id){
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RecipeCategory", "id", id));
    }

    public RecipeCategoryResponse add(RecipeCategoryRequest request){
        if(request.getCategoryId() != null){
            throw new ResourceCreateException(request.getCategoryId());
        }
        RecipeCategory category = createEntityFromRequest(request);
        return responseMapper.categoryToResponse(categoryRepository.save(category));
    }

    public RecipeCategoryResponse edit(Long id, RecipeCategoryRequest request){
        validateEditInput(id, request);
        RecipeCategory category = createEntityFromRequest(request);
        return responseMapper.categoryToResponse(categoryRepository.save(category));
    }

    private RecipeCategory createEntityFromRequest(RecipeCategoryRequest request){
        return new RecipeCategory(
                request.getCategoryId(),
                request.getName(),
                request.getImg()
        );
    }

    private void validateEditInput(Long id, RecipeCategoryRequest request){
        if(idNotPresentORNotMatching(id, request)){
            throw new ResourceCreateException(id); // TODO : custom exception
        }
        if(!categoryRepository.existsById(id)){
            throw new ResourceNotFoundException("ComponentCategory", "id", id);
        }
    }

    private boolean idNotPresentORNotMatching(Long pathId, RecipeCategoryRequest request){
        return request.getCategoryId() == null || !request.getCategoryId().equals(pathId);
    }
}
