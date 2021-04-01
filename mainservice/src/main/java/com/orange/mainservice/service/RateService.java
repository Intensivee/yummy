package com.orange.mainservice.service;

import com.orange.mainservice.entity.Rate;
import com.orange.mainservice.exception.PathNotMatchBodyException;
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
        Rate rate = createNewEntityFromRequest(request);
        return responseMapper.rateToResponse(rateRepository.save(rate));
    }

    public RateResponse edit(Long id, RateRequest request){
        validateEditInput(id, request);
        Rate rate = createEditedEntityFromRequest(request);
        return responseMapper.rateToResponse(rateRepository.save(rate));
    }

    private Rate getById(Long id){
        return rateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rate", "id", id));
    }

    public void delete(Long id) {
        rateRepository.delete(getById(id));
    }

    private Rate createNewEntityFromRequest(RateRequest request){
        return new Rate(
                request.getRateId(),
                request.getValue(),
                null,
                userService.getById(request.getUserId()),
                recipeService.getById(request.getRecipeId())
        );
    }

    private Rate createEditedEntityFromRequest(RateRequest request){
        Rate rate = getById(request.getRateId());
        return new Rate(
                request.getRateId(),
                request.getValue(),
                rate.getDateCreated(),
                userService.getById(request.getUserId()),
                recipeService.getById(request.getRecipeId())
        );
    }

    private void validateEditInput(Long id, RateRequest request){
        if(idNotPresentORNotMatching(id, request)){
            throw new PathNotMatchBodyException(id, request.getRateId());
        }
        if(!rateRepository.existsById(id)){
            throw new ResourceNotFoundException("ComponentCategory", "id", id);
        }
    }

    private boolean idNotPresentORNotMatching(Long pathId, RateRequest request){
        return request.getRateId() == null || !request.getRateId().equals(pathId);
    }
}
