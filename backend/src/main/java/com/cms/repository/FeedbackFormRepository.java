package com.cms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.FeedbackForm;

public interface FeedbackFormRepository extends JpaRepository<FeedbackForm, Long> {
}
