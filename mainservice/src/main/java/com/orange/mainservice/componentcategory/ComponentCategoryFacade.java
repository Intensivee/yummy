package com.orange.mainservice.componentcategory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ComponentCategoryFacade {

    private final ComponentCategoryService componentCategoryService;

    public ComponentCategory getById(Long id) {
        return componentCategoryService.getById(id);
    }
}
