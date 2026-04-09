package com.cms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.FeeInstallment;

public interface FeeInstallmentRepository extends JpaRepository<FeeInstallment, Long> {
}
