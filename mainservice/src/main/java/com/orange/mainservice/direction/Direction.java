package com.orange.mainservice.direction;

import com.orange.mainservice.recipe.Recipe;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.*;

@Entity(name = "Direction")
@Table(
        name = "directions",
        uniqueConstraints = {
                @UniqueConstraint(name = "direction_order_unique", columnNames = {"recipe_id", "direction_order"})
        },
        indexes = @Index( name = "directions_recipe_id_index", columnList = "recipe_id")
)
@Getter
@AllArgsConstructor
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

    protected Direction() {
    }

    public Direction(Integer order, String description, Recipe recipe) {
        this.order = order;
        this.description = description;
        this.recipe = recipe;
    }
}
