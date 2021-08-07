package com.orange.mainservice.rate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RateFacade {

    private final RateService rateService;

    public Double getRecipeAverageRate(Long recipeId) {
        return rateService.getRecipeAverageRate(recipeId);
    }

    public Double getUserAverageRate(Long userId) {
        return rateService.getUserAverageRate(userId);
    }
}
