package com.cms.repository;

import com.cms.model.Experiment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExperimentRepository extends JpaRepository<Experiment, Long> {

    List<Experiment> findByCourseIdOrderBySequenceOrderAsc(Long courseId);
}
