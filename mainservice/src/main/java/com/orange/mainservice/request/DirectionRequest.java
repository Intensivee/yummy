package com.orange.mainservice.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class DirectionRequest {

    private final Long directionId;
    private final Integer order;
    private final String description;
    private final Long recipeId;
}
