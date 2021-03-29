package com.orange.mainservice.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
public final class ComponentRequest {

    private final Long componentId;
    private final String name;
    private final Boolean isAccepted;
    private final Set<Long> categoryIds;
}
