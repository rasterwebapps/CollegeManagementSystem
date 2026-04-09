package com.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.ContinuousEvaluation;

public interface ContinuousEvaluationRepository extends JpaRepository<ContinuousEvaluation, Long> {
}
