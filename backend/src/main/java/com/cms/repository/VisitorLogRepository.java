package com.cms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.VisitorLog;

public interface VisitorLogRepository extends JpaRepository<VisitorLog, Long> {
}
