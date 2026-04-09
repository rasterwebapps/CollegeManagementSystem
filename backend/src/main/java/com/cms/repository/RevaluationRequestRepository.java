package com.cms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.RevaluationRequest;

public interface RevaluationRequestRepository extends JpaRepository<RevaluationRequest, Long> {
}
