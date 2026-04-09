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

import com.cms.dto.CourseOutcomeRequest;
import com.cms.dto.CourseOutcomeResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.CourseOutcome;
import com.cms.model.Syllabus;
import com.cms.repository.CourseOutcomeRepository;
import com.cms.repository.SyllabusRepository;

@ExtendWith(MockitoExtension.class)
class CourseOutcomeServiceTest {

    @Mock private CourseOutcomeRepository coRepo;
    @Mock private SyllabusRepository syllabusRepo;
    @InjectMocks private CourseOutcomeService service;

    private CourseOutcome co;
    private Syllabus syllabus;

    @BeforeEach
    void setUp() {
        syllabus = new Syllabus();
        syllabus.setId(1L);

        co = new CourseOutcome();
        co.setId(1L);
        co.setSyllabus(syllabus);
        co.setCode("CO1");
        co.setDescription("Understand DS");
        co.setBloomLevel("UNDERSTAND");
    }

    @Test void findAll() {
        when(coRepo.findAll()).thenReturn(List.of(co));
        assertThat(service.findAll()).hasSize(1);
    }

    @Test void findById_found() {
        when(coRepo.findById(1L)).thenReturn(Optional.of(co));
        assertThat(service.findById(1L).code()).isEqualTo("CO1");
    }

    @Test void findById_notFound() {
        when(coRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(99L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void create_success() {
        CourseOutcomeRequest req = new CourseOutcomeRequest(1L, "CO1", "Desc", "UNDERSTAND");
        when(syllabusRepo.findById(1L)).thenReturn(Optional.of(syllabus));
        when(coRepo.save(any())).thenReturn(co);
        assertThat(service.create(req).code()).isEqualTo("CO1");
    }

    @Test void create_syllabusNotFound() {
        CourseOutcomeRequest req = new CourseOutcomeRequest(99L, "CO1", "Desc", "UNDERSTAND");
        when(syllabusRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void update_success() {
        CourseOutcomeRequest req = new CourseOutcomeRequest(1L, "CO2", "Updated", "APPLY");
        when(coRepo.findById(1L)).thenReturn(Optional.of(co));
        when(syllabusRepo.findById(1L)).thenReturn(Optional.of(syllabus));
        when(coRepo.save(any())).thenReturn(co);
        assertThat(service.update(1L, req)).isNotNull();
    }

    @Test void update_notFound() {
        CourseOutcomeRequest req = new CourseOutcomeRequest(1L, "CO2", "Updated", "APPLY");
        when(coRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(99L, req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void update_syllabusNotFound() {
        CourseOutcomeRequest req = new CourseOutcomeRequest(99L, "CO2", "Updated", "APPLY");
        when(coRepo.findById(1L)).thenReturn(Optional.of(co));
        when(syllabusRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(1L, req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void delete_success() {
        when(coRepo.existsById(1L)).thenReturn(true);
        service.delete(1L);
        verify(coRepo).deleteById(1L);
    }

    @Test void delete_notFound() {
        when(coRepo.existsById(99L)).thenReturn(false);
        assertThatThrownBy(() -> service.delete(99L)).isInstanceOf(ResourceNotFoundException.class);
    }
}
