package com.orange.mainservice.recipecategory;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
final class RecipeCategoryRequest {

    private final Long id;
    @NotBlank
    @Size(max = 25)
    private final String name;
    private final String img;
}
