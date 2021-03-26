package com.orange.mainservice.repository;

import com.orange.mainservice.entity.Component;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComponentRepository extends JpaRepository<Component, Long> {

    Optional<Component> getById(Long id);
}
