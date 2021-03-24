package com.orange.mainservice.service;

import com.orange.mainservice.repository.RecipeCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipeCategoryService {

    private final RecipeCategoryRepository categoryRepository;

    @Autowired
    public RecipeCategoryService(RecipeCategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
}
