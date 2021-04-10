package com.orange.mainservice.mapper.response;

import com.orange.mainservice.entity.User;
import com.orange.mainservice.response.UserResponse;
import com.orange.mainservice.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class UserResponseMapper {

    private final RateService rateService;

    @Autowired
    public UserResponseMapper(@Lazy RateService rateService) {
        this.rateService = rateService;
    }

    public UserResponse userToResponse(User user){
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getBio(),
                user.getImgUrl(),
                user.getDateCreated(),
                rateService.getUserAvgRate(user.getId())
        );
    }
}
