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

import com.cms.dto.LabStaffAssignmentRequest;
import com.cms.dto.LabStaffAssignmentResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Lab;
import com.cms.model.LabStaffAssignment;
import com.cms.model.LabType;
import com.cms.model.Department;
import com.cms.repository.LabRepository;
import com.cms.repository.LabStaffAssignmentRepository;

@ExtendWith(MockitoExtension.class)
class LabStaffAssignmentServiceTest {

    @Mock private LabStaffAssignmentRepository assignRepo;
    @Mock private LabRepository labRepo;
    @InjectMocks private LabStaffAssignmentService service;

    private LabStaffAssignment assignment;
    private Lab lab;

    @BeforeEach
    void setUp() {
        lab = new Lab();
        lab.setId(1L);
        lab.setName("CS Lab 1");

        assignment = new LabStaffAssignment();
        assignment.setId(1L);
        assignment.setLab(lab);
        assignment.setStaffKeycloakId("kc-123");
        assignment.setStaffRole("LAB_INCHARGE");
        assignment.setAssignedDate(LocalDate.of(2024, 8, 1));
        assignment.setActive(true);
    }

    @Test
    void findAll() {
        when(assignRepo.findAll()).thenReturn(List.of(assignment));
        assertThat(service.findAll()).hasSize(1);
    }

    @Test
    void findById_found() {
        when(assignRepo.findById(1L)).thenReturn(Optional.of(assignment));
        LabStaffAssignmentResponse r = service.findById(1L);
        assertThat(r.staffKeycloakId()).isEqualTo("kc-123");
    }

    @Test
    void findById_notFound() {
        when(assignRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(99L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void create_success() {
        LabStaffAssignmentRequest req = new LabStaffAssignmentRequest(1L, "kc-123", "LAB_INCHARGE", LocalDate.of(2024, 8, 1), null, true);
        when(labRepo.findById(1L)).thenReturn(Optional.of(lab));
        when(assignRepo.save(any())).thenReturn(assignment);
        assertThat(service.create(req).staffRole()).isEqualTo("LAB_INCHARGE");
    }

    @Test
    void create_nullActive_defaultsTrue() {
        LabStaffAssignmentRequest req = new LabStaffAssignmentRequest(1L, "kc-123", "LAB_INCHARGE", LocalDate.of(2024, 8, 1), null, null);
        when(labRepo.findById(1L)).thenReturn(Optional.of(lab));
        when(assignRepo.save(any())).thenReturn(assignment);
        service.create(req);
        verify(assignRepo).save(any());
    }

    @Test
    void create_labNotFound() {
        LabStaffAssignmentRequest req = new LabStaffAssignmentRequest(99L, "kc-123", "LAB_INCHARGE", LocalDate.of(2024, 8, 1), null, null);
        when(labRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void update_success() {
        LabStaffAssignmentRequest req = new LabStaffAssignmentRequest(1L, "kc-456", "TECHNICIAN", LocalDate.of(2024, 9, 1), LocalDate.of(2025, 5, 31), false);
        when(assignRepo.findById(1L)).thenReturn(Optional.of(assignment));
        when(labRepo.findById(1L)).thenReturn(Optional.of(lab));
        when(assignRepo.save(any())).thenReturn(assignment);
        assertThat(service.update(1L, req)).isNotNull();
    }

    @Test
    void update_nullActive_keepsCurrent() {
        LabStaffAssignmentRequest req = new LabStaffAssignmentRequest(1L, "kc-456", "TECHNICIAN", LocalDate.of(2024, 9, 1), null, null);
        when(assignRepo.findById(1L)).thenReturn(Optional.of(assignment));
        when(labRepo.findById(1L)).thenReturn(Optional.of(lab));
        when(assignRepo.save(any())).thenReturn(assignment);
        service.update(1L, req);
        assertThat(assignment.getActive()).isTrue();
    }

    @Test
    void update_notFound() {
        LabStaffAssignmentRequest req = new LabStaffAssignmentRequest(1L, "kc-456", "TECHNICIAN", LocalDate.of(2024, 9, 1), null, null);
        when(assignRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(99L, req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void update_labNotFound() {
        LabStaffAssignmentRequest req = new LabStaffAssignmentRequest(99L, "kc-456", "TECHNICIAN", LocalDate.of(2024, 9, 1), null, null);
        when(assignRepo.findById(1L)).thenReturn(Optional.of(assignment));
        when(labRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(1L, req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void delete_success() {
        when(assignRepo.existsById(1L)).thenReturn(true);
        service.delete(1L);
        verify(assignRepo).deleteById(1L);
    }

    @Test
    void delete_notFound() {
        when(assignRepo.existsById(99L)).thenReturn(false);
        assertThatThrownBy(() -> service.delete(99L)).isInstanceOf(ResourceNotFoundException.class);
    }
}
