package com.cms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.ExamSchedule;

public interface ExamScheduleRepository extends JpaRepository<ExamSchedule, Long> {
    List<ExamSchedule> findBySemesterId(Long semesterId);
    List<ExamSchedule> findByCourseId(Long courseId);
    List<ExamSchedule> findBySemesterIdAndExamType(Long semesterId, String examType);
}
