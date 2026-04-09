package com.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cms.dto.ExperimentRequest;
import com.cms.dto.ExperimentResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Experiment;
import com.cms.model.LabType;
import com.cms.model.Syllabus;
import com.cms.repository.ExperimentRepository;
import com.cms.repository.LabTypeRepository;
import com.cms.repository.SyllabusRepository;

@Service
public class ExperimentService {

    private final ExperimentRepository experimentRepository;
    private final SyllabusRepository syllabusRepository;
    private final LabTypeRepository labTypeRepository;

    public ExperimentService(ExperimentRepository experimentRepository, SyllabusRepository syllabusRepository,
                             LabTypeRepository labTypeRepository) {
        this.experimentRepository = experimentRepository;
        this.syllabusRepository = syllabusRepository;
        this.labTypeRepository = labTypeRepository;
    }

    public List<ExperimentResponse> findAll() {
        return experimentRepository.findAll().stream()
            .map(this::toResponse)
            .toList();
    }

    public ExperimentResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    public ExperimentResponse create(ExperimentRequest request) {
        Syllabus syllabus = syllabusRepository.findById(request.syllabusId())
            .orElseThrow(() -> new ResourceNotFoundException("Syllabus", request.syllabusId()));
        Experiment experiment = new Experiment();
        experiment.setSyllabus(syllabus);
        experiment.setExperimentNumber(request.experimentNumber());
        experiment.setTitle(request.title());
        experiment.setDescription(request.description());
        if (request.labTypeId() != null) {
            LabType labType = labTypeRepository.findById(request.labTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("LabType", request.labTypeId()));
            experiment.setLabType(labType);
        }
        experiment.setDurationHours(request.durationHours() != null ? request.durationHours() : 2);
        return toResponse(experimentRepository.save(experiment));
    }

    public ExperimentResponse update(Long id, ExperimentRequest request) {
        Experiment experiment = getOrThrow(id);
        Syllabus syllabus = syllabusRepository.findById(request.syllabusId())
            .orElseThrow(() -> new ResourceNotFoundException("Syllabus", request.syllabusId()));
        experiment.setSyllabus(syllabus);
        experiment.setExperimentNumber(request.experimentNumber());
        experiment.setTitle(request.title());
        experiment.setDescription(request.description());
        if (request.labTypeId() != null) {
            LabType labType = labTypeRepository.findById(request.labTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("LabType", request.labTypeId()));
            experiment.setLabType(labType);
        } else {
            experiment.setLabType(null);
        }
        if (request.durationHours() != null) {
            experiment.setDurationHours(request.durationHours());
        }
        return toResponse(experimentRepository.save(experiment));
    }

    public void delete(Long id) {
        if (!experimentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Experiment", id);
        }
        experimentRepository.deleteById(id);
    }

    private Experiment getOrThrow(Long id) {
        return experimentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Experiment", id));
    }

    private ExperimentResponse toResponse(Experiment e) {
        return new ExperimentResponse(
            e.getId(), e.getSyllabus().getId(), e.getExperimentNumber(),
            e.getTitle(), e.getDescription(),
            e.getLabType() != null ? e.getLabType().getId() : null,
            e.getLabType() != null ? e.getLabType().getName() : null,
            e.getDurationHours(), e.getCreatedAt(), e.getUpdatedAt()
        );
    }
}
