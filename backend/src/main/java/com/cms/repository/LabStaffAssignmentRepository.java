package com.cms.repository;

import com.cms.model.LabStaffAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabStaffAssignmentRepository extends JpaRepository<LabStaffAssignment, Long> {

    List<LabStaffAssignment> findByLabId(Long labId);
}
