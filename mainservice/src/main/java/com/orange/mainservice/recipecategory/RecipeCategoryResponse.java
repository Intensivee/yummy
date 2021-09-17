package com.orange.mainservice.recipecategory;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
final class RecipeCategoryResponse {

    private final Long id;
    private final String name;
    private final String img;
}
