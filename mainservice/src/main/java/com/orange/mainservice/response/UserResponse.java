package com.orange.mainservice.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponse {

    private Long userId;
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private String img;
}
