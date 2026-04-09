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

import com.cms.dto.SyllabusRequest;
import com.cms.dto.SyllabusResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.AcademicYear;
import com.cms.model.Course;
import com.cms.model.Program;
import com.cms.model.Syllabus;
import com.cms.repository.AcademicYearRepository;
import com.cms.repository.CourseRepository;
import com.cms.repository.SyllabusRepository;

@ExtendWith(MockitoExtension.class)
class SyllabusServiceTest {

    @Mock private SyllabusRepository syllabusRepo;
    @Mock private CourseRepository courseRepo;
    @Mock private AcademicYearRepository ayRepo;
    @InjectMocks private SyllabusService service;

    private Syllabus syllabus;
    private Course course;
    private AcademicYear ay;

    @BeforeEach
    void setUp() {
        Program program = new Program();
        program.setId(1L);
        program.setName("B.Tech CS");

        course = new Course();
        course.setId(1L);
        course.setName("Data Structures");
        course.setProgram(program);

        ay = new AcademicYear();
        ay.setId(1L);
        ay.setName("2024-2025");

        syllabus = new Syllabus();
        syllabus.setId(1L);
        syllabus.setCourse(course);
        syllabus.setAcademicYear(ay);
        syllabus.setContent("Content");
        syllabus.setObjectives("Objectives");
        syllabus.setStatus("DRAFT");
    }

    @Test void findAll() {
        when(syllabusRepo.findAll()).thenReturn(List.of(syllabus));
        assertThat(service.findAll()).hasSize(1);
    }

    @Test void findById_found() {
        when(syllabusRepo.findById(1L)).thenReturn(Optional.of(syllabus));
        assertThat(service.findById(1L).courseName()).isEqualTo("Data Structures");
    }

    @Test void findById_notFound() {
        when(syllabusRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(99L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void create_success() {
        SyllabusRequest req = new SyllabusRequest(1L, 1L, "Content", "Obj", "ACTIVE");
        when(courseRepo.findById(1L)).thenReturn(Optional.of(course));
        when(ayRepo.findById(1L)).thenReturn(Optional.of(ay));
        when(syllabusRepo.save(any())).thenReturn(syllabus);
        assertThat(service.create(req).status()).isEqualTo("DRAFT");
    }

    @Test void create_nullStatus_defaultsDraft() {
        SyllabusRequest req = new SyllabusRequest(1L, 1L, "Content", "Obj", null);
        when(courseRepo.findById(1L)).thenReturn(Optional.of(course));
        when(ayRepo.findById(1L)).thenReturn(Optional.of(ay));
        when(syllabusRepo.save(any())).thenReturn(syllabus);
        service.create(req);
        verify(syllabusRepo).save(any());
    }

    @Test void create_courseNotFound() {
        SyllabusRequest req = new SyllabusRequest(99L, 1L, "Content", "Obj", null);
        when(courseRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void create_ayNotFound() {
        SyllabusRequest req = new SyllabusRequest(1L, 99L, "Content", "Obj", null);
        when(courseRepo.findById(1L)).thenReturn(Optional.of(course));
        when(ayRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void update_success() {
        SyllabusRequest req = new SyllabusRequest(1L, 1L, "Updated", "Obj2", "ACTIVE");
        when(syllabusRepo.findById(1L)).thenReturn(Optional.of(syllabus));
        when(courseRepo.findById(1L)).thenReturn(Optional.of(course));
        when(ayRepo.findById(1L)).thenReturn(Optional.of(ay));
        when(syllabusRepo.save(any())).thenReturn(syllabus);
        assertThat(service.update(1L, req)).isNotNull();
    }

    @Test void update_nullStatus_keepsCurrent() {
        SyllabusRequest req = new SyllabusRequest(1L, 1L, "Updated", "Obj2", null);
        when(syllabusRepo.findById(1L)).thenReturn(Optional.of(syllabus));
        when(courseRepo.findById(1L)).thenReturn(Optional.of(course));
        when(ayRepo.findById(1L)).thenReturn(Optional.of(ay));
        when(syllabusRepo.save(any())).thenReturn(syllabus);
        service.update(1L, req);
        assertThat(syllabus.getStatus()).isEqualTo("DRAFT");
    }

    @Test void update_notFound() {
        SyllabusRequest req = new SyllabusRequest(1L, 1L, "Updated", "Obj2", null);
        when(syllabusRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(99L, req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void delete_success() {
        when(syllabusRepo.existsById(1L)).thenReturn(true);
        service.delete(1L);
        verify(syllabusRepo).deleteById(1L);
    }

    @Test void delete_notFound() {
        when(syllabusRepo.existsById(99L)).thenReturn(false);
        assertThatThrownBy(() -> service.delete(99L)).isInstanceOf(ResourceNotFoundException.class);
    }
}
