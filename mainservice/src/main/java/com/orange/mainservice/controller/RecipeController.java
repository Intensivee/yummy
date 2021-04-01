package com.orange.mainservice.controller;

import com.orange.mainservice.request.RecipeRequest;
import com.orange.mainservice.response.RecipeResponse;
import com.orange.mainservice.service.RecipeService;
import lombok.AllArgsConstructor;
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

    @PostMapping
    public ResponseEntity<RecipeResponse> create(@Valid @RequestBody RecipeRequest request){
        RecipeResponse created = recipeService.add(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(created.getRecipeId()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeResponse> edit(@PathVariable("id") Long id,
                                                       @Valid @RequestBody RecipeRequest request){
        RecipeResponse edited = recipeService.edit(id, request);
        return ResponseEntity.ok(edited);
    }
}
