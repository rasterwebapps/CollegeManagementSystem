package com.cms.repository;

import com.cms.model.LabType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabTypeRepository extends JpaRepository<LabType, Long> {

    boolean existsByName(String name);
}
