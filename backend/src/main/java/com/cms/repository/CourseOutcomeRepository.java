package com.cms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cms.model.CourseOutcome;

public interface CourseOutcomeRepository extends JpaRepository<CourseOutcome, Long> {
    List<CourseOutcome> findBySyllabusId(Long syllabusId);
}
