package com.orange.mainservice.recipe;

import com.orange.mainservice.entity.enums.TimeType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@AllArgsConstructor
final class RecipeRequest {

    private final Long id;
    @NotNull
    private final TimeType timeType;
    @NotBlank
    @Size(max = 40)
    private final String title;
    private final String img;
    @NotNull
    private final Long userId; // TODO: userId from session
    @NotEmpty
    private final Set<Long> categoriesIds;
}
