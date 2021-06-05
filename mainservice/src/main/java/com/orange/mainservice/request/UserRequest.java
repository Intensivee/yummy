package com.orange.mainservice.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class UserRequest {

    private final String img;
    private final String bio;
}
