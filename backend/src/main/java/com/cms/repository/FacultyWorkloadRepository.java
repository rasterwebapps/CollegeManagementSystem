package com.cms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.FacultyWorkload;

public interface FacultyWorkloadRepository extends JpaRepository<FacultyWorkload, Long> {
}
