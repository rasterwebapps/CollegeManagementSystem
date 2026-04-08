package com.cms.repository;

import com.cms.model.Lab;
import com.cms.model.enums.LabStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabRepository extends JpaRepository<Lab, Long> {

    List<Lab> findByDepartmentId(Long departmentId);

    List<Lab> findByLabTypeId(Long labTypeId);

    List<Lab> findByActiveTrue();

    long countByStatus(LabStatus status);

    long countByActiveTrue();
}
