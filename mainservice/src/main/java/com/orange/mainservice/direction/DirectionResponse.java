package com.orange.mainservice.direction;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
final class DirectionResponse {

    private final Long id;
    private final Integer order;
    private final String description;
}
