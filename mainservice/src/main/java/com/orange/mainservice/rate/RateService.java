package com.orange.mainservice.rate;

import com.orange.mainservice.exception.ResourceNotFoundException;
import com.orange.mainservice.recipe.RecipeFacade;
import com.orange.mainservice.security.AuthenticationFacade;
import com.orange.mainservice.user.UserFacade;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class RateService {

    private final RateRepository rateRepository;
    private final UserFacade userFacade;
    private final RecipeFacade recipeFacade;
    private final AuthenticationFacade authenticationFacade;

    Double getRecipeAverageRate(Long recipeId) {
        return rateRepository.getRecipeAverageRate(recipeId)
                .orElse(0D);
    }

    Double getUserAverageRate(Long userId) {
        return rateRepository.getUserAverageRate(userId)
                .orElse(0D);
    }

    Double crateOrEditRate(RateRequest request) {
        Long userId = authenticationFacade.getCurrentUser().getId();
        rateRepository.findByUser_idAndRecipe_id(userId, request.getRecipeId())
                .ifPresentOrElse(rate -> {
                            rate.setValue(request.getValue());
                            rateRepository.save(rate);
                        },
                        () -> rateRepository.save(createNewEntityFromRequest(request)));
        return getRecipeAverageRate(request.getRecipeId());
    }

    void deleteRate(Long id) {
        rateRepository.delete(getById(id));
    }

    private Rate getById(Long id) {
        return rateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rate", "id", id));
    }

    private Rate createNewEntityFromRequest(RateRequest request) {
        return new Rate(
                request.getValue(),
                authenticationFacade.getCurrentUser(),
                recipeFacade.getById(request.getRecipeId())
        );
    }
}
