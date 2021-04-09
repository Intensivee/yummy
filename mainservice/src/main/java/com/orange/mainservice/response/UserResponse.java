package com.orange.mainservice.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public final class UserResponse {

    private final Long id;
    private final String email;
    private final String username;
    private final String firstName;
    private final String lastName;
    private final String img;
    private final Date dateCreated;
}
