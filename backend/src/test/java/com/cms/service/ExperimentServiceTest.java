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

import com.cms.dto.ExperimentRequest;
import com.cms.dto.ExperimentResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Experiment;
import com.cms.model.LabType;
import com.cms.model.Syllabus;
import com.cms.repository.ExperimentRepository;
import com.cms.repository.LabTypeRepository;
import com.cms.repository.SyllabusRepository;

@ExtendWith(MockitoExtension.class)
class ExperimentServiceTest {

    @Mock private ExperimentRepository expRepo;
    @Mock private SyllabusRepository syllabusRepo;
    @Mock private LabTypeRepository labTypeRepo;
    @InjectMocks private ExperimentService service;

    private Experiment experiment;
    private Syllabus syllabus;
    private LabType labType;

    @BeforeEach
    void setUp() {
        syllabus = new Syllabus();
        syllabus.setId(1L);

        labType = new LabType();
        labType.setId(1L);
        labType.setName("Computer Lab");

        experiment = new Experiment();
        experiment.setId(1L);
        experiment.setSyllabus(syllabus);
        experiment.setExperimentNumber(1);
        experiment.setTitle("Linked List");
        experiment.setDescription("Implement linked list");
        experiment.setLabType(labType);
        experiment.setDurationHours(2);
    }

    @Test void findAll() {
        when(expRepo.findAll()).thenReturn(List.of(experiment));
        List<ExperimentResponse> result = service.findAll();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).labTypeName()).isEqualTo("Computer Lab");
    }

    @Test void findById_found() {
        when(expRepo.findById(1L)).thenReturn(Optional.of(experiment));
        assertThat(service.findById(1L).title()).isEqualTo("Linked List");
    }

    @Test void findById_notFound() {
        when(expRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(99L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void create_withLabType() {
        ExperimentRequest req = new ExperimentRequest(1L, 1, "Linked List", "Desc", 1L, 2);
        when(syllabusRepo.findById(1L)).thenReturn(Optional.of(syllabus));
        when(labTypeRepo.findById(1L)).thenReturn(Optional.of(labType));
        when(expRepo.save(any())).thenReturn(experiment);
        assertThat(service.create(req).title()).isEqualTo("Linked List");
    }

    @Test void create_withoutLabType() {
        Experiment noLt = new Experiment();
        noLt.setId(2L);
        noLt.setSyllabus(syllabus);
        noLt.setExperimentNumber(2);
        noLt.setTitle("Arrays");
        noLt.setDurationHours(2);

        ExperimentRequest req = new ExperimentRequest(1L, 2, "Arrays", "Desc", null, null);
        when(syllabusRepo.findById(1L)).thenReturn(Optional.of(syllabus));
        when(expRepo.save(any())).thenReturn(noLt);
        ExperimentResponse result = service.create(req);
        assertThat(result.labTypeId()).isNull();
    }

    @Test void create_syllabusNotFound() {
        ExperimentRequest req = new ExperimentRequest(99L, 1, "Linked List", "Desc", null, null);
        when(syllabusRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void create_labTypeNotFound() {
        ExperimentRequest req = new ExperimentRequest(1L, 1, "Linked List", "Desc", 99L, 2);
        when(syllabusRepo.findById(1L)).thenReturn(Optional.of(syllabus));
        when(labTypeRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void update_success() {
        ExperimentRequest req = new ExperimentRequest(1L, 1, "Updated", "New desc", 1L, 3);
        when(expRepo.findById(1L)).thenReturn(Optional.of(experiment));
        when(syllabusRepo.findById(1L)).thenReturn(Optional.of(syllabus));
        when(labTypeRepo.findById(1L)).thenReturn(Optional.of(labType));
        when(expRepo.save(any())).thenReturn(experiment);
        assertThat(service.update(1L, req)).isNotNull();
    }

    @Test void update_removeLabType() {
        ExperimentRequest req = new ExperimentRequest(1L, 1, "Updated", "New desc", null, null);
        when(expRepo.findById(1L)).thenReturn(Optional.of(experiment));
        when(syllabusRepo.findById(1L)).thenReturn(Optional.of(syllabus));
        when(expRepo.save(any())).thenReturn(experiment);
        service.update(1L, req);
        assertThat(experiment.getLabType()).isNull();
    }

    @Test void update_notFound() {
        ExperimentRequest req = new ExperimentRequest(1L, 1, "Updated", "Desc", null, null);
        when(expRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(99L, req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void delete_success() {
        when(expRepo.existsById(1L)).thenReturn(true);
        service.delete(1L);
        verify(expRepo).deleteById(1L);
    }

    @Test void delete_notFound() {
        when(expRepo.existsById(99L)).thenReturn(false);
        assertThatThrownBy(() -> service.delete(99L)).isInstanceOf(ResourceNotFoundException.class);
    }
}
