package com.orange.mainservice.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class UserResponse {

    private final Long userId;
    private final String email;
    private final String username;
    private final String firstName;
    private final String lastName;
    private final String img;
}
