package com.cms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.FeedbackResponse;

public interface FeedbackResponseRepository extends JpaRepository<FeedbackResponse, Long> {
}
