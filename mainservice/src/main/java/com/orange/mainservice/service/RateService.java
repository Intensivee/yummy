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

    public Rate getById(Long id){
        return rateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rate", "id", id));
    }

    public Double getRecipeAvgRate(Long recipeId) {
        return rateRepository.getRecipeAverageRate(recipeId)
                .orElse(0D);
    }

    public Double getUserAvgRate(Long userId) {
        return rateRepository.getUserAverageRate(userId)
                .orElse(0D);
    }

    public RateResponse add(RateRequest request){
        validateCreateRequest(request);
        Rate rateToAdd = createNewEntityFromRequest(request);
        Rate addedRate = rateRepository.save(rateToAdd);
        return responseMapper.rateToResponse(addedRate);
    }

    public RateResponse edit(Long id, RateRequest request){
        validateEditInput(id, request);
        Rate rateToEdit = createEditedEntityFromRequest(request);
        Rate editedRate = rateRepository.save(rateToEdit);
        return responseMapper.rateToResponse(editedRate);
    }

    public void delete(Long id) {
        rateRepository.delete(getById(id));
    }

    private void validateEditInput(Long id, RateRequest request){
        if(idNotPresentORNotMatching(id, request)){
            throw new PathNotMatchBodyException(id, request.getId());
        }
        if(!rateRepository.existsById(id)){
            throw new ResourceNotFoundException("ComponentCategory", "id", id);
        }
    }

    private boolean idNotPresentORNotMatching(Long pathId, RateRequest request){
        return !isIdInRequest(request) || !request.getId().equals(pathId);
    }

    private void validateCreateRequest(RateRequest request){
        if(isIdInRequest(request)){
            throw new ResourceCreateException(request.getId());
        }
    }

    private boolean isIdInRequest(RateRequest request){
        return request.getId() != null;
    }

    private Rate createNewEntityFromRequest(RateRequest request){
        return new Rate(
                request.getId(),
                request.getValue(),
                null,
                userService.getById(request.getUserId()),
                recipeService.getById(request.getRecipeId())
        );
    }

    private Rate createEditedEntityFromRequest(RateRequest request){
        Rate rate = getById(request.getId());
        return new Rate(
                request.getId(),
                request.getValue(),
                rate.getDateCreated(),
                userService.getById(request.getUserId()),
                recipeService.getById(request.getRecipeId())
        );
    }
}
