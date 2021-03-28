package com.orange.mainservice.mapper.response;

import com.orange.mainservice.entity.ComponentCategory;
import com.orange.mainservice.response.ComponentCategoryResponse;
import org.springframework.stereotype.Component;

@Component
public class ComponentCategoryResponseMapper {

    public ComponentCategoryResponse categoryToResponse(ComponentCategory category){
        return new ComponentCategoryResponse(
                category.getId(),
                category.getName()
        );
    }
}
