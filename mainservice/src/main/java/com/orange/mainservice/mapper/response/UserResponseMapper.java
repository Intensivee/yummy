package com.orange.mainservice.mapper.response;

import com.orange.mainservice.entity.User;
import com.orange.mainservice.rate.RateFacade;
import com.orange.mainservice.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class UserResponseMapper {

    private final RateFacade rateFacade;

    @Autowired
    public UserResponseMapper(@Lazy RateFacade rateFacade) {
        this.rateFacade = rateFacade;
    }

    public UserResponse userToResponse(User user) {
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
