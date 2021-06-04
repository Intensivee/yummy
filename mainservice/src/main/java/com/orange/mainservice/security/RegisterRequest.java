package com.orange.mainservice.security;


import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public final class RegisterRequest {

    @Size(min = 3, max = 15, message = "username must be between 3 and 15 characters")
    private final String username;

    @Email(message = "Provided email is not valid.")
    private final String email;

    @Size(min = 5, max = 100, message = "Password must be between 5 and 100 characters")
    private final String password;
}
