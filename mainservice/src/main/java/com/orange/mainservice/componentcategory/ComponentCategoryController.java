package com.orange.mainservice.componentcategory;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Set;

@RestController
@RequestMapping("componentCategories")
@AllArgsConstructor
class ComponentCategoryController {

    private final ComponentCategoryService categoryService;

    @GetMapping
    public ResponseEntity<Set<ComponentCategoryResponse>> getAll() {
        return ResponseEntity.ok(categoryService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComponentCategoryResponse> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(categoryService.getResponseById(id));
    }

    @PostMapping
    public ResponseEntity<ComponentCategoryResponse> create(@Valid @RequestBody ComponentCategoryRequest request){
        ComponentCategoryResponse created = categoryService.add(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComponentCategoryResponse> edit(@PathVariable("id") Long id,
                                                @Valid @RequestBody ComponentCategoryRequest request){
        ComponentCategoryResponse edited = categoryService.edit(id, request);
        return ResponseEntity.ok(edited);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id){
        categoryService.delete(id);
        return ResponseEntity.ok().build();
    }
}
