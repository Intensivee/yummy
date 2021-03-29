package com.orange.mainservice.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class ComponentCategoryRequest {

    private final Long categoryId;
    private final String name;
}
