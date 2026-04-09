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

import com.cms.dto.AnalyticsSnapshotRequest;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.AnalyticsSnapshot;
import com.cms.model.Department;
import com.cms.model.Program;
import com.cms.model.Semester;
import com.cms.repository.AnalyticsSnapshotRepository;
import com.cms.repository.DepartmentRepository;
import com.cms.repository.ProgramRepository;
import com.cms.repository.SemesterRepository;

@ExtendWith(MockitoExtension.class)
class AnalyticsSnapshotServiceTest {

    @Mock private AnalyticsSnapshotRepository snapshotRepo;
    @Mock private DepartmentRepository departmentRepo;
    @Mock private ProgramRepository programRepo;
    @Mock private SemesterRepository semesterRepo;
    @InjectMocks private AnalyticsSnapshotService service;

    private AnalyticsSnapshot snapshot;
    private Department department;
    private Program program;
    private Semester semester;

    @BeforeEach
    void setUp() {
        department = new Department(); department.setId(1L); department.setName("Computer Science");
        program = new Program(); program.setId(1L); program.setName("B.Tech CS");
        semester = new Semester(); semester.setId(1L); semester.setName("Fall 2024");
        snapshot = new AnalyticsSnapshot();
        snapshot.setId(1L); snapshot.setDepartment(department); snapshot.setProgram(program);
        snapshot.setSemester(semester); snapshot.setSnapshotType("ENROLLMENT");
        snapshot.setMetricName("Total Students"); snapshot.setMetricValue(new BigDecimal("250"));
        snapshot.setSnapshotDate(LocalDate.of(2024, 6, 30)); snapshot.setDetails("Enrollment metrics");
    }

    @Test void findAll() {
        when(snapshotRepo.findAll()).thenReturn(List.of(snapshot));
        assertThat(service.findAll()).hasSize(1);
    }

    @Test void findById_found() {
        when(snapshotRepo.findById(1L)).thenReturn(Optional.of(snapshot));
        assertThat(service.findById(1L).metricName()).isEqualTo("Total Students");
    }

    @Test void findById_notFound() {
        when(snapshotRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(99L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void create_success() {
        AnalyticsSnapshotRequest req = new AnalyticsSnapshotRequest(1L, 1L, 1L, "ENROLLMENT", "Total Students", new BigDecimal("250"), LocalDate.of(2024, 6, 30), "Enrollment metrics");
        when(departmentRepo.findById(1L)).thenReturn(Optional.of(department));
        when(semesterRepo.findById(1L)).thenReturn(Optional.of(semester));
        when(programRepo.findById(1L)).thenReturn(Optional.of(program));
        when(snapshotRepo.save(any())).thenReturn(snapshot);
        assertThat(service.create(req).metricName()).isEqualTo("Total Students");
    }

    @Test void create_nullProgram() {
        AnalyticsSnapshotRequest req = new AnalyticsSnapshotRequest(1L, null, 1L, "ENROLLMENT", "Total Students", new BigDecimal("250"), LocalDate.of(2024, 6, 30), null);
        when(departmentRepo.findById(1L)).thenReturn(Optional.of(department));
        when(semesterRepo.findById(1L)).thenReturn(Optional.of(semester));
        AnalyticsSnapshot saved = new AnalyticsSnapshot();
        saved.setId(2L); saved.setDepartment(department); saved.setSemester(semester);
        saved.setSnapshotType("ENROLLMENT"); saved.setMetricName("Total Students");
        saved.setMetricValue(new BigDecimal("250")); saved.setSnapshotDate(LocalDate.of(2024, 6, 30));
        when(snapshotRepo.save(any())).thenReturn(saved);
        assertThat(service.create(req).programId()).isNull();
    }

    @Test void create_departmentNotFound() {
        AnalyticsSnapshotRequest req = new AnalyticsSnapshotRequest(99L, null, 1L, "ENROLLMENT", "Total Students", new BigDecimal("250"), LocalDate.of(2024, 6, 30), null);
        when(departmentRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void create_semesterNotFound() {
        AnalyticsSnapshotRequest req = new AnalyticsSnapshotRequest(1L, null, 99L, "ENROLLMENT", "Total Students", new BigDecimal("250"), LocalDate.of(2024, 6, 30), null);
        when(departmentRepo.findById(1L)).thenReturn(Optional.of(department));
        when(semesterRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void create_programNotFound() {
        AnalyticsSnapshotRequest req = new AnalyticsSnapshotRequest(1L, 99L, 1L, "ENROLLMENT", "Total Students", new BigDecimal("250"), LocalDate.of(2024, 6, 30), null);
        when(departmentRepo.findById(1L)).thenReturn(Optional.of(department));
        when(semesterRepo.findById(1L)).thenReturn(Optional.of(semester));
        when(programRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void update_success() {
        AnalyticsSnapshotRequest req = new AnalyticsSnapshotRequest(1L, 1L, 1L, "PERFORMANCE", "Avg GPA", new BigDecimal("3.50"), LocalDate.of(2024, 7, 1), "Updated");
        when(snapshotRepo.findById(1L)).thenReturn(Optional.of(snapshot));
        when(departmentRepo.findById(1L)).thenReturn(Optional.of(department));
        when(semesterRepo.findById(1L)).thenReturn(Optional.of(semester));
        when(programRepo.findById(1L)).thenReturn(Optional.of(program));
        when(snapshotRepo.save(any())).thenReturn(snapshot);
        assertThat(service.update(1L, req)).isNotNull();
    }

    @Test void update_notFound() {
        AnalyticsSnapshotRequest req = new AnalyticsSnapshotRequest(1L, null, 1L, "PERFORMANCE", "Avg GPA", new BigDecimal("3.50"), LocalDate.of(2024, 7, 1), null);
        when(snapshotRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(99L, req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void update_departmentNotFound() {
        AnalyticsSnapshotRequest req = new AnalyticsSnapshotRequest(99L, null, 1L, "PERFORMANCE", "Avg GPA", new BigDecimal("3.50"), LocalDate.of(2024, 7, 1), null);
        when(snapshotRepo.findById(1L)).thenReturn(Optional.of(snapshot));
        when(departmentRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(1L, req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void delete_success() {
        when(snapshotRepo.existsById(1L)).thenReturn(true);
        service.delete(1L);
        verify(snapshotRepo).deleteById(1L);
    }

    @Test void delete_notFound() {
        when(snapshotRepo.existsById(99L)).thenReturn(false);
        assertThatThrownBy(() -> service.delete(99L)).isInstanceOf(ResourceNotFoundException.class);
    }
}
