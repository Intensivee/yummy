package com.orange.mainservice.component;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;


interface ComponentRepository extends JpaRepository<Component, Long> {

    Optional<Component> getById(Long id);

    Set<Component> findAllByCategories_Id(Long id);

    @Query("select c.name From Component c ORDER BY c.name")
    LinkedHashSet<String> findAllNamesOrdered();
}
