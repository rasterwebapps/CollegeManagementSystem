package com.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.AccreditationReport;

public interface AccreditationReportRepository extends JpaRepository<AccreditationReport, Long> {
}
