package com.orange.mainservice.mapper.response;

import com.orange.mainservice.response.DirectionResponse;
import com.orange.mainservice.entity.Direction;
import org.springframework.stereotype.Component;

@Component
public class DirectionResponseMapper {

    public DirectionResponse directionToDto(Direction direction){
        return new DirectionResponse(
                direction.getId(),
                direction.getOrder(),
                direction.getDescription()
        );
    }
}
