package com.orange.mainservice.component;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;


interface ComponentRepository extends JpaRepository<Component, Long> {

    Optional<Component> findByName(String name);

    Set<Component> findAllByCategories_Id(Long id);

    @Query("select c.name From Component c Where c.isAccepted=true ORDER BY c.name")
    List<String> findAllAcceptedNamesOrdered();
}
