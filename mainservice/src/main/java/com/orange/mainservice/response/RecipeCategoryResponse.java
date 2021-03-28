package com.orange.mainservice.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RecipeCategoryResponse {

    private Long categoryId;
    private String name;
    private String img;
}
