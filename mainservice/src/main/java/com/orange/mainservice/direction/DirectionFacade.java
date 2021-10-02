package com.orange.mainservice.direction;

import com.orange.mainservice.recipe.Recipe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DirectionFacade {

    private final DirectionService directionService;

    public void createDirections(List<String> directions, Recipe recipe) {
        directionService.createDirections(directions, recipe);
    }

    public void replaceDirections(List<String> directions, Recipe recipe) {
        directionService.replaceDirections(directions, recipe);
    }
}
