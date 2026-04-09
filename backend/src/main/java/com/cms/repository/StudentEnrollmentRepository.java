package com.cms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cms.model.StudentEnrollment;

public interface StudentEnrollmentRepository extends JpaRepository<StudentEnrollment, Long> {
    List<StudentEnrollment> findByStudentId(Long studentId);
    List<StudentEnrollment> findByCourseId(Long courseId);
    List<StudentEnrollment> findBySemesterId(Long semesterId);
}
