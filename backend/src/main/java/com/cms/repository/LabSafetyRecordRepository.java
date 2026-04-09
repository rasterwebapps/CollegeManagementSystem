package com.cms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.LabSafetyRecord;

public interface LabSafetyRecordRepository extends JpaRepository<LabSafetyRecord, Long> {
}
