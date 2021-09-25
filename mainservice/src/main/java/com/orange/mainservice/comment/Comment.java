package com.orange.mainservice.comment;

import com.orange.mainservice.recipe.Recipe;
import com.orange.mainservice.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "Comment")
@Table(
        name = "comments",
        indexes = @Index( name = "comments_recipe_id_index", columnList = "recipe_id")
)
@Getter
@AllArgsConstructor
public final class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "body", nullable = false, columnDefinition = "VARCHAR(300)")
    private String body;

    @CreationTimestamp
    @Column(name = "date_created", nullable = false)
    private Date dateCreated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    protected Comment() {
    }

    public Comment(String body, User user, Recipe recipe) {
        this.body = body;
        this.user = user;
        this.recipe = recipe;
    }
}
