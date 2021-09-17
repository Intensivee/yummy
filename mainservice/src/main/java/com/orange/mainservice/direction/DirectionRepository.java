package com.orange.mainservice.direction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.LinkedHashSet;


interface DirectionRepository extends JpaRepository<Direction, Long> {

    @Query(value = "SELECT DISTINCT d FROM Direction d WHERE d.recipe.id=?1 ORDER BY d.order")
    LinkedHashSet<Direction> findByRecipeIdOrdered(Long id);
}
