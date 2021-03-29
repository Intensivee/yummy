package com.orange.mainservice.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class RecipeCategoryRequest {

    private final Long categoryId;
    private final String name;
    private final String img;
}
