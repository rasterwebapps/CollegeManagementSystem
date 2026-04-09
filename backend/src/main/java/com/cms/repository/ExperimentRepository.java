package com.cms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cms.model.Experiment;

public interface ExperimentRepository extends JpaRepository<Experiment, Long> {
    List<Experiment> findBySyllabusId(Long syllabusId);
}
