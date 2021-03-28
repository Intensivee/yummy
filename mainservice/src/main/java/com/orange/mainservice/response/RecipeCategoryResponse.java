package com.orange.mainservice.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class RecipeCategoryResponse {

    private final Long categoryId;
    private final String name;
    private final String img;
}
