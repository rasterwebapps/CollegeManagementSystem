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

import com.cms.dto.AccreditationReportRequest;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.AccreditationReport;
import com.cms.model.Department;
import com.cms.repository.AccreditationReportRepository;
import com.cms.repository.DepartmentRepository;

@ExtendWith(MockitoExtension.class)
class AccreditationReportServiceTest {

    @Mock private AccreditationReportRepository arRepo;
    @Mock private DepartmentRepository departmentRepo;
    @InjectMocks private AccreditationReportService service;

    private AccreditationReport report;
    private Department department;

    @BeforeEach
    void setUp() {
        department = new Department(); department.setId(1L); department.setName("Computer Science");
        report = new AccreditationReport();
        report.setId(1L); report.setDepartment(department);
        report.setReportType("NBA"); report.setAcademicYear("2023-24");
        report.setGeneratedDate(LocalDate.of(2024, 3, 1));
        report.setOverallScore(new BigDecimal("8.50")); report.setStatus("DRAFT");
        report.setSummary("Annual accreditation report");
    }

    @Test void findAll() {
        when(arRepo.findAll()).thenReturn(List.of(report));
        assertThat(service.findAll()).hasSize(1);
    }

    @Test void findById_found() {
        when(arRepo.findById(1L)).thenReturn(Optional.of(report));
        assertThat(service.findById(1L).reportType()).isEqualTo("NBA");
    }

    @Test void findById_notFound() {
        when(arRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(99L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void create_success() {
        AccreditationReportRequest req = new AccreditationReportRequest(1L, "NBA", "2023-24", LocalDate.of(2024, 3, 1), new BigDecimal("8.50"), "DRAFT", "Annual report");
        when(departmentRepo.findById(1L)).thenReturn(Optional.of(department));
        when(arRepo.save(any())).thenReturn(report);
        assertThat(service.create(req).reportType()).isEqualTo("NBA");
    }

    @Test void create_nullStatus() {
        AccreditationReportRequest req = new AccreditationReportRequest(1L, "NBA", "2023-24", LocalDate.of(2024, 3, 1), null, null, null);
        when(departmentRepo.findById(1L)).thenReturn(Optional.of(department));
        when(arRepo.save(any())).thenReturn(report);
        service.create(req);
        verify(arRepo).save(any());
    }

    @Test void create_departmentNotFound() {
        AccreditationReportRequest req = new AccreditationReportRequest(99L, "NBA", "2023-24", LocalDate.of(2024, 3, 1), null, null, null);
        when(departmentRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void update_success() {
        AccreditationReportRequest req = new AccreditationReportRequest(1L, "NAAC", "2024-25", LocalDate.of(2024, 6, 1), new BigDecimal("9.00"), "SUBMITTED", "Updated report");
        when(arRepo.findById(1L)).thenReturn(Optional.of(report));
        when(departmentRepo.findById(1L)).thenReturn(Optional.of(department));
        when(arRepo.save(any())).thenReturn(report);
        assertThat(service.update(1L, req)).isNotNull();
    }

    @Test void update_nullStatus() {
        AccreditationReportRequest req = new AccreditationReportRequest(1L, "NAAC", "2024-25", LocalDate.of(2024, 6, 1), null, null, null);
        when(arRepo.findById(1L)).thenReturn(Optional.of(report));
        when(departmentRepo.findById(1L)).thenReturn(Optional.of(department));
        when(arRepo.save(any())).thenReturn(report);
        service.update(1L, req);
        assertThat(report.getStatus()).isEqualTo("DRAFT");
    }

    @Test void update_notFound() {
        AccreditationReportRequest req = new AccreditationReportRequest(1L, "NAAC", "2024-25", LocalDate.of(2024, 6, 1), null, null, null);
        when(arRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(99L, req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void update_departmentNotFound() {
        AccreditationReportRequest req = new AccreditationReportRequest(99L, "NAAC", "2024-25", LocalDate.of(2024, 6, 1), null, null, null);
        when(arRepo.findById(1L)).thenReturn(Optional.of(report));
        when(departmentRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(1L, req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void delete_success() {
        when(arRepo.existsById(1L)).thenReturn(true);
        service.delete(1L);
        verify(arRepo).deleteById(1L);
    }

    @Test void delete_notFound() {
        when(arRepo.existsById(99L)).thenReturn(false);
        assertThatThrownBy(() -> service.delete(99L)).isInstanceOf(ResourceNotFoundException.class);
    }
}
