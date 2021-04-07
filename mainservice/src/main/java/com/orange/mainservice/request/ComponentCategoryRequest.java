package com.orange.mainservice.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public final class ComponentCategoryRequest {

    private final Long id;
    @NotBlank
    @Size(max=25)
    private final String name;
}
