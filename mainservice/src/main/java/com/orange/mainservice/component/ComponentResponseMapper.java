package com.orange.mainservice.component;

@org.springframework.stereotype.Component
class ComponentResponseMapper {

    ComponentResponse componentToResponse(Component component) {
        return new ComponentResponse(
                component.getId(),
                component.getName()
        );
    }
}
