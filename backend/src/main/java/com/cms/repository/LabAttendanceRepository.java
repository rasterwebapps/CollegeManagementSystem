package com.cms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cms.model.LabAttendance;

public interface LabAttendanceRepository extends JpaRepository<LabAttendance, Long> {
    List<LabAttendance> findByStudentId(Long studentId);
    List<LabAttendance> findByLabTimetableId(Long labTimetableId);
    List<LabAttendance> findByExperimentId(Long experimentId);
}
