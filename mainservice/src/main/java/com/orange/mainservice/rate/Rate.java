package com.orange.mainservice.rate;

import com.orange.mainservice.entity.Recipe;
import com.orange.mainservice.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "Rate")
@Table(
        name = "rates",
        uniqueConstraints = {
                @UniqueConstraint(name = "rate_user_recipe_unique", columnNames = {"user_id", "recipe_id"})
        },
        indexes = @Index(name = "rates_recipe_id_index", columnList = "recipe_id")
)
@Getter
@AllArgsConstructor
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "value", nullable = false)
    private Integer value;

    @CreationTimestamp
    @Column(name = "date_created", nullable = false)
    private Date dateCreated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    protected Rate() {
    }
}
