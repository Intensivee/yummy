package com.orange.mainservice.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
final class UserRequest {

    private final String img;
    private final String bio;
}
