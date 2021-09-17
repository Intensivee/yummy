package com.orange.mainservice.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
final class LoginRequest {

    @NotBlank(message = "Provided email is not valid.")
    private final String username;

    @NotBlank(message = "Password must be specified!")
    private final String password;
}
