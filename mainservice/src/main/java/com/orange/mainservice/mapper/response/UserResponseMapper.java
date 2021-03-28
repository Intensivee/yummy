package com.orange.mainservice.mapper.response;

import com.orange.mainservice.entity.User;
import com.orange.mainservice.response.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserResponseMapper {

    public UserResponse userToResponse(User user){
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getImgUrl()
        );
    }
}
