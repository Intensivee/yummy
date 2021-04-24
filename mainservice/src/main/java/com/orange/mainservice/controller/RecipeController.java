package com.orange.mainservice.controller;

import com.orange.mainservice.request.RecipeRequest;
import com.orange.mainservice.response.RecipeResponse;
import com.orange.mainservice.service.RecipeService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("recipes")
@AllArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @GetMapping("/{id}")
    public ResponseEntity<RecipeResponse> getById(@PathVariable("id") Long id){
        return ResponseEntity.ok(recipeService.getResponseById(id));
    }

    @GetMapping
    public ResponseEntity<Page<RecipeResponse>> getAllPaged(Pageable pageable) {
        return ResponseEntity.ok(recipeService.getAllPaged(pageable));
    }

    @GetMapping("/search/findByUserId/{id}")
    public ResponseEntity<Page<RecipeResponse>> getByUserIdPaged(@PathVariable("id") Long id, Pageable pageable){
        return ResponseEntity.ok(recipeService.getByUserIdPaged(id, pageable));
    }

    @GetMapping("/search/findByCategoryName/{category}")
    public ResponseEntity<Page<RecipeResponse>> getByCategoryName(@PathVariable("category") String category, Pageable pageable){
        return ResponseEntity.ok(recipeService.getByCategoryName(category, pageable));
    }

    @PostMapping
    public ResponseEntity<RecipeResponse> create(@Valid @RequestBody RecipeRequest request){
        RecipeResponse created = recipeService.add(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeResponse> edit(@PathVariable("id") Long id,
                                                       @Valid @RequestBody RecipeRequest request){
        RecipeResponse edited = recipeService.edit(id, request);
        return ResponseEntity.ok(edited);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id){
        recipeService.delete(id);
        return ResponseEntity.ok().build();
    }
}
