package com.cms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.Payroll;

public interface PayrollRepository extends JpaRepository<Payroll, Long> {
}
