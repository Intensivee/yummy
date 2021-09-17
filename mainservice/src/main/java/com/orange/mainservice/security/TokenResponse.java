package com.orange.mainservice.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
final class TokenResponse {

    private final String token;
}
