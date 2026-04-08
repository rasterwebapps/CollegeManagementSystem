package com.cms.repository;

import com.cms.model.Syllabus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SyllabusRepository extends JpaRepository<Syllabus, Long> {

    Optional<Syllabus> findByCourseIdAndAcademicYearId(Long courseId, Long academicYearId);

    List<Syllabus> findByCourseId(Long courseId);
}
