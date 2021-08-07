package com.orange.mainservice.security;

import com.orange.mainservice.entity.User;
import com.orange.mainservice.exception.RegistrationException;
import com.orange.mainservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
class AuthenticationService implements UserDetailsService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationService(@Lazy PasswordEncoder passwordEncoder, UserRepository userRepository,
                                 @Lazy AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(user -> new JwtUserDetails(
                        user.getId(),
                        user.getUsername(),
                        user.getPassword(),
                        Collections.singleton(new SimpleGrantedAuthority(user.getUserRole().toString())
                        )))
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with Username %s not found", username)));
    }


    Authentication authenticateCredentials(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    User registerUser(RegisterRequest registerRequest) {
        userRepository.findByEmail(registerRequest.getEmail())
                .ifPresent(user -> {
                    throw new RegistrationException(String.format(
                            "Email: %s already exists.", registerRequest.getEmail()
                    ));
                });

        User user = createUserFromRegisterRequest(registerRequest);
        return userRepository.save(user);
    }

    private User createUserFromRegisterRequest(RegisterRequest registerRequest) {
        var user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setEmail(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        return user;
    }
}
