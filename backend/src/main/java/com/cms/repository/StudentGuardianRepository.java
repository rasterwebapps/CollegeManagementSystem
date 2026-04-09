package com.cms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.StudentGuardian;

public interface StudentGuardianRepository extends JpaRepository<StudentGuardian, Long> {
    List<StudentGuardian> findByStudentId(Long studentId);
}
