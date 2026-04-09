package com.cms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cms.model.LabStaffAssignment;

public interface LabStaffAssignmentRepository extends JpaRepository<LabStaffAssignment, Long> {
    List<LabStaffAssignment> findByLabId(Long labId);
    List<LabStaffAssignment> findByStaffKeycloakId(String staffKeycloakId);
    List<LabStaffAssignment> findByLabIdAndActiveTrue(Long labId);
}
