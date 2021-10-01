package com.orange.mainservice.security;

import com.orange.mainservice.exception.AuthenticationException;
import com.orange.mainservice.exception.RegistrationException;
import com.orange.mainservice.user.User;
import com.orange.mainservice.user.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

import static com.orange.mainservice.entity.enums.UserRole.ROLE_USER;

@Service
@RequiredArgsConstructor
class AuthenticationService implements UserDetailsService {


    private final UserFacade userFacade;
    @Lazy
    private final PasswordEncoder passwordEncoder;
    @Lazy
    private final AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String email) {
        return userFacade.findByEmail(email)
                .map(user -> new JwtUserDetails(
                        user.getId(),
                        user.getUsername(),
                        user.getPassword(),
                        Collections.singleton(new SimpleGrantedAuthority(user.getUserRole().toString())
                        )))
                .orElseThrow(() -> new AuthenticationException(String.format("User with email %s not found", email)));
    }


    Authentication authenticateCredentials(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    Authentication registerUser(RegisterRequest registerRequest) {
        validateUserNotExists(registerRequest);
        userFacade.save(createUserFromRegisterRequest(registerRequest));
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        registerRequest.getEmail(),
                        registerRequest.getPassword()
                )
        );
    }

    private void validateUserNotExists(RegisterRequest registerRequest) {
        userFacade.findByEmail(registerRequest.getEmail()).ifPresent(user -> {
            throw new RegistrationException(String.format("Email: %s already exists.", registerRequest.getEmail()));
        });
        userFacade.findByUsername(registerRequest.getUsername()).ifPresent(user -> {
            throw new RegistrationException(String.format("Username: %s already exists.", registerRequest.getUsername()));
        });
    }

    private User createUserFromRegisterRequest(RegisterRequest registerRequest) {
        var user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setUserRole(ROLE_USER);
        return user;
    }
}
