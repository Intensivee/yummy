package com.orange.mainservice.ingredient;

import com.orange.mainservice.component.Component;
import com.orange.mainservice.entity.enums.AmountType;
import com.orange.mainservice.recipe.Recipe;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity(name = "Ingredient")
@Table(
        name = "ingredients",
        indexes = @Index( name = "ingredients_recipe_id_index", columnList = "recipe_id")
)
@Getter
@AllArgsConstructor
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Enumerated(EnumType.STRING)
    @Type(type = "com.orange.mainservice.entity.enums.EnumTypePostgreSql")
    @Column(name = "amount_type", nullable = false, columnDefinition = "amount_type")
    private AmountType amountType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "component_id", nullable = false)
    private Component component;

    protected Ingredient() {
    }
}