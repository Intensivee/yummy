package com.orange.mainservice.recipe;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class RecipePdfRequest {

    private final boolean image;
    private final boolean socialSection;
    private final boolean directionsSection;
    private final boolean ingredientsSection;
}
