package com.cms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cms.model.FacultyLabExpertise;

public interface FacultyLabExpertiseRepository extends JpaRepository<FacultyLabExpertise, Long> {
    List<FacultyLabExpertise> findByFacultyId(Long facultyId);
    List<FacultyLabExpertise> findByLabTypeId(Long labTypeId);
}
