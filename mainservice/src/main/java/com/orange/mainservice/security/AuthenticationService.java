package com.orange.mainservice.security;

import com.orange.mainservice.exception.AuthenticationException;
import com.orange.mainservice.exception.RegistrationException;
import com.orange.mainservice.user.User;
import com.orange.mainservice.user.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
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

@Service
class AuthenticationService implements UserDetailsService {


    private final UserFacade userFacade;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationService(@Lazy PasswordEncoder passwordEncoder, UserFacade userFacade,
                                 @Lazy AuthenticationManager authenticationManager) {
        this.userFacade = userFacade;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

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

    User registerUser(RegisterRequest registerRequest) {
        userFacade.findByEmail(registerRequest.getEmail())
                .ifPresent(user -> {
                    throw new RegistrationException(String.format(
                            "Email: %s already exists.", registerRequest.getEmail()
                    ));
                });

        return userFacade.save(createUserFromRegisterRequest(registerRequest));
    }

    private User createUserFromRegisterRequest(RegisterRequest registerRequest) {
        var user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setEmail(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        return user;
    }
}
