package com.orange.mainservice.mapper.response;

import com.orange.mainservice.response.ComponentResponse;
import com.orange.mainservice.entity.Component;

@org.springframework.stereotype.Component
public class ComponentResponseMapper {

    public ComponentResponse componentToResponse(Component component){
        return new ComponentResponse(
                component.getId(),
                component.getName()
        );
    }
}
