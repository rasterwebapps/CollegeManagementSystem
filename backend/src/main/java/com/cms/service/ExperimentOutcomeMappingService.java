package com.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cms.dto.ExperimentOutcomeMappingRequest;
import com.cms.dto.ExperimentOutcomeMappingResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.CourseOutcome;
import com.cms.model.Experiment;
import com.cms.model.ExperimentOutcomeMapping;
import com.cms.repository.CourseOutcomeRepository;
import com.cms.repository.ExperimentOutcomeMappingRepository;
import com.cms.repository.ExperimentRepository;

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

    public List<ExperimentOutcomeMappingResponse> findAll() {
        return mappingRepository.findAll().stream()
            .map(this::toResponse)
            .toList();
    }

    public ExperimentOutcomeMappingResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    public ExperimentOutcomeMappingResponse create(ExperimentOutcomeMappingRequest request) {
        Experiment experiment = experimentRepository.findById(request.experimentId())
            .orElseThrow(() -> new ResourceNotFoundException("Experiment", request.experimentId()));
        CourseOutcome courseOutcome = courseOutcomeRepository.findById(request.courseOutcomeId())
            .orElseThrow(() -> new ResourceNotFoundException("CourseOutcome", request.courseOutcomeId()));
        ExperimentOutcomeMapping mapping = new ExperimentOutcomeMapping();
        mapping.setExperiment(experiment);
        mapping.setCourseOutcome(courseOutcome);
        return toResponse(mappingRepository.save(mapping));
    }

    public void delete(Long id) {
        if (!mappingRepository.existsById(id)) {
            throw new ResourceNotFoundException("ExperimentOutcomeMapping", id);
        }
        mappingRepository.deleteById(id);
    }

    private ExperimentOutcomeMapping getOrThrow(Long id) {
        return mappingRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("ExperimentOutcomeMapping", id));
    }

    private ExperimentOutcomeMappingResponse toResponse(ExperimentOutcomeMapping m) {
        return new ExperimentOutcomeMappingResponse(
            m.getId(), m.getExperiment().getId(), m.getExperiment().getTitle(),
            m.getCourseOutcome().getId(), m.getCourseOutcome().getCode(),
            m.getCreatedAt()
        );
    }
}
