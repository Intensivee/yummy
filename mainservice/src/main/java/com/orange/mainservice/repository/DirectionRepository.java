package com.orange.mainservice.repository;

import com.orange.mainservice.entity.Direction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashSet;

@Repository
public interface DirectionRepository extends JpaRepository<Direction, Long> {

    @Query(value = "SELECT DISTINCT d FROM Direction d WHERE d.recipe.id=?1 ORDER BY d.order")
    LinkedHashSet<Direction> findByRecipeIdOrdered(Long id);
}
