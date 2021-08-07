package com.orange.mainservice.component;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
final class ComponentResponse {

    private final Long id;
    private final String name;
}
