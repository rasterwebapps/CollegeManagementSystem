package com.cms.service;

import com.cms.dto.ExperimentOutcomeMappingRequest;
import com.cms.dto.ExperimentOutcomeMappingResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.CourseOutcome;
import com.cms.model.Experiment;
import com.cms.model.ExperimentOutcomeMapping;
import com.cms.repository.CourseOutcomeRepository;
import com.cms.repository.ExperimentOutcomeMappingRepository;
import com.cms.repository.ExperimentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExperimentOutcomeMappingService {

    private final ExperimentOutcomeMappingRepository mappingRepository;
    private final ExperimentRepository experimentRepository;
    private final CourseOutcomeRepository courseOutcomeRepository;

    public ExperimentOutcomeMappingService(ExperimentOutcomeMappingRepository mappingRepository,
                                           ExperimentRepository experimentRepository,
                                           CourseOutcomeRepository courseOutcomeRepository) {
        this.mappingRepository = mappingRepository;
        this.experimentRepository = experimentRepository;
        this.courseOutcomeRepository = courseOutcomeRepository;
    }

    @Transactional(readOnly = true)
    public List<ExperimentOutcomeMappingResponse> findByExperimentId(Long experimentId) {
        return mappingRepository.findByExperimentId(experimentId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public ExperimentOutcomeMappingResponse create(ExperimentOutcomeMappingRequest request) {
        Experiment experiment = experimentRepository.findById(request.experimentId())
                .orElseThrow(() -> new ResourceNotFoundException("Experiment", request.experimentId()));
        CourseOutcome courseOutcome = courseOutcomeRepository.findById(request.courseOutcomeId())
                .orElseThrow(() -> new ResourceNotFoundException("CourseOutcome", request.courseOutcomeId()));

        ExperimentOutcomeMapping mapping = new ExperimentOutcomeMapping();
        mapping.setExperiment(experiment);
        mapping.setCourseOutcome(courseOutcome);
        mapping.setMappingLevel(request.mappingLevel());
        ExperimentOutcomeMapping saved = mappingRepository.save(mapping);
        return toResponse(saved);
    }

    @Transactional
    public void delete(Long experimentId, Long courseOutcomeId) {
        mappingRepository.deleteByExperimentIdAndCourseOutcomeId(experimentId, courseOutcomeId);
    }

    private ExperimentOutcomeMappingResponse toResponse(ExperimentOutcomeMapping entity) {
        return new ExperimentOutcomeMappingResponse(
                entity.getExperiment().getId(),
                entity.getExperiment().getName(),
                entity.getCourseOutcome().getId(),
                entity.getCourseOutcome().getCode(),
                entity.getMappingLevel()
        );
    }
}
