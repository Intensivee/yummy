package com.orange.mainservice.dto;

import com.orange.mainservice.entity.Ingredient;

public class IngredientDto {

    private Long id;
    private Double amount;
    private Ingredient.AmountType amountType;
    private String name;
    private Long componentId;

    public IngredientDto() {
    }

    public IngredientDto(Long id, Double amount, Ingredient.AmountType amountType, String name, Long componentId) {
        this.id = id;
        this.amount = amount;
        this.amountType = amountType;
        this.name = name;
        this.componentId = componentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Ingredient.AmountType getAmountType() {
        return amountType;
    }

    public void setAmountType(Ingredient.AmountType amountType) {
        this.amountType = amountType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getComponentId() {
        return componentId;
    }

    public void setComponentId(Long componentId) {
        this.componentId = componentId;
    }
}
