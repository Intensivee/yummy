package com.orange.mainservice.entity;

import javax.persistence.*;

@Entity(name = "Ingredient")
@Table(
        name = "ingredients",
        indexes = @Index( name = "ingredients_recipe_id_index", columnList = "recipe_id")
)
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "amount_type", nullable = false, columnDefinition = "amount_type")
    private AmountType amountType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "component_id", nullable = false)
    private Component component;

    public enum AmountType {
        PIECE, SLICE, CUBE, CLOVE, PINCH, SPOON, TEASPOON, G, ML, L, LEAVE
    }

    public Ingredient() {
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

    public AmountType getAmountType() {
        return amountType;
    }

    public void setAmountType(AmountType amountType) {
        this.amountType = amountType;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }
}
