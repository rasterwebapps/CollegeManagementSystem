package com.cms.repository;

import com.cms.model.FacultyLabAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacultyLabAssignmentRepository extends JpaRepository<FacultyLabAssignment, Long> {

    List<FacultyLabAssignment> findByFacultyId(Long facultyId);

    List<FacultyLabAssignment> findByLabId(Long labId);
}
