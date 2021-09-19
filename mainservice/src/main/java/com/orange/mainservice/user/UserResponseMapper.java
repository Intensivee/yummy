package com.orange.mainservice.user;

import com.orange.mainservice.rate.RateFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class UserResponseMapper {

    @Lazy
    private final RateFacade rateFacade;

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
