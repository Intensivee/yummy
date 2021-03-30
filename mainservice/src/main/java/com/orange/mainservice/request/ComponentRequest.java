package com.orange.mainservice.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@AllArgsConstructor
public final class ComponentRequest {

    private final Long componentId;
    @NotBlank
    @Size(max=30)
    private final String name;
    @NotNull
    private final Boolean isAccepted;
    private final Set<Long> categoryIds;
}
