package com.orange.mainservice.service;

import com.orange.mainservice.entity.User;
import com.orange.mainservice.exception.ResourceNotFoundException;
import com.orange.mainservice.mapper.response.UserResponseMapper;
import com.orange.mainservice.repository.UserRepository;
import com.orange.mainservice.request.UserRequest;
import com.orange.mainservice.response.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserResponseMapper responseMapper;

    public UserResponse getResponseById(Long id) {
        return responseMapper.userToResponse(getById(id));
    }

    public UserResponse getResponseByUsername(String username) {
        return responseMapper.userToResponse(getByUsername(username));
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    public UserResponse patchUser(String username, UserRequest userRequest) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        if (userRequest.getBio() != null) {
            user.setBio(userRequest.getBio());
        }
        if (userRequest.getImg() != null) {
            user.setImgUrl(userRequest.getImg());
        }
        return responseMapper.userToResponse(userRepository.save(user));
    }

    private User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
    }
}
