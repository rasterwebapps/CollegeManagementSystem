package com.cms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}
