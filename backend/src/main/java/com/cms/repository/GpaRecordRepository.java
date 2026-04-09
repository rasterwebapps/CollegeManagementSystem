package com.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.GpaRecord;

public interface GpaRecordRepository extends JpaRepository<GpaRecord, Long> {
}
