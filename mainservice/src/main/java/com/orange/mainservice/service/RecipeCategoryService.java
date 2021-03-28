package com.orange.mainservice.service;

import com.orange.mainservice.entity.RecipeCategory;
import com.orange.mainservice.exception.ResourceNotFoundException;
import com.orange.mainservice.mapper.response.RecipeCategoryResponseMapper;
import com.orange.mainservice.repository.RecipeCategoryRepository;
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

    private RecipeCategory getById(Long id){
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RecipeCategory", "id", id));
    }
}