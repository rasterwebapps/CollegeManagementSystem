package com.cms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cms.model.StudentProfile;

public interface StudentProfileRepository extends JpaRepository<StudentProfile, Long> {
    Optional<StudentProfile> findByKeycloakId(String keycloakId);
    Optional<StudentProfile> findByEnrollmentNumber(String enrollmentNumber);
    Optional<StudentProfile> findByEmail(String email);
    boolean existsByEnrollmentNumber(String enrollmentNumber);
    boolean existsByEmail(String email);
    List<StudentProfile> findByDepartmentId(Long departmentId);
}
