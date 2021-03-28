package com.orange.mainservice.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DirectionResponse {

    private Long directionId;
    private Integer order;
    private String description;
}
