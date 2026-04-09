package com.cms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cms.model.ExperimentOutcomeMapping;

public interface ExperimentOutcomeMappingRepository extends JpaRepository<ExperimentOutcomeMapping, Long> {
    List<ExperimentOutcomeMapping> findByExperimentId(Long experimentId);
    List<ExperimentOutcomeMapping> findByCourseOutcomeId(Long courseOutcomeId);
}
