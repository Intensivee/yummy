package com.orange.mainservice.entity;

import javax.persistence.*;

@Entity(name = "Direction")
@Table(
        name = "directions",
        uniqueConstraints = {
                @UniqueConstraint(name = "direction_order_unique", columnNames = {"recipe_id", "direction_order"})
        }
)
public class Direction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "direction_order", nullable = false)
    private Integer order;

    @Column(name = "description", nullable = false, columnDefinition = "VARCHAR(300)")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    public Direction() {
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

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
