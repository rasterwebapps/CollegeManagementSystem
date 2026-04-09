package com.cms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.LabReservation;

public interface LabReservationRepository extends JpaRepository<LabReservation, Long> {
}
