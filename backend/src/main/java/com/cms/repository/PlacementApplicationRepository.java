package com.cms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.PlacementApplication;

public interface PlacementApplicationRepository extends JpaRepository<PlacementApplication, Long> {
    List<PlacementApplication> findByStudentId(Long studentId);
    List<PlacementApplication> findByDriveId(Long driveId);
}
