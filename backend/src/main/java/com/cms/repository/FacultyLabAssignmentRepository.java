package com.cms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cms.model.FacultyLabAssignment;

public interface FacultyLabAssignmentRepository extends JpaRepository<FacultyLabAssignment, Long> {
    List<FacultyLabAssignment> findByFacultyId(Long facultyId);
    List<FacultyLabAssignment> findByLabId(Long labId);
    List<FacultyLabAssignment> findBySemesterId(Long semesterId);
    List<FacultyLabAssignment> findByFacultyIdAndActiveTrue(Long facultyId);
}
