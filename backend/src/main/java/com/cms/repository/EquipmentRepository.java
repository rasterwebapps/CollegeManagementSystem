package com.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cms.model.Equipment;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
}
