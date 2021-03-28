package com.orange.mainservice.service;

import com.orange.mainservice.entity.Rate;
import com.orange.mainservice.mapper.response.RateResponseMapper;
import com.orange.mainservice.repository.RateRepository;
import com.orange.mainservice.response.RateResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class RateService {

    private final RateRepository rateRepository;
    private final RateResponseMapper responseMapper;

    public RateResponse getResponseById(Long id){
        return responseMapper.rateToResponse(getById(id));
    }

    public Double getRecipeAvgRate(Long recipeId) {
        return rateRepository.getRecipeAverageRate(recipeId)
                .orElse(0D);
    }

    private Rate getById(Long id){
        return rateRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }
}
