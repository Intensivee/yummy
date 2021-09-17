package com.orange.mainservice.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;

    public User getById(Long id) {
        return userService.getById(id);
    }

    public User save(User user) {
        return userService.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userService.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userService.findByEmail(email);
    }
}
