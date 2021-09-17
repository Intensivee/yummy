package com.orange.mainservice.direction;

import org.springframework.stereotype.Component;

@Component
class DirectionResponseMapper {

    DirectionResponse directionToDto(Direction direction) {
        return new DirectionResponse(
                direction.getId(),
                direction.getOrder(),
                direction.getDescription()
        );
    }
}
