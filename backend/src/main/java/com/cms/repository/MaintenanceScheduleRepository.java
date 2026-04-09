package com.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cms.model.MaintenanceSchedule;

public interface MaintenanceScheduleRepository extends JpaRepository<MaintenanceSchedule, Long> {
}
