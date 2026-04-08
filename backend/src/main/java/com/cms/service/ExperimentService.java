package com.cms.service;

import com.cms.dto.ExperimentRequest;
import com.cms.dto.ExperimentResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Course;
import com.cms.model.Experiment;
import com.cms.repository.CourseRepository;
import com.cms.repository.ExperimentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExperimentService {

    private final ExperimentRepository experimentRepository;
    private final CourseRepository courseRepository;

    public ExperimentService(ExperimentRepository experimentRepository,
                             CourseRepository courseRepository) {
        this.experimentRepository = experimentRepository;
        this.courseRepository = courseRepository;
    }

    @Transactional(readOnly = true)
    public List<ExperimentResponse> findByCourseId(Long courseId) {
        return experimentRepository.findByCourseIdOrderBySequenceOrderAsc(courseId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ExperimentResponse findById(Long id) {
        Experiment experiment = experimentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Experiment", id));
        return toResponse(experiment);
    }

    @Transactional
    public ExperimentResponse create(ExperimentRequest request) {
        Course course = courseRepository.findById(request.courseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", request.courseId()));

        Experiment experiment = new Experiment();
        experiment.setCourse(course);
        experiment.setName(request.name());
        experiment.setDescription(request.description());
        experiment.setObjective(request.objective());
        experiment.setProcedure(request.procedureText());
        experiment.setPreRequirements(request.preRequirements());
        experiment.setSequenceOrder(request.sequenceOrder() != null ? request.sequenceOrder() : 0);
        Experiment saved = experimentRepository.save(experiment);
        return toResponse(saved);
    }

    @Transactional
    public ExperimentResponse update(Long id, ExperimentRequest request) {
        Experiment experiment = experimentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Experiment", id));
        Course course = courseRepository.findById(request.courseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", request.courseId()));

        experiment.setCourse(course);
        experiment.setName(request.name());
        experiment.setDescription(request.description());
        experiment.setObjective(request.objective());
        experiment.setProcedure(request.procedureText());
        experiment.setPreRequirements(request.preRequirements());
        if (request.sequenceOrder() != null) {
            experiment.setSequenceOrder(request.sequenceOrder());
        }
        Experiment saved = experimentRepository.save(experiment);
        return toResponse(saved);
    }

    @Transactional
    public void delete(Long id) {
        Experiment experiment = experimentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Experiment", id));
        experimentRepository.delete(experiment);
    }

    private ExperimentResponse toResponse(Experiment entity) {
        return new ExperimentResponse(
                entity.getId(),
                entity.getCourse().getId(),
                entity.getCourse().getName(),
                entity.getName(),
                entity.getDescription(),
                entity.getObjective(),
                entity.getProcedure(),
                entity.getPreRequirements(),
                entity.getSequenceOrder()
        );
    }
}
