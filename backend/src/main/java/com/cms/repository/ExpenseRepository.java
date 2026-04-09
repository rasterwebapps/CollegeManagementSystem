package com.cms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
