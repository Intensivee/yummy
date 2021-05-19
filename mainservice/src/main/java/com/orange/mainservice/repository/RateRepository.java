package com.orange.mainservice.repository;

import com.orange.mainservice.entity.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {

    @Query(
            value = "SELECT avg(r.value) FROM rates r WHERE r.recipe_id=?",
            nativeQuery = true
    )
    Optional<Double> getRecipeAverageRate(Long recipeId);

    @Query(
            value = "SELECT avg(r.value) FROM rates r JOIN recipes rr ON r.recipe_id = rr.id WHERE rr.user_id=?",
            nativeQuery = true
    )
    Optional<Double> getUserAverageRate(Long userId);
}
