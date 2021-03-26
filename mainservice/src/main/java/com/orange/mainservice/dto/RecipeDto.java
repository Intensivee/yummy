package com.orange.mainservice.dto;

import com.orange.mainservice.entity.Recipe;

public class RecipeDto {

    private Long id;
    private Recipe.TimeType timeType;
    private String title;
    private String imgUrl;
    private Double avgRate;
    private Long userId;

    public RecipeDto() {
    }

    public RecipeDto(Long id, Recipe.TimeType timeType, String title, String imgUrl, Double avgRate, Long userId) {
        this.id = id;
        this.timeType = timeType;
        this.title = title;
        this.imgUrl = imgUrl;
        this.avgRate = avgRate;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Recipe.TimeType getTimeType() {
        return timeType;
    }

    public void setTimeType(Recipe.TimeType timeType) {
        this.timeType = timeType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Double getAvgRate() {
        return avgRate;
    }

    public void setAvgRate(Double avgRate) {
        this.avgRate = avgRate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
