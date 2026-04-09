package com.cms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cms.model.FacultyProfile;

public interface FacultyProfileRepository extends JpaRepository<FacultyProfile, Long> {
    Optional<FacultyProfile> findByKeycloakId(String keycloakId);
    Optional<FacultyProfile> findByEmployeeCode(String employeeCode);
    Optional<FacultyProfile> findByEmail(String email);
    List<FacultyProfile> findByDepartmentId(Long departmentId);
}
