package com.orange.mainservice.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public final class CommentRequest {

    private final Long commentId;
    @NotBlank
    @Size(max=300)
    private final String body;
    @NotNull
    private final Long userId;
    @NotNull
    private final Long recipeId;
}
