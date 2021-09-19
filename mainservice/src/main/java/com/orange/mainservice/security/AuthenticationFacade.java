package com.orange.mainservice.security;

import com.orange.mainservice.exception.AuthenticationException;
import com.orange.mainservice.user.User;
import com.orange.mainservice.user.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationFacade {

    private final UserFacade userFacade;

    public User getCurrentUser() {
        String username = getAuthentication().getName();
        return userFacade.findByUsername(username)
                .orElseThrow(() -> new AuthenticationException(
                        String.format("User with username %s not found", username)));
    }

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
