package com.orange.mainservice.componentcategory;

import org.springframework.stereotype.Component;

@Component
class ComponentCategoryResponseMapper {

    ComponentCategoryResponse categoryToResponse(ComponentCategory category) {
        return new ComponentCategoryResponse(
                category.getId(),
                category.getName()
        );
    }
}
