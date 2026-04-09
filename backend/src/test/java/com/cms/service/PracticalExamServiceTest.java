package com.cms.service;

import java.math.BigDecimal;
import java.time.LocalDate;
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

import com.cms.dto.PracticalExamRequest;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Course;
import com.cms.model.Experiment;
import com.cms.model.Lab;
import com.cms.model.PracticalExam;
import com.cms.model.Semester;
import com.cms.repository.CourseRepository;
import com.cms.repository.ExperimentRepository;
import com.cms.repository.LabRepository;
import com.cms.repository.PracticalExamRepository;
import com.cms.repository.SemesterRepository;

@ExtendWith(MockitoExtension.class)
class PracticalExamServiceTest {

    @Mock private PracticalExamRepository practicalExamRepo;
    @Mock private CourseRepository courseRepo;
    @Mock private SemesterRepository semesterRepo;
    @Mock private ExperimentRepository experimentRepo;
    @Mock private LabRepository labRepo;
    @InjectMocks private PracticalExamService service;

    private PracticalExam exam;
    private Course course;
    private Semester semester;
    private Experiment experiment;
    private Lab lab;

    @BeforeEach
    void setUp() {
        course = new Course(); course.setId(1L); course.setName("Physics");
        semester = new Semester(); semester.setId(1L); semester.setName("Fall 2024");
        experiment = new Experiment(); experiment.setId(1L); experiment.setTitle("Ohm's Law");
        lab = new Lab(); lab.setId(1L); lab.setName("Physics Lab");
        exam = new PracticalExam();
        exam.setId(1L); exam.setCourse(course); exam.setSemester(semester);
        exam.setExperiment(experiment); exam.setLab(lab);
        exam.setExamDate(LocalDate.of(2024, 6, 15));
        exam.setStartTime("09:00"); exam.setEndTime("12:00");
        exam.setMaxMarks(new BigDecimal("100.00")); exam.setPassingMarks(new BigDecimal("40.00"));
        exam.setExaminer("Dr. Smith"); exam.setStatus("SCHEDULED");
    }

    @Test void findAll() {
        when(practicalExamRepo.findAll()).thenReturn(List.of(exam));
        assertThat(service.findAll()).hasSize(1);
    }

    @Test void findById_found() {
        when(practicalExamRepo.findById(1L)).thenReturn(Optional.of(exam));
        assertThat(service.findById(1L).examiner()).isEqualTo("Dr. Smith");
    }

    @Test void findById_notFound() {
        when(practicalExamRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(99L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void create_success() {
        PracticalExamRequest req = new PracticalExamRequest(1L, 1L, 1L, LocalDate.of(2024, 6, 15), "09:00", "12:00", 1L, new BigDecimal("100"), new BigDecimal("40"), "Dr. Smith", "SCHEDULED");
        when(courseRepo.findById(1L)).thenReturn(Optional.of(course));
        when(semesterRepo.findById(1L)).thenReturn(Optional.of(semester));
        when(experimentRepo.findById(1L)).thenReturn(Optional.of(experiment));
        when(labRepo.findById(1L)).thenReturn(Optional.of(lab));
        when(practicalExamRepo.save(any())).thenReturn(exam);
        assertThat(service.create(req).examiner()).isEqualTo("Dr. Smith");
    }

    @Test void create_nullOptionalFks() {
        PracticalExamRequest req = new PracticalExamRequest(1L, 1L, null, LocalDate.of(2024, 6, 15), null, null, null, new BigDecimal("100"), null, null, null);
        when(courseRepo.findById(1L)).thenReturn(Optional.of(course));
        when(semesterRepo.findById(1L)).thenReturn(Optional.of(semester));
        PracticalExam saved = new PracticalExam();
        saved.setId(2L); saved.setCourse(course); saved.setSemester(semester);
        saved.setExamDate(LocalDate.of(2024, 6, 15)); saved.setMaxMarks(new BigDecimal("100"));
        saved.setStatus("SCHEDULED");
        when(practicalExamRepo.save(any())).thenReturn(saved);
        assertThat(service.create(req).status()).isEqualTo("SCHEDULED");
    }

    @Test void create_courseNotFound() {
        PracticalExamRequest req = new PracticalExamRequest(99L, 1L, null, LocalDate.of(2024, 6, 15), null, null, null, new BigDecimal("100"), null, null, null);
        when(courseRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void create_semesterNotFound() {
        PracticalExamRequest req = new PracticalExamRequest(1L, 99L, null, LocalDate.of(2024, 6, 15), null, null, null, new BigDecimal("100"), null, null, null);
        when(courseRepo.findById(1L)).thenReturn(Optional.of(course));
        when(semesterRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void create_experimentNotFound() {
        PracticalExamRequest req = new PracticalExamRequest(1L, 1L, 99L, LocalDate.of(2024, 6, 15), null, null, null, new BigDecimal("100"), null, null, null);
        when(courseRepo.findById(1L)).thenReturn(Optional.of(course));
        when(semesterRepo.findById(1L)).thenReturn(Optional.of(semester));
        when(experimentRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void create_labNotFound() {
        PracticalExamRequest req = new PracticalExamRequest(1L, 1L, null, LocalDate.of(2024, 6, 15), null, null, 99L, new BigDecimal("100"), null, null, null);
        when(courseRepo.findById(1L)).thenReturn(Optional.of(course));
        when(semesterRepo.findById(1L)).thenReturn(Optional.of(semester));
        when(labRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void update_success() {
        PracticalExamRequest req = new PracticalExamRequest(1L, 1L, 1L, LocalDate.of(2024, 7, 1), "10:00", "13:00", 1L, new BigDecimal("100"), new BigDecimal("50"), "Dr. Jones", "COMPLETED");
        when(practicalExamRepo.findById(1L)).thenReturn(Optional.of(exam));
        when(courseRepo.findById(1L)).thenReturn(Optional.of(course));
        when(semesterRepo.findById(1L)).thenReturn(Optional.of(semester));
        when(experimentRepo.findById(1L)).thenReturn(Optional.of(experiment));
        when(labRepo.findById(1L)).thenReturn(Optional.of(lab));
        when(practicalExamRepo.save(any())).thenReturn(exam);
        assertThat(service.update(1L, req)).isNotNull();
    }

    @Test void update_nullStatus() {
        PracticalExamRequest req = new PracticalExamRequest(1L, 1L, null, LocalDate.of(2024, 7, 1), null, null, null, new BigDecimal("100"), null, null, null);
        when(practicalExamRepo.findById(1L)).thenReturn(Optional.of(exam));
        when(courseRepo.findById(1L)).thenReturn(Optional.of(course));
        when(semesterRepo.findById(1L)).thenReturn(Optional.of(semester));
        when(practicalExamRepo.save(any())).thenReturn(exam);
        service.update(1L, req);
        assertThat(exam.getStatus()).isEqualTo("SCHEDULED");
    }

    @Test void update_notFound() {
        PracticalExamRequest req = new PracticalExamRequest(1L, 1L, null, LocalDate.of(2024, 7, 1), null, null, null, new BigDecimal("100"), null, null, null);
        when(practicalExamRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(99L, req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void delete_success() {
        when(practicalExamRepo.existsById(1L)).thenReturn(true);
        service.delete(1L);
        verify(practicalExamRepo).deleteById(1L);
    }

    @Test void delete_notFound() {
        when(practicalExamRepo.existsById(99L)).thenReturn(false);
        assertThatThrownBy(() -> service.delete(99L)).isInstanceOf(ResourceNotFoundException.class);
    }
}
