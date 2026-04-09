package com.cms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cms.model.Lab;

public interface LabRepository extends JpaRepository<Lab, Long> {
    Optional<Lab> findByCode(String code);
    boolean existsByCode(String code);
    List<Lab> findByDepartmentId(Long departmentId);
    List<Lab> findByLabTypeId(Long labTypeId);
}
