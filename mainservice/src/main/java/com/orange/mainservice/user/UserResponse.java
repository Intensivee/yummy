package com.orange.mainservice.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
final class UserResponse {

    private final Long id;
    private final String email;
    private final String username;
    private final String bio;
    private final String img;
    private final Date dateCreated;
    private final Double avgRate;
}
