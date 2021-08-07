package com.orange.mainservice.entity;

import com.orange.mainservice.comment.Comment;
import com.orange.mainservice.entity.enums.TimeType;
import com.orange.mainservice.rate.Rate;
import lombok.Getter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "Recipe")
@Table(
        name = "recipes",
        indexes = @Index( name = "recipes_user_id_index", columnList = "user_id")
)
@Getter
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Type(type = "com.orange.mainservice.entity.enums.EnumTypePostgreSql")
    @Column(name = "time_type", nullable = false, columnDefinition = "time_type")
    private TimeType timeType;

    @Column(name = "title", nullable = false, columnDefinition = "VARCHAR(40)")
    private String title;

    @Column(name = "img_url")
    private String imgUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<Comment> comments;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY,  cascade = CascadeType.REMOVE)
    private Set<Rate> rates;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY,  cascade = CascadeType.REMOVE)
    private Set<Direction> directions;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY,  cascade = CascadeType.REMOVE)
    private Set<Ingredient> ingredients;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "recipe_categories_recipes",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_category_id")
    )
    private Set<RecipeCategory> categories;

    public Recipe() {
    }

    public Recipe(Long id, TimeType timeType, String title, String imgUrl, User user, Set<RecipeCategory> categories) {
        this.id = id;
        this.timeType = timeType;
        this.title = title;
        this.imgUrl = imgUrl;
        this.user = user;
        this.categories = categories;
    }
}
