package com.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cms.model.Scholarship;

public interface ScholarshipRepository extends JpaRepository<Scholarship, Long> {
}
