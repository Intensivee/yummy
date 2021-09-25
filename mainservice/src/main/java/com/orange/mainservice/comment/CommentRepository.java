package com.orange.mainservice.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "SELECT DISTINCT c FROM Comment c WHERE c.recipe.id=?1 ORDER BY c.dateCreated DESC")
    List<Comment> findByRecipeIdOrdered(Long id);
}
