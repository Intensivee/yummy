package com.orange.mainservice.direction;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
final class DirectionRequest {

    private final Long id;
    @NotNull
    @Min(1)
    private final Integer order;
    @NotBlank
    @Size(max = 300)
    private final String description;
    @NotNull
    private final Long recipeId;
}
