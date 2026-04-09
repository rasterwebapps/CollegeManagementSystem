package com.cms.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.TheoryAttendance;

public interface TheoryAttendanceRepository extends JpaRepository<TheoryAttendance, Long> {
    List<TheoryAttendance> findByStudentId(Long studentId);
    List<TheoryAttendance> findByStudentIdAndCourseId(Long studentId, Long courseId);
    List<TheoryAttendance> findByStudentIdAndSemesterId(Long studentId, Long semesterId);
    List<TheoryAttendance> findByCourseIdAndAttendanceDate(Long courseId, LocalDate date);
}
