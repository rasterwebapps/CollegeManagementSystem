package com.cms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.StudentMark;

public interface StudentMarkRepository extends JpaRepository<StudentMark, Long> {
    List<StudentMark> findByStudentId(Long studentId);
    List<StudentMark> findByStudentIdAndSemesterId(Long studentId, Long semesterId);
    List<StudentMark> findByCourseIdAndSemesterId(Long courseId, Long semesterId);
    List<StudentMark> findByExamId(Long examId);
}
