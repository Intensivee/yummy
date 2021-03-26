package com.orange.mainservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class DirectionDto {

    private Long id;
    private Integer order;
    private String description;
    private Long recipeId;

    public DirectionDto() {
    }

    public DirectionDto(Long id, Integer order, String description, Long recipeId) {
        this.id = id;
        this.order = order;
        this.description = description;
        this.recipeId = recipeId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonIgnore
    public Long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }
}
