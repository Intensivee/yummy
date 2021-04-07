package com.orange.mainservice.service;

import com.orange.mainservice.entity.RecipeCategory;
import com.orange.mainservice.exception.PathNotMatchBodyException;
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
        validateCreateRequest(request);
        RecipeCategory categoryToAdd = createEntityFromRequest(request);
        RecipeCategory addedCategory = categoryRepository.save(categoryToAdd);
        return responseMapper.categoryToResponse(addedCategory);
    }

    public RecipeCategoryResponse edit(Long id, RecipeCategoryRequest request){
        validateEditInput(id, request);
        RecipeCategory categoryToEdit = createEntityFromRequest(request);
        RecipeCategory editedCategory = categoryRepository.save(categoryToEdit);
        return responseMapper.categoryToResponse(editedCategory);
    }

    public void delete(Long id) {
        categoryRepository.delete(getById(id));
    }

    private void validateEditInput(Long id, RecipeCategoryRequest request){
        if(idNotPresentORNotMatching(id, request)){
            throw new PathNotMatchBodyException(id, request.getId());
        }
        if(!categoryRepository.existsById(id)){
            throw new ResourceNotFoundException("ComponentCategory", "id", id);
        }
    }

    private boolean idNotPresentORNotMatching(Long pathId, RecipeCategoryRequest request){
        return !isIdInRequest(request) || !request.getId().equals(pathId);
    }

    private void validateCreateRequest(RecipeCategoryRequest request){
        if(isIdInRequest(request)){
            throw new ResourceCreateException(request.getId());
        }
    }

    private boolean isIdInRequest(RecipeCategoryRequest request){
        return request.getId() != null;
    }

    private RecipeCategory createEntityFromRequest(RecipeCategoryRequest request){
        return new RecipeCategory(
                request.getId(),
                request.getName(),
                request.getImg()
        );
    }
}
