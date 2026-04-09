package com.cms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cms.model.AttendanceAlert;

public interface AttendanceAlertRepository extends JpaRepository<AttendanceAlert, Long> {
    List<AttendanceAlert> findByStudentId(Long studentId);
    List<AttendanceAlert> findByCourseId(Long courseId);
    List<AttendanceAlert> findByResolved(Boolean resolved);
}
