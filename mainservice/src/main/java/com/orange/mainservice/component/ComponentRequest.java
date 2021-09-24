package com.orange.mainservice.component;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@AllArgsConstructor
final class ComponentRequest {

    private final Long id;
    @Size(max = 30)
    private final String name;
    private final Boolean isAccepted;
    private final Boolean isReviewed;
    private final Set<Long> categoriesIds;
}
