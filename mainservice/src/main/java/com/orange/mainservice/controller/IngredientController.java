package com.orange.mainservice.controller;

import com.orange.mainservice.response.IngredientResponse;
import com.orange.mainservice.service.IngredientService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ingredients")
@AllArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;

    @GetMapping("/{id}")
    public ResponseEntity<IngredientResponse> getById(@PathVariable("id") Long id){
        return ResponseEntity.ok(ingredientService.getResponseById(id));
    }
}
