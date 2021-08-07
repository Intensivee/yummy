package com.orange.mainservice.ingredient;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Set;

@RestController
@RequestMapping("ingredients")
@AllArgsConstructor
class IngredientController {

    private final IngredientService ingredientService;

    @GetMapping("/{id}")
    public ResponseEntity<IngredientResponse> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(ingredientService.getResponseById(id));
    }

    @PostMapping
    public ResponseEntity<IngredientResponse> create(@Valid @RequestBody IngredientRequest request) {
        IngredientResponse created = ingredientService.add(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IngredientResponse> edit(@PathVariable("id") Long id,
                                                  @Valid @RequestBody IngredientRequest request){
        IngredientResponse edited = ingredientService.edit(id, request);
        return ResponseEntity.ok(edited);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        ingredientService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search/findByRecipeId/{id}")
    public ResponseEntity<Set<IngredientResponse>> getByRecipeId(@PathVariable Long id) {
        Set<IngredientResponse> ingredients = ingredientService.getByRecipeId(id);
        return ResponseEntity.ok(ingredients);
    }
}