package com.cms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.CampusFacility;

public interface CampusFacilityRepository extends JpaRepository<CampusFacility, Long> {
}
