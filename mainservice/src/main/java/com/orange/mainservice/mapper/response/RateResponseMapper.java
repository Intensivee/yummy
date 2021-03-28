package com.orange.mainservice.mapper.response;

import com.orange.mainservice.entity.Rate;
import com.orange.mainservice.response.RateResponse;
import org.springframework.stereotype.Component;

@Component
public class RateResponseMapper {

    public RateResponse rateToResponse(Rate rate){
        return new RateResponse(
                rate.getId(),
                rate.getValue(),
                rate.getUser().getId(),
                rate.getRecipe().getId()
        );
    }
}
