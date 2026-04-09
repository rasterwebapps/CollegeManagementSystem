package com.cms.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.HostelAllocation;

public interface HostelAllocationRepository extends JpaRepository<HostelAllocation, Long> {
    List<HostelAllocation> findByStudentId(Long studentId);
    List<HostelAllocation> findByRoomId(Long roomId);
    Optional<HostelAllocation> findByStudentIdAndStatus(Long studentId, String status);
}
