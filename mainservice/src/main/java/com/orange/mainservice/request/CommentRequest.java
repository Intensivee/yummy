package com.orange.mainservice.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class CommentRequest {

    private final Long commentId;
    private final String body;
    private final Long userId;
    private final Long recipeId;
}
