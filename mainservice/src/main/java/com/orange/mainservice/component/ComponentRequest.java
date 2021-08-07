package com.orange.mainservice.component;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@AllArgsConstructor
final class ComponentRequest {

    private final Long id;
    @NotBlank
    @Size(max = 30)
    private final String name;
    @NotNull
    private final Boolean isAccepted;
    private final Set<Long> categoriesIds;
}
