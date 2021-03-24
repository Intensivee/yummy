package com.orange.mainservice.service;

import com.orange.mainservice.repository.ComponentCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComponentCategoryService {

    private final ComponentCategoryRepository categoryRepository;

    @Autowired
    public ComponentCategoryService(ComponentCategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
}
