package com.cms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.LeaveType;

public interface LeaveTypeRepository extends JpaRepository<LeaveType, Long> {
}
