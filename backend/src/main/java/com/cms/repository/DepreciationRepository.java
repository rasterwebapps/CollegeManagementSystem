package com.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cms.model.Depreciation;

public interface DepreciationRepository extends JpaRepository<Depreciation, Long> {
}
