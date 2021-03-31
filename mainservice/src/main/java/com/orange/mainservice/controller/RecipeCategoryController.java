package com.orange.mainservice.controller;

import com.orange.mainservice.request.RecipeCategoryRequest;
import com.orange.mainservice.response.RecipeCategoryResponse;
import com.orange.mainservice.service.RecipeCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("recipeCategories")
@AllArgsConstructor
public class RecipeCategoryController {

    private final RecipeCategoryService categoryService;

    @GetMapping("/{id}")
    public ResponseEntity<RecipeCategoryResponse> getById(@PathVariable("id") Long id){
        return ResponseEntity.ok(categoryService.getResponseById(id));
    }

    @PostMapping
    public ResponseEntity<RecipeCategoryResponse> create(@Valid @RequestBody RecipeCategoryRequest request){
        RecipeCategoryResponse created = categoryService.add(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(created.getCategoryId()).toUri();
        return ResponseEntity.created(location).body(created);
    }
}
