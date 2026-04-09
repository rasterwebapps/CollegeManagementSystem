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

import com.cms.dto.AdmissionRequest;
import com.cms.dto.AdmissionResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Admission;
import com.cms.model.Department;
import com.cms.model.Program;
import com.cms.model.StudentProfile;
import com.cms.repository.AdmissionRepository;
import com.cms.repository.ProgramRepository;
import com.cms.repository.StudentProfileRepository;

@ExtendWith(MockitoExtension.class)
class AdmissionServiceTest {

    @Mock private AdmissionRepository admissionRepo;
    @Mock private StudentProfileRepository studentRepo;
    @Mock private ProgramRepository programRepo;
    @InjectMocks private AdmissionService service;

    private Admission admission;
    private StudentProfile student;
    private Program program;

    @BeforeEach
    void setUp() {
        Department dept = new Department(); dept.setId(1L); dept.setName("CS");
        program = new Program(); program.setId(1L); program.setName("B.Tech");
        student = new StudentProfile();
        student.setId(1L); student.setFirstName("Alice"); student.setLastName("Smith");
        student.setProgram(program); student.setDepartment(dept);

        admission = new Admission();
        admission.setId(1L); admission.setStudent(student);
        admission.setApplicationNumber("APP001"); admission.setProgram(program);
        admission.setAdmissionDate(LocalDate.of(2024,8,1));
        admission.setAdmissionType("REGULAR"); admission.setStatus("PENDING");
        admission.setRemarks("Good student");
    }

    @Test void findAll() {
        when(admissionRepo.findAll()).thenReturn(List.of(admission));
        List<AdmissionResponse> result = service.findAll();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).applicationNumber()).isEqualTo("APP001");
    }

    @Test void findById_found() {
        when(admissionRepo.findById(1L)).thenReturn(Optional.of(admission));
        assertThat(service.findById(1L).admissionType()).isEqualTo("REGULAR");
    }

    @Test void findById_notFound() {
        when(admissionRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(99L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void create_success() {
        AdmissionRequest req = new AdmissionRequest(1L, "APP001", 1L, LocalDate.of(2024,8,1), "REGULAR", "PENDING", "Good student");
        when(admissionRepo.existsByApplicationNumber("APP001")).thenReturn(false);
        when(studentRepo.findById(1L)).thenReturn(Optional.of(student));
        when(programRepo.findById(1L)).thenReturn(Optional.of(program));
        when(admissionRepo.save(any())).thenReturn(admission);
        assertThat(service.create(req).applicationNumber()).isEqualTo("APP001");
    }

    @Test void create_nullStatus_defaultsPending() {
        AdmissionRequest req = new AdmissionRequest(1L, "APP001", 1L, LocalDate.of(2024,8,1), "REGULAR", null, null);
        when(admissionRepo.existsByApplicationNumber("APP001")).thenReturn(false);
        when(studentRepo.findById(1L)).thenReturn(Optional.of(student));
        when(programRepo.findById(1L)).thenReturn(Optional.of(program));
        when(admissionRepo.save(any())).thenReturn(admission);
        service.create(req);
        verify(admissionRepo).save(any());
    }

    @Test void create_duplicateApplicationNumber() {
        AdmissionRequest req = new AdmissionRequest(1L, "APP001", 1L, LocalDate.of(2024,8,1), "REGULAR", null, null);
        when(admissionRepo.existsByApplicationNumber("APP001")).thenReturn(true);
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("Application number already exists");
    }

    @Test void create_studentNotFound() {
        AdmissionRequest req = new AdmissionRequest(99L, "APP001", 1L, LocalDate.of(2024,8,1), "REGULAR", null, null);
        when(admissionRepo.existsByApplicationNumber("APP001")).thenReturn(false);
        when(studentRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void create_programNotFound() {
        AdmissionRequest req = new AdmissionRequest(1L, "APP001", 99L, LocalDate.of(2024,8,1), "REGULAR", null, null);
        when(admissionRepo.existsByApplicationNumber("APP001")).thenReturn(false);
        when(studentRepo.findById(1L)).thenReturn(Optional.of(student));
        when(programRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void update_success() {
        AdmissionRequest req = new AdmissionRequest(1L, "APP002", 1L, LocalDate.of(2024,9,1), "LATERAL", "APPROVED", "Updated");
        when(admissionRepo.findById(1L)).thenReturn(Optional.of(admission));
        when(admissionRepo.findByApplicationNumber("APP002")).thenReturn(Optional.empty());
        when(studentRepo.findById(1L)).thenReturn(Optional.of(student));
        when(programRepo.findById(1L)).thenReturn(Optional.of(program));
        when(admissionRepo.save(any())).thenReturn(admission);
        assertThat(service.update(1L, req)).isNotNull();
    }

    @Test void update_nullStatus_keepsCurrent() {
        AdmissionRequest req = new AdmissionRequest(1L, "APP001", 1L, LocalDate.of(2024,8,1), "REGULAR", null, null);
        when(admissionRepo.findById(1L)).thenReturn(Optional.of(admission));
        when(admissionRepo.findByApplicationNumber("APP001")).thenReturn(Optional.of(admission));
        when(studentRepo.findById(1L)).thenReturn(Optional.of(student));
        when(programRepo.findById(1L)).thenReturn(Optional.of(program));
        when(admissionRepo.save(any())).thenReturn(admission);
        service.update(1L, req);
        assertThat(admission.getStatus()).isEqualTo("PENDING");
    }

    @Test void update_notFound() {
        AdmissionRequest req = new AdmissionRequest(1L, "APP001", 1L, LocalDate.of(2024,8,1), "REGULAR", null, null);
        when(admissionRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(99L, req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void update_duplicateApplicationNumber() {
        Admission other = new Admission(); other.setId(2L);
        AdmissionRequest req = new AdmissionRequest(1L, "APP002", 1L, LocalDate.of(2024,8,1), "REGULAR", null, null);
        when(admissionRepo.findById(1L)).thenReturn(Optional.of(admission));
        when(admissionRepo.findByApplicationNumber("APP002")).thenReturn(Optional.of(other));
        assertThatThrownBy(() -> service.update(1L, req)).isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("Application number already exists");
    }

    @Test void delete_success() {
        when(admissionRepo.existsById(1L)).thenReturn(true);
        service.delete(1L);
        verify(admissionRepo).deleteById(1L);
    }

    @Test void delete_notFound() {
        when(admissionRepo.existsById(99L)).thenReturn(false);
        assertThatThrownBy(() -> service.delete(99L)).isInstanceOf(ResourceNotFoundException.class);
    }
}
