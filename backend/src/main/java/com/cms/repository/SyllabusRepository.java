package com.cms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cms.model.Syllabus;

public interface SyllabusRepository extends JpaRepository<Syllabus, Long> {
    List<Syllabus> findByCourseId(Long courseId);
    List<Syllabus> findByAcademicYearId(Long academicYearId);
}
