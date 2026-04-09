package com.cms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.Backlog;

public interface BacklogRepository extends JpaRepository<Backlog, Long> {
    List<Backlog> findByStudentId(Long studentId);
    List<Backlog> findByStudentIdAndStatus(Long studentId, String status);
}
