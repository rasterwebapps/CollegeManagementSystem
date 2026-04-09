package com.cms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.FacultyQualification;

public interface FacultyQualificationRepository extends JpaRepository<FacultyQualification, Long> {
    List<FacultyQualification> findByFacultyId(Long facultyId);
}
