package com.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.AnalyticsSnapshot;

public interface AnalyticsSnapshotRepository extends JpaRepository<AnalyticsSnapshot, Long> {
}
