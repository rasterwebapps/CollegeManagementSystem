package com.cms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cms.model.LabType;

public interface LabTypeRepository extends JpaRepository<LabType, Long> {
    Optional<LabType> findByName(String name);
    boolean existsByName(String name);
}
