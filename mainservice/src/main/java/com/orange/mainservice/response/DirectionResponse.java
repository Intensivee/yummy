package com.orange.mainservice.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class DirectionResponse {

    private final Long id;
    private final Integer order;
    private final String description;
}
