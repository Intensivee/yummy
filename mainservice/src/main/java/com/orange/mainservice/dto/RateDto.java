package com.orange.mainservice.dto;

public class RateDto {

    private Integer value;
    private Long userId;
    private Long recipeId;

    public RateDto() {
    }

    public RateDto(Integer value, Long userId, Long recipeId) {
        this.value = value;
        this.userId = userId;
        this.recipeId = recipeId;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }
}
