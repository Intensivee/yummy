package com.orange.mainservice.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RateResponse {

    private Long rateId;
    private Integer value;
    private Long userId;
    private Long recipeId;
}
