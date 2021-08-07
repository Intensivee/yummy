package com.orange.mainservice.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
final class CommentResponse {

    private final Long id;
    private final String body;
    private final Date dateCreated;
    private final String username;
    private final String userImg;
}
