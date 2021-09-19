package com.orange.mainservice.rate;

import com.orange.mainservice.exception.PathNotMatchBodyException;
import com.orange.mainservice.exception.ResourceCreateException;
import com.orange.mainservice.exception.ResourceNotFoundException;
import com.orange.mainservice.recipe.RecipeFacade;
import com.orange.mainservice.user.UserFacade;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
class RateService {

    private final RateRepository rateRepository;
    private final RateResponseMapper responseMapper;
    private final UserFacade userFacade;
    private final RecipeFacade recipeFacade;

    Double getRecipeAverageRate(Long recipeId) {
        return rateRepository.getRecipeAverageRate(recipeId)
                .orElse(0D);
    }

    Double getUserAverageRate(Long userId) {
        return rateRepository.getUserAverageRate(userId)
                .orElse(0D);
    }

    RateResponse getResponseById(Long id) {
        return responseMapper.rateToResponse(getById(id));
    }

    RateResponse createRate(RateRequest request) {
        validateCreateRequest(request);
        var createdRate = rateRepository.save(createNewEntityFromRequest(request));
        return responseMapper.rateToResponse(createdRate);
    }

    RateResponse editRate(Long id, RateRequest request) {
        validateEditInput(id, request);
        var editedRate = rateRepository.save(createEditedEntityFromRequest(request));
        return responseMapper.rateToResponse(editedRate);
    }

    void deleteRate(Long id) {
        rateRepository.delete(getById(id));
    }

    private Rate getById(Long id) {
        return rateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rate", "id", id));
    }

    private void validateEditInput(Long id, RateRequest request) {
        if (isIdNotPresentOrNotMatching(id, request)) {
            throw new PathNotMatchBodyException(id, request.getId());
        }
        if (!rateRepository.existsById(id)) {
            throw new ResourceNotFoundException("ComponentCategory", "id", id);
        }
    }

    private boolean isIdNotPresentOrNotMatching(Long pathId, RateRequest request) {
        return !isIdInRequest(request) || !request.getId().equals(pathId);
    }

    private void validateCreateRequest(RateRequest request) {
        if (isIdInRequest(request)) {
            throw new ResourceCreateException(request.getId());
        }
    }

    private boolean isIdInRequest(RateRequest request) {
        return Objects.nonNull(request.getId());
    }

    private Rate createNewEntityFromRequest(RateRequest request) {
        return new Rate(
                request.getId(),
                request.getValue(),
                null,
                userFacade.getById(request.getUserId()),
                recipeFacade.getById(request.getRecipeId())
        );
    }

    private Rate createEditedEntityFromRequest(RateRequest request) {
        var rate = getById(request.getId());
        return new Rate(
                request.getId(),
                request.getValue(),
                rate.getDateCreated(),
                userFacade.getById(request.getUserId()),
                recipeFacade.getById(request.getRecipeId())
        );
    }
}
