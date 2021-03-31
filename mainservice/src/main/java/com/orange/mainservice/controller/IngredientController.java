package com.orange.mainservice.controller;

import com.orange.mainservice.request.IngredientRequest;
import com.orange.mainservice.response.IngredientResponse;
import com.orange.mainservice.service.IngredientService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("ingredients")
@AllArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;

    @GetMapping("/{id}")
    public ResponseEntity<IngredientResponse> getById(@PathVariable("id") Long id){
        return ResponseEntity.ok(ingredientService.getResponseById(id));
    }

    @PostMapping
    public ResponseEntity<IngredientResponse> create(@Valid @RequestBody IngredientRequest request){
        IngredientResponse created = ingredientService.add(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(created.getIngredientId()).toUri();
        return ResponseEntity.created(location).body(created);
    }
}
