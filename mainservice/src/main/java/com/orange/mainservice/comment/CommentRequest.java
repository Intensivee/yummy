package com.orange.mainservice.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
final class CommentRequest {

    private final Long id;
    @NotBlank
    @Size(max = 300)
    private final String body;
    @NotNull
    private final Long recipeId;
}
