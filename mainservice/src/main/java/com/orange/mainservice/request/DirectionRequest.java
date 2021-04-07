package com.orange.mainservice.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.*;

@Getter
@AllArgsConstructor
public final class DirectionRequest {

    private final Long id;
    @NotNull
    @Min(1)
    private final Integer order;
    @NotBlank
    @Size(max=300)
    private final String description;
    @NotNull
    private final Long recipeId;
}
