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

import com.cms.dto.LabUtilizationKpiRequest;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Lab;
import com.cms.model.LabUtilizationKpi;
import com.cms.model.Semester;
import com.cms.repository.LabRepository;
import com.cms.repository.LabUtilizationKpiRepository;
import com.cms.repository.SemesterRepository;

@ExtendWith(MockitoExtension.class)
class LabUtilizationKpiServiceTest {

    @Mock private LabUtilizationKpiRepository kpiRepo;
    @Mock private LabRepository labRepo;
    @Mock private SemesterRepository semesterRepo;
    @InjectMocks private LabUtilizationKpiService service;

    private LabUtilizationKpi kpi;
    private Lab lab;
    private Semester semester;

    @BeforeEach
    void setUp() {
        lab = new Lab(); lab.setId(1L); lab.setName("Physics Lab");
        semester = new Semester(); semester.setId(1L); semester.setName("Fall 2024");
        kpi = new LabUtilizationKpi();
        kpi.setId(1L); kpi.setLab(lab); kpi.setSemester(semester);
        kpi.setTotalSlots(100); kpi.setUtilizedSlots(85);
        kpi.setUtilizationPercentage(new BigDecimal("85.00"));
        kpi.setPeakHours("10:00-14:00"); kpi.setAvgOccupancy(new BigDecimal("75.50"));
        kpi.setMeasurementDate(LocalDate.of(2024, 6, 30));
    }

    @Test void findAll() {
        when(kpiRepo.findAll()).thenReturn(List.of(kpi));
        assertThat(service.findAll()).hasSize(1);
    }

    @Test void findById_found() {
        when(kpiRepo.findById(1L)).thenReturn(Optional.of(kpi));
        assertThat(service.findById(1L).totalSlots()).isEqualTo(100);
    }

    @Test void findById_notFound() {
        when(kpiRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(99L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void create_success() {
        LabUtilizationKpiRequest req = new LabUtilizationKpiRequest(1L, 1L, 100, 85, new BigDecimal("85.00"), "10:00-14:00", new BigDecimal("75.50"), LocalDate.of(2024, 6, 30));
        when(labRepo.findById(1L)).thenReturn(Optional.of(lab));
        when(semesterRepo.findById(1L)).thenReturn(Optional.of(semester));
        when(kpiRepo.save(any())).thenReturn(kpi);
        assertThat(service.create(req).totalSlots()).isEqualTo(100);
    }

    @Test void create_labNotFound() {
        LabUtilizationKpiRequest req = new LabUtilizationKpiRequest(99L, 1L, 100, 85, null, null, null, LocalDate.of(2024, 6, 30));
        when(labRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void create_semesterNotFound() {
        LabUtilizationKpiRequest req = new LabUtilizationKpiRequest(1L, 99L, 100, 85, null, null, null, LocalDate.of(2024, 6, 30));
        when(labRepo.findById(1L)).thenReturn(Optional.of(lab));
        when(semesterRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void update_success() {
        LabUtilizationKpiRequest req = new LabUtilizationKpiRequest(1L, 1L, 120, 100, new BigDecimal("83.33"), "09:00-13:00", new BigDecimal("80.00"), LocalDate.of(2024, 7, 15));
        when(kpiRepo.findById(1L)).thenReturn(Optional.of(kpi));
        when(labRepo.findById(1L)).thenReturn(Optional.of(lab));
        when(semesterRepo.findById(1L)).thenReturn(Optional.of(semester));
        when(kpiRepo.save(any())).thenReturn(kpi);
        assertThat(service.update(1L, req)).isNotNull();
    }

    @Test void update_notFound() {
        LabUtilizationKpiRequest req = new LabUtilizationKpiRequest(1L, 1L, 120, 100, null, null, null, LocalDate.of(2024, 7, 15));
        when(kpiRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(99L, req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void update_labNotFound() {
        LabUtilizationKpiRequest req = new LabUtilizationKpiRequest(99L, 1L, 120, 100, null, null, null, LocalDate.of(2024, 7, 15));
        when(kpiRepo.findById(1L)).thenReturn(Optional.of(kpi));
        when(labRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(1L, req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void delete_success() {
        when(kpiRepo.existsById(1L)).thenReturn(true);
        service.delete(1L);
        verify(kpiRepo).deleteById(1L);
    }

    @Test void delete_notFound() {
        when(kpiRepo.existsById(99L)).thenReturn(false);
        assertThatThrownBy(() -> service.delete(99L)).isInstanceOf(ResourceNotFoundException.class);
    }
}
