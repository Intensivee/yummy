package com.orange.mainservice.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class ComponentResponse {

    private final Long componentId;
    private final String name;
}
