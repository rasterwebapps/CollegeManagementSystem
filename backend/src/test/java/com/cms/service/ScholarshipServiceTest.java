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

import com.cms.dto.ScholarshipRequest;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Scholarship;
import com.cms.model.StudentProfile;
import com.cms.repository.ScholarshipRepository;
import com.cms.repository.StudentProfileRepository;

@ExtendWith(MockitoExtension.class)
class ScholarshipServiceTest {

    @Mock private ScholarshipRepository scholarshipRepo;
    @Mock private StudentProfileRepository studentProfileRepo;
    @InjectMocks private ScholarshipService service;

    private Scholarship scholarship;
    private StudentProfile student;

    @BeforeEach
    void setUp() {
        student = new StudentProfile();
        student.setId(1L); student.setFirstName("John"); student.setLastName("Doe");
        scholarship = new Scholarship();
        scholarship.setId(1L); scholarship.setStudent(student); scholarship.setName("Merit");
        scholarship.setAmount(new BigDecimal("10000.00")); scholarship.setScholarshipType("MERIT");
        scholarship.setAwardedDate(LocalDate.of(2024, 1, 1)); scholarship.setStatus("ACTIVE");
    }

    @Test void findAll() {
        when(scholarshipRepo.findAll()).thenReturn(List.of(scholarship));
        assertThat(service.findAll()).hasSize(1);
    }

    @Test void findById_found() {
        when(scholarshipRepo.findById(1L)).thenReturn(Optional.of(scholarship));
        var resp = service.findById(1L);
        assertThat(resp.studentName()).isEqualTo("John Doe");
    }

    @Test void findById_notFound() {
        when(scholarshipRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(99L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void create_success() {
        ScholarshipRequest req = new ScholarshipRequest(1L, "Merit", new BigDecimal("10000"), "MERIT", LocalDate.of(2024, 1, 1), null, "ACTIVE", null);
        when(studentProfileRepo.findById(1L)).thenReturn(Optional.of(student));
        when(scholarshipRepo.save(any())).thenReturn(scholarship);
        assertThat(service.create(req).name()).isEqualTo("Merit");
    }

    @Test void create_nullStatus() {
        ScholarshipRequest req = new ScholarshipRequest(1L, "Merit", new BigDecimal("10000"), "MERIT", LocalDate.of(2024, 1, 1), null, null, null);
        when(studentProfileRepo.findById(1L)).thenReturn(Optional.of(student));
        when(scholarshipRepo.save(any())).thenReturn(scholarship);
        service.create(req);
        verify(scholarshipRepo).save(any());
    }

    @Test void create_studentNotFound() {
        ScholarshipRequest req = new ScholarshipRequest(99L, "Merit", new BigDecimal("10000"), "MERIT", LocalDate.of(2024, 1, 1), null, null, null);
        when(studentProfileRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void update_success() {
        ScholarshipRequest req = new ScholarshipRequest(1L, "Updated", new BigDecimal("15000"), "NEED", LocalDate.of(2024, 1, 1), LocalDate.of(2025, 1, 1), "ACTIVE", "remarks");
        when(scholarshipRepo.findById(1L)).thenReturn(Optional.of(scholarship));
        when(studentProfileRepo.findById(1L)).thenReturn(Optional.of(student));
        when(scholarshipRepo.save(any())).thenReturn(scholarship);
        assertThat(service.update(1L, req)).isNotNull();
    }

    @Test void update_nullStatus() {
        ScholarshipRequest req = new ScholarshipRequest(1L, "Updated", new BigDecimal("15000"), "NEED", LocalDate.of(2024, 1, 1), null, null, null);
        when(scholarshipRepo.findById(1L)).thenReturn(Optional.of(scholarship));
        when(studentProfileRepo.findById(1L)).thenReturn(Optional.of(student));
        when(scholarshipRepo.save(any())).thenReturn(scholarship);
        service.update(1L, req);
        assertThat(scholarship.getStatus()).isEqualTo("ACTIVE");
    }

    @Test void update_notFound() {
        ScholarshipRequest req = new ScholarshipRequest(1L, "Updated", new BigDecimal("15000"), "NEED", LocalDate.of(2024, 1, 1), null, null, null);
        when(scholarshipRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(99L, req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void update_studentNotFound() {
        ScholarshipRequest req = new ScholarshipRequest(99L, "Updated", new BigDecimal("15000"), "NEED", LocalDate.of(2024, 1, 1), null, null, null);
        when(scholarshipRepo.findById(1L)).thenReturn(Optional.of(scholarship));
        when(studentProfileRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(1L, req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void delete_success() {
        when(scholarshipRepo.existsById(1L)).thenReturn(true);
        service.delete(1L);
        verify(scholarshipRepo).deleteById(1L);
    }

    @Test void delete_notFound() {
        when(scholarshipRepo.existsById(99L)).thenReturn(false);
        assertThatThrownBy(() -> service.delete(99L)).isInstanceOf(ResourceNotFoundException.class);
    }
}
