package com.cms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cms.model.LabTimetable;

public interface LabTimetableRepository extends JpaRepository<LabTimetable, Long> {
    List<LabTimetable> findByLabIdAndDayOfWeek(Long labId, String dayOfWeek);
    List<LabTimetable> findByFacultyIdAndDayOfWeek(Long facultyId, String dayOfWeek);
    List<LabTimetable> findBySemesterId(Long semesterId);
}
