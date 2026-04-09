package com.cms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.DepartmentBudget;

public interface DepartmentBudgetRepository extends JpaRepository<DepartmentBudget, Long> {
}
