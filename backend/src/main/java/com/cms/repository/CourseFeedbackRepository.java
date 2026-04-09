package com.cms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.CourseFeedback;

public interface CourseFeedbackRepository extends JpaRepository<CourseFeedback, Long> {
}
