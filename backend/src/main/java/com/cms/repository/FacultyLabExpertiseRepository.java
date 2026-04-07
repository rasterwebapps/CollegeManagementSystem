package com.cms.repository;

import com.cms.model.FacultyLabExpertise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacultyLabExpertiseRepository extends JpaRepository<FacultyLabExpertise, Long> {

    List<FacultyLabExpertise> findByFacultyId(Long facultyId);
}
