package com.orange.mainservice.controller;

import com.orange.mainservice.response.ComponentCategoryResponse;
import com.orange.mainservice.service.ComponentCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("componentCategories")
@AllArgsConstructor
public class ComponentCategoryController {

    private final ComponentCategoryService categoryService;

    @GetMapping("/{id}")
    public ResponseEntity<ComponentCategoryResponse> getById(@PathVariable("id") Long id){
        return ResponseEntity.ok(categoryService.getResponseById(id));
    }
}
