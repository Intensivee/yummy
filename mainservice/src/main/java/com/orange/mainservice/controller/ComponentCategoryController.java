package com.orange.mainservice.controller;

import com.orange.mainservice.service.ComponentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ComponentCategoryController {

    private final ComponentCategoryService categoryService;

    @Autowired
    public ComponentCategoryController(ComponentCategoryService categoryService) {
        this.categoryService = categoryService;
    }
}
