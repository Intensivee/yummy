package com.orange.mainservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "Recipe")
@Table(
        name = "recipes",
        indexes = @Index( name = "recipes_user_id_index", columnList = "user_id")
)
@Getter
@AllArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "time_type", nullable = false, columnDefinition = "time_type")
    private TimeType timeType;

    @Column(name = "title", nullable = false, columnDefinition = "VARCHAR(40)")
    private String title;

    @Column(name = "img_url")
    private String imgUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY)
    private Set<Comment> comments;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY)
    private Set<Rate> rates;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY)
    private Set<Direction> directions;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY)
    private Set<Ingredient> ingredients;

    @ManyToMany(mappedBy = "recipes", fetch = FetchType.LAZY)
    private Set<RecipeCategory> categories;

    public enum TimeType {
        TIME_1, TIME_2, TIME_3
    }

    protected Recipe() {
    }
}
