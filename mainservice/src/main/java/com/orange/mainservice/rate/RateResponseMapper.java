package com.orange.mainservice.rate;

import org.springframework.stereotype.Component;

@Component
class RateResponseMapper {

    public RateResponse rateToResponse(Rate rate) {
        return new RateResponse(
                rate.getId(),
                rate.getValue(),
                rate.getUser().getId(),
                rate.getRecipe().getId()
        );
    }
}
