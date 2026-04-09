package com.cms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.PlacementDrive;

public interface PlacementDriveRepository extends JpaRepository<PlacementDrive, Long> {
    List<PlacementDrive> findByCompanyId(Long companyId);
    List<PlacementDrive> findByAcademicYearId(Long academicYearId);
    List<PlacementDrive> findByStatus(String status);
}
