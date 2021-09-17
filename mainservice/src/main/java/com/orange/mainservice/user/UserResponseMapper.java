package com.orange.mainservice.user;

import com.orange.mainservice.rate.RateFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
class UserResponseMapper {

    private final RateFacade rateFacade;

    @Autowired
    public UserResponseMapper(@Lazy RateFacade rateFacade) {
        this.rateFacade = rateFacade;
    }

    UserResponse userToResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getBio(),
                user.getImgUrl(),
                user.getDateCreated(),
                rateFacade.getUserAverageRate(user.getId())
        );
    }
}
