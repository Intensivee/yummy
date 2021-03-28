package com.orange.mainservice.controller;

import com.orange.mainservice.response.RecipeCategoryResponse;
import com.orange.mainservice.service.RecipeCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("recipeCategories")
@AllArgsConstructor
public class RecipeCategoryController {

    private final RecipeCategoryService categoryService;

    @GetMapping("/{id}")
    public ResponseEntity<RecipeCategoryResponse> getById(@PathVariable("id") Long id){
        return ResponseEntity.ok(categoryService.getResponseById(id));
    }
}
