package com.orange.mainservice.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
        Authentication authentication = authenticationService.registerUser(registerRequest);

        String token = tokenUtil.createToken(authentication);

        return ResponseEntity.ok(new TokenResponse(tokenConfig.getTokenPrefix() + token));
    }
}
