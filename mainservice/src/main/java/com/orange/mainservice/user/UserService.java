package com.orange.mainservice.user;

import com.orange.mainservice.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
class UserService {

    private final UserRepository userRepository;
    private final UserResponseMapper responseMapper;

    User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    UserResponse getResponseById(Long id) {
        return responseMapper.userToResponse(getById(id));
    }

    UserResponse getResponseByUsername(String username) {
        return responseMapper.userToResponse(findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username)));
    }

    UserResponse patchUser(String username, UserRequest userRequest) {
        User user = findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        if (userRequest.getBio() != null) {
            user.setBio(userRequest.getBio());
        }
        if (userRequest.getImg() != null) {
            user.setImgUrl(userRequest.getImg());
        }
        return responseMapper.userToResponse(save(user));
    }

    User save(User user) {
        return userRepository.save(user);
    }

    Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
