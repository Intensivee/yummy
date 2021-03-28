package com.orange.mainservice.service;

import com.orange.mainservice.entity.ComponentCategory;
import com.orange.mainservice.mapper.response.ComponentCategoryResponseMapper;
import com.orange.mainservice.repository.ComponentCategoryRepository;
import com.orange.mainservice.response.ComponentCategoryResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class ComponentCategoryService {

    private final ComponentCategoryRepository categoryRepository;
    private final ComponentCategoryResponseMapper responseMapper;

    public ComponentCategoryResponse getResponseById(Long id){
        return responseMapper.categoryToResponse(getById(id));
    }

    private ComponentCategory getById(Long id){
        return categoryRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }
}
