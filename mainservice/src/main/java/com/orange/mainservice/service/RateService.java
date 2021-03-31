package com.orange.mainservice.service;

import com.orange.mainservice.entity.Rate;
import com.orange.mainservice.exception.ResourceCreateException;
import com.orange.mainservice.exception.ResourceNotFoundException;
import com.orange.mainservice.mapper.response.RateResponseMapper;
import com.orange.mainservice.repository.RateRepository;
import com.orange.mainservice.request.RateRequest;
import com.orange.mainservice.response.RateResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RateService {

    private final RateRepository rateRepository;
    private final RateResponseMapper responseMapper;
    private final UserService userService;
    private final RecipeService recipeService;

    public RateResponse getResponseById(Long id){
        return responseMapper.rateToResponse(getById(id));
    }

    public Double getRecipeAvgRate(Long recipeId) {
        return rateRepository.getRecipeAverageRate(recipeId)
                .orElse(0D);
    }

    public RateResponse add(RateRequest request){
        if(request.getRateId() != null){
            throw new ResourceCreateException(request.getRateId());
        }
        Rate rate = createEntityFromRequest(request);
        return responseMapper.rateToResponse(rateRepository.save(rate));
    }

    private Rate getById(Long id){
        return rateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rate", "id", id));
    }

    private Rate createEntityFromRequest(RateRequest request){
        return new Rate(
                request.getRateId(),
                request.getValue(),
                null,
                userService.getById(request.getUserId()),
                recipeService.getById(request.getRecipeId())
        );
    }
}
