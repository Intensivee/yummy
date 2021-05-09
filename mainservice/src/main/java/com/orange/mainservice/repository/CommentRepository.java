package com.orange.mainservice.repository;

import com.orange.mainservice.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashSet;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "SELECT DISTINCT c FROM Comment c WHERE c.recipe.id=?1 ORDER BY c.dateCreated DESC")
    LinkedHashSet<Comment> findByRecipeIdOrdered(Long id);
}
