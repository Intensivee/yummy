package com.orange.mainservice.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public final class RecipeCategoryRequest {

    private final Long categoryId;
    @NotBlank
    @Size(max = 25)
    private final String name;
    private final String img;
}
