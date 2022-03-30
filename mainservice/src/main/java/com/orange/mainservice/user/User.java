package com.orange.mainservice.user;

import com.orange.mainservice.comment.Comment;
import com.orange.mainservice.rate.Rate;
import com.orange.mainservice.recipe.Recipe;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity(name = "User")
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = "user_email_unique", columnNames = "email"),
                @UniqueConstraint(name = "user_username_unique", columnNames = "username")
        },
        indexes = @Index( name = "users_username_index", columnList = "username", unique = true)
)
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "email", nullable = false, columnDefinition = "VARCHAR(100)")
    private String email;

    @Column(name = "username", nullable = false, columnDefinition = "VARCHAR(20)")
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "bio", columnDefinition = "VARCHAR(250)")
    private String bio;

    @Column(name = "img_url", columnDefinition = "VARCHAR(255)")
    private String imgUrl;

    @CreationTimestamp
    @Column(name = "date_created", nullable = false)
    private Date dateCreated;

    @Enumerated(EnumType.STRING)
    @Type(type = "com.orange.mainservice.util.EnumTypePostgreSql")
    @Column(name = "role", nullable = false, columnDefinition = "user_role_type")
    private UserRole userRole;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Recipe> recipes;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Comment> comments;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Rate> rates;
}
