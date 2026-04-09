package com.cms.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.Grievance;

public interface GrievanceRepository extends JpaRepository<Grievance, Long> {
    Optional<Grievance> findByTicketNumber(String ticketNumber);
    List<Grievance> findByComplainantKeycloakId(String keycloakId);
    List<Grievance> findByStatus(String status);
    List<Grievance> findByCategory(String category);
    List<Grievance> findByAssignedTo(String assignedTo);
}
