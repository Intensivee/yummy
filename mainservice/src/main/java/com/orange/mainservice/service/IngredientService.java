package com.orange.mainservice.service;

import com.orange.mainservice.entity.Ingredient;
import com.orange.mainservice.exception.ResourceNotFoundException;
import com.orange.mainservice.mapper.response.IngredientResponseMapper;
import com.orange.mainservice.repository.IngredientRepository;
import com.orange.mainservice.response.IngredientResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final IngredientResponseMapper responseMapper;

    public IngredientResponse getResponseById(Long id){
        return responseMapper.ingredientToResponse(getById(id));
    }

    private Ingredient getById(Long id){
        return ingredientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient", "id", id));
    }
}
