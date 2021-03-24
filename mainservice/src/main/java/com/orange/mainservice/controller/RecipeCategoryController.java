package com.orange.mainservice.controller;

import com.orange.mainservice.service.RecipeCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecipeCategoryController {

    private final RecipeCategoryService categoryService;

    @Autowired
    public RecipeCategoryController(RecipeCategoryService categoryService) {
        this.categoryService = categoryService;
    }
}
