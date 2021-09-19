package com.orange.mainservice.recipecategory;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("recipeCategories")
@AllArgsConstructor
class RecipeCategoryController {

    private final RecipeCategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<RecipeCategoryResponse>> getAll() {
        return ResponseEntity.ok(categoryService.getAllOrderByName());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeCategoryResponse> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(categoryService.getResponseById(id));
    }

    @PostMapping
    public ResponseEntity<RecipeCategoryResponse> create(@Valid @RequestBody RecipeCategoryRequest request){
        RecipeCategoryResponse created = categoryService.createCategory(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeCategoryResponse> edit(@PathVariable("id") Long id,
                                             @Valid @RequestBody RecipeCategoryRequest request){
        RecipeCategoryResponse edited = categoryService.editCategory(id, request);
        return ResponseEntity.ok(edited);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }
}
