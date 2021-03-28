package com.orange.mainservice.service;

import com.orange.mainservice.entity.Recipe;
import com.orange.mainservice.mapper.response.RecipeResponseMapper;
import com.orange.mainservice.repository.RecipeRepository;
import com.orange.mainservice.response.RecipeResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeResponseMapper responseMapper;

    public RecipeResponse getResponseById(Long id){
        return responseMapper.recipeToResponse(getById(id));
    }

    private Recipe getById(Long id){
        return recipeRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }
}
