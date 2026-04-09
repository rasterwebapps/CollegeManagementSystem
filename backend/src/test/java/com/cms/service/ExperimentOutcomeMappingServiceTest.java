package com.cms.service;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cms.dto.ExperimentOutcomeMappingRequest;
import com.cms.dto.ExperimentOutcomeMappingResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.CourseOutcome;
import com.cms.model.Experiment;
import com.cms.model.ExperimentOutcomeMapping;
import com.cms.repository.CourseOutcomeRepository;
import com.cms.repository.ExperimentOutcomeMappingRepository;
import com.cms.repository.ExperimentRepository;

@ExtendWith(MockitoExtension.class)
class ExperimentOutcomeMappingServiceTest {

    @Mock private ExperimentOutcomeMappingRepository mappingRepo;
    @Mock private ExperimentRepository expRepo;
    @Mock private CourseOutcomeRepository coRepo;
    @InjectMocks private ExperimentOutcomeMappingService service;

    private ExperimentOutcomeMapping mapping;
    private Experiment experiment;
    private CourseOutcome co;

    @BeforeEach
    void setUp() {
        experiment = new Experiment();
        experiment.setId(1L);
        experiment.setTitle("Linked List");

        co = new CourseOutcome();
        co.setId(1L);
        co.setCode("CO1");

        mapping = new ExperimentOutcomeMapping();
        mapping.setId(1L);
        mapping.setExperiment(experiment);
        mapping.setCourseOutcome(co);
    }

    @Test void findAll() {
        when(mappingRepo.findAll()).thenReturn(List.of(mapping));
        assertThat(service.findAll()).hasSize(1);
    }

    @Test void findById_found() {
        when(mappingRepo.findById(1L)).thenReturn(Optional.of(mapping));
        ExperimentOutcomeMappingResponse r = service.findById(1L);
        assertThat(r.experimentTitle()).isEqualTo("Linked List");
        assertThat(r.courseOutcomeCode()).isEqualTo("CO1");
    }

    @Test void findById_notFound() {
        when(mappingRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(99L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void create_success() {
        ExperimentOutcomeMappingRequest req = new ExperimentOutcomeMappingRequest(1L, 1L);
        when(expRepo.findById(1L)).thenReturn(Optional.of(experiment));
        when(coRepo.findById(1L)).thenReturn(Optional.of(co));
        when(mappingRepo.save(any())).thenReturn(mapping);
        assertThat(service.create(req).experimentId()).isEqualTo(1L);
    }

    @Test void create_experimentNotFound() {
        ExperimentOutcomeMappingRequest req = new ExperimentOutcomeMappingRequest(99L, 1L);
        when(expRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void create_courseOutcomeNotFound() {
        ExperimentOutcomeMappingRequest req = new ExperimentOutcomeMappingRequest(1L, 99L);
        when(expRepo.findById(1L)).thenReturn(Optional.of(experiment));
        when(coRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void delete_success() {
        when(mappingRepo.existsById(1L)).thenReturn(true);
        service.delete(1L);
        verify(mappingRepo).deleteById(1L);
    }

    @Test void delete_notFound() {
        when(mappingRepo.existsById(99L)).thenReturn(false);
        assertThatThrownBy(() -> service.delete(99L)).isInstanceOf(ResourceNotFoundException.class);
    }
}
