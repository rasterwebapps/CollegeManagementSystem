package com.cms.repository;

import com.cms.model.ExperimentOutcomeMapping;
import com.cms.model.ExperimentOutcomeMappingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExperimentOutcomeMappingRepository extends JpaRepository<ExperimentOutcomeMapping, ExperimentOutcomeMappingId> {

    List<ExperimentOutcomeMapping> findByExperimentId(Long experimentId);

    List<ExperimentOutcomeMapping> findByCourseOutcomeId(Long courseOutcomeId);

    void deleteByExperimentIdAndCourseOutcomeId(Long experimentId, Long courseOutcomeId);
}
