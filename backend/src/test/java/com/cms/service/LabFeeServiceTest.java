package com.cms.service;

import java.math.BigDecimal;
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

import com.cms.dto.LabFeeRequest;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Course;
import com.cms.model.Lab;
import com.cms.model.LabFee;
import com.cms.model.Semester;
import com.cms.repository.CourseRepository;
import com.cms.repository.LabFeeRepository;
import com.cms.repository.LabRepository;
import com.cms.repository.SemesterRepository;

@ExtendWith(MockitoExtension.class)
class LabFeeServiceTest {

    @Mock private LabFeeRepository labFeeRepo;
    @Mock private LabRepository labRepo;
    @Mock private CourseRepository courseRepo;
    @Mock private SemesterRepository semesterRepo;
    @InjectMocks private LabFeeService service;

    private LabFee labFee;
    private Lab lab;
    private Course course;
    private Semester semester;

    @BeforeEach
    void setUp() {
        lab = new Lab(); lab.setId(1L); lab.setName("Physics Lab");
        course = new Course(); course.setId(1L); course.setName("Physics 101");
        semester = new Semester(); semester.setId(1L); semester.setName("Sem 1");
        labFee = new LabFee();
        labFee.setId(1L); labFee.setLab(lab); labFee.setCourse(course); labFee.setSemester(semester);
        labFee.setAmount(new BigDecimal("5000.00")); labFee.setStatus("ACTIVE");
    }

    @Test void findAll() {
        when(labFeeRepo.findAll()).thenReturn(List.of(labFee));
        assertThat(service.findAll()).hasSize(1);
    }

    @Test void findById_found() {
        when(labFeeRepo.findById(1L)).thenReturn(Optional.of(labFee));
        assertThat(service.findById(1L).labName()).isEqualTo("Physics Lab");
    }

    @Test void findById_notFound() {
        when(labFeeRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(99L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void create_success() {
        LabFeeRequest req = new LabFeeRequest(1L, 1L, 1L, new BigDecimal("5000"), null, "ACTIVE");
        when(labRepo.findById(1L)).thenReturn(Optional.of(lab));
        when(courseRepo.findById(1L)).thenReturn(Optional.of(course));
        when(semesterRepo.findById(1L)).thenReturn(Optional.of(semester));
        when(labFeeRepo.save(any())).thenReturn(labFee);
        assertThat(service.create(req).amount()).isEqualTo(new BigDecimal("5000.00"));
    }

    @Test void create_nullStatus() {
        LabFeeRequest req = new LabFeeRequest(1L, 1L, 1L, new BigDecimal("5000"), null, null);
        when(labRepo.findById(1L)).thenReturn(Optional.of(lab));
        when(courseRepo.findById(1L)).thenReturn(Optional.of(course));
        when(semesterRepo.findById(1L)).thenReturn(Optional.of(semester));
        when(labFeeRepo.save(any())).thenReturn(labFee);
        service.create(req);
        verify(labFeeRepo).save(any());
    }

    @Test void create_labNotFound() {
        LabFeeRequest req = new LabFeeRequest(99L, 1L, 1L, new BigDecimal("5000"), null, null);
        when(labRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void create_courseNotFound() {
        LabFeeRequest req = new LabFeeRequest(1L, 99L, 1L, new BigDecimal("5000"), null, null);
        when(labRepo.findById(1L)).thenReturn(Optional.of(lab));
        when(courseRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void create_semesterNotFound() {
        LabFeeRequest req = new LabFeeRequest(1L, 1L, 99L, new BigDecimal("5000"), null, null);
        when(labRepo.findById(1L)).thenReturn(Optional.of(lab));
        when(courseRepo.findById(1L)).thenReturn(Optional.of(course));
        when(semesterRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void update_success() {
        LabFeeRequest req = new LabFeeRequest(1L, 1L, 1L, new BigDecimal("6000"), "Updated", "ACTIVE");
        when(labFeeRepo.findById(1L)).thenReturn(Optional.of(labFee));
        when(labRepo.findById(1L)).thenReturn(Optional.of(lab));
        when(courseRepo.findById(1L)).thenReturn(Optional.of(course));
        when(semesterRepo.findById(1L)).thenReturn(Optional.of(semester));
        when(labFeeRepo.save(any())).thenReturn(labFee);
        assertThat(service.update(1L, req)).isNotNull();
    }

    @Test void update_nullStatus() {
        LabFeeRequest req = new LabFeeRequest(1L, 1L, 1L, new BigDecimal("6000"), null, null);
        when(labFeeRepo.findById(1L)).thenReturn(Optional.of(labFee));
        when(labRepo.findById(1L)).thenReturn(Optional.of(lab));
        when(courseRepo.findById(1L)).thenReturn(Optional.of(course));
        when(semesterRepo.findById(1L)).thenReturn(Optional.of(semester));
        when(labFeeRepo.save(any())).thenReturn(labFee);
        service.update(1L, req);
        assertThat(labFee.getStatus()).isEqualTo("ACTIVE");
    }

    @Test void update_notFound() {
        LabFeeRequest req = new LabFeeRequest(1L, 1L, 1L, new BigDecimal("6000"), null, null);
        when(labFeeRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(99L, req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void delete_success() {
        when(labFeeRepo.existsById(1L)).thenReturn(true);
        service.delete(1L);
        verify(labFeeRepo).deleteById(1L);
    }

    @Test void delete_notFound() {
        when(labFeeRepo.existsById(99L)).thenReturn(false);
        assertThatThrownBy(() -> service.delete(99L)).isInstanceOf(ResourceNotFoundException.class);
    }
}
