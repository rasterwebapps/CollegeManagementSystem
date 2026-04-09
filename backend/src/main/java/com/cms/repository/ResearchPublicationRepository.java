package com.cms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.ResearchPublication;

public interface ResearchPublicationRepository extends JpaRepository<ResearchPublication, Long> {
    List<ResearchPublication> findByFacultyId(Long facultyId);
}
