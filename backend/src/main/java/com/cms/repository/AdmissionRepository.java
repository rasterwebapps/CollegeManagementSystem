package com.cms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cms.model.Admission;

public interface AdmissionRepository extends JpaRepository<Admission, Long> {
    Optional<Admission> findByApplicationNumber(String applicationNumber);
    boolean existsByApplicationNumber(String applicationNumber);
    List<Admission> findByStudentId(Long studentId);
}
