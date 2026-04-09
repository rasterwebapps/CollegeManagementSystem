package com.cms.service;

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

import com.cms.dto.FacultyLabAssignmentRequest;
import com.cms.dto.FacultyLabAssignmentResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.AcademicYear;
import com.cms.model.FacultyLabAssignment;
import com.cms.model.FacultyProfile;
import com.cms.model.Lab;
import com.cms.model.Semester;
import com.cms.repository.FacultyLabAssignmentRepository;
import com.cms.repository.FacultyProfileRepository;
import com.cms.repository.LabRepository;
import com.cms.repository.SemesterRepository;

@ExtendWith(MockitoExtension.class)
class FacultyLabAssignmentServiceTest {

    @Mock private FacultyLabAssignmentRepository assignRepo;
    @Mock private FacultyProfileRepository fpRepo;
    @Mock private LabRepository labRepo;
    @Mock private SemesterRepository semRepo;
    @InjectMocks private FacultyLabAssignmentService service;

    private FacultyLabAssignment assignment;
    private FacultyProfile faculty;
    private Lab lab;
    private Semester semester;

    @BeforeEach
    void setUp() {
        faculty = new FacultyProfile();
        faculty.setId(1L);
        faculty.setFirstName("John");
        faculty.setLastName("Doe");

        lab = new Lab();
        lab.setId(1L);
        lab.setName("CS Lab 1");

        AcademicYear ay = new AcademicYear();
        ay.setId(1L);
        ay.setName("2024-2025");

        semester = new Semester();
        semester.setId(1L);
        semester.setName("Fall 2024");
        semester.setAcademicYear(ay);

        assignment = new FacultyLabAssignment();
        assignment.setId(1L);
        assignment.setFaculty(faculty);
        assignment.setLab(lab);
        assignment.setSemester(semester);
        assignment.setAssignedDate(LocalDate.of(2024, 8, 1));
        assignment.setActive(true);
    }

    @Test void findAll() {
        when(assignRepo.findAll()).thenReturn(List.of(assignment));
        List<FacultyLabAssignmentResponse> result = service.findAll();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).facultyName()).isEqualTo("John Doe");
    }

    @Test void findById_found() {
        when(assignRepo.findById(1L)).thenReturn(Optional.of(assignment));
        assertThat(service.findById(1L).labName()).isEqualTo("CS Lab 1");
    }

    @Test void findById_notFound() {
        when(assignRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(99L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void create_success() {
        FacultyLabAssignmentRequest req = new FacultyLabAssignmentRequest(1L, 1L, 1L, LocalDate.of(2024, 8, 1), true);
        when(fpRepo.findById(1L)).thenReturn(Optional.of(faculty));
        when(labRepo.findById(1L)).thenReturn(Optional.of(lab));
        when(semRepo.findById(1L)).thenReturn(Optional.of(semester));
        when(assignRepo.save(any())).thenReturn(assignment);
        assertThat(service.create(req).active()).isTrue();
    }

    @Test void create_nullActive_defaultsTrue() {
        FacultyLabAssignmentRequest req = new FacultyLabAssignmentRequest(1L, 1L, 1L, LocalDate.of(2024, 8, 1), null);
        when(fpRepo.findById(1L)).thenReturn(Optional.of(faculty));
        when(labRepo.findById(1L)).thenReturn(Optional.of(lab));
        when(semRepo.findById(1L)).thenReturn(Optional.of(semester));
        when(assignRepo.save(any())).thenReturn(assignment);
        service.create(req);
        verify(assignRepo).save(any());
    }

    @Test void create_facultyNotFound() {
        FacultyLabAssignmentRequest req = new FacultyLabAssignmentRequest(99L, 1L, 1L, LocalDate.of(2024, 8, 1), null);
        when(fpRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void create_labNotFound() {
        FacultyLabAssignmentRequest req = new FacultyLabAssignmentRequest(1L, 99L, 1L, LocalDate.of(2024, 8, 1), null);
        when(fpRepo.findById(1L)).thenReturn(Optional.of(faculty));
        when(labRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void create_semesterNotFound() {
        FacultyLabAssignmentRequest req = new FacultyLabAssignmentRequest(1L, 1L, 99L, LocalDate.of(2024, 8, 1), null);
        when(fpRepo.findById(1L)).thenReturn(Optional.of(faculty));
        when(labRepo.findById(1L)).thenReturn(Optional.of(lab));
        when(semRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void update_success() {
        FacultyLabAssignmentRequest req = new FacultyLabAssignmentRequest(1L, 1L, 1L, LocalDate.of(2024, 9, 1), false);
        when(assignRepo.findById(1L)).thenReturn(Optional.of(assignment));
        when(fpRepo.findById(1L)).thenReturn(Optional.of(faculty));
        when(labRepo.findById(1L)).thenReturn(Optional.of(lab));
        when(semRepo.findById(1L)).thenReturn(Optional.of(semester));
        when(assignRepo.save(any())).thenReturn(assignment);
        assertThat(service.update(1L, req)).isNotNull();
    }

    @Test void update_nullActive_keepsCurrent() {
        FacultyLabAssignmentRequest req = new FacultyLabAssignmentRequest(1L, 1L, 1L, LocalDate.of(2024, 9, 1), null);
        when(assignRepo.findById(1L)).thenReturn(Optional.of(assignment));
        when(fpRepo.findById(1L)).thenReturn(Optional.of(faculty));
        when(labRepo.findById(1L)).thenReturn(Optional.of(lab));
        when(semRepo.findById(1L)).thenReturn(Optional.of(semester));
        when(assignRepo.save(any())).thenReturn(assignment);
        service.update(1L, req);
        assertThat(assignment.getActive()).isTrue();
    }

    @Test void update_notFound() {
        FacultyLabAssignmentRequest req = new FacultyLabAssignmentRequest(1L, 1L, 1L, LocalDate.of(2024, 9, 1), null);
        when(assignRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(99L, req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void delete_success() {
        when(assignRepo.existsById(1L)).thenReturn(true);
        service.delete(1L);
        verify(assignRepo).deleteById(1L);
    }

    @Test void delete_notFound() {
        when(assignRepo.existsById(99L)).thenReturn(false);
        assertThatThrownBy(() -> service.delete(99L)).isInstanceOf(ResourceNotFoundException.class);
    }
}
