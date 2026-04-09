package com.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cms.model.ConsumableStock;

public interface ConsumableStockRepository extends JpaRepository<ConsumableStock, Long> {
}
