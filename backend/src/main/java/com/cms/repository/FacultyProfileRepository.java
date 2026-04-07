package com.cms.repository;

import com.cms.model.FacultyProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FacultyProfileRepository extends JpaRepository<FacultyProfile, Long> {

    Optional<FacultyProfile> findByKeycloakUserId(String keycloakUserId);

    Optional<FacultyProfile> findByEmployeeId(String employeeId);

    boolean existsByKeycloakUserId(String keycloakUserId);

    boolean existsByEmployeeId(String employeeId);

    List<FacultyProfile> findByDepartmentId(Long departmentId);

    List<FacultyProfile> findByActiveTrue();
}
