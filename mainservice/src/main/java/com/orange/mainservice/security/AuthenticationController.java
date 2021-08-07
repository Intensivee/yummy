package com.orange.mainservice.security;

import com.orange.mainservice.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/authentication")
@RequiredArgsConstructor
class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final JwtTokenUtil tokenUtil;
    private final JwtTokenConfig tokenConfig;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> authenticateCredentials(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationService.authenticateCredentials(loginRequest);

        String token = tokenUtil.createToken(authentication);

        return ResponseEntity.ok(new TokenResponse(tokenConfig.getTokenPrefix() + token));
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        User user = authenticationService.registerUser(registerRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/users/{id}")
                .buildAndExpand(user.getId())
                .toUri();

        return ResponseEntity.created(location).body(user.getId());
    }
}
