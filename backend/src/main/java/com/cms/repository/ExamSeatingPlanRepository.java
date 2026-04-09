package com.cms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.ExamSeatingPlan;

public interface ExamSeatingPlanRepository extends JpaRepository<ExamSeatingPlan, Long> {
}
