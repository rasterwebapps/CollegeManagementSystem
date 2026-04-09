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

import com.cms.dto.AttendanceAlertRequest;
import com.cms.dto.AttendanceAlertResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.AttendanceAlert;
import com.cms.model.Course;
import com.cms.model.Department;
import com.cms.model.Program;
import com.cms.model.StudentProfile;
import com.cms.repository.AttendanceAlertRepository;
import com.cms.repository.CourseRepository;
import com.cms.repository.StudentProfileRepository;

@ExtendWith(MockitoExtension.class)
class AttendanceAlertServiceTest {

    @Mock private AttendanceAlertRepository alertRepo;
    @Mock private StudentProfileRepository studentRepo;
    @Mock private CourseRepository courseRepo;
    @InjectMocks private AttendanceAlertService service;

    private AttendanceAlert alert;
    private StudentProfile student;
    private Course course;

    @BeforeEach
    void setUp() {
        Department dept = new Department(); dept.setId(1L); dept.setName("CS");
        Program program = new Program(); program.setId(1L); program.setName("B.Tech");
        student = new StudentProfile();
        student.setId(1L); student.setFirstName("Alice"); student.setLastName("Smith");
        student.setProgram(program); student.setDepartment(dept);
        course = new Course(); course.setId(1L); course.setName("CS101");

        alert = new AttendanceAlert();
        alert.setId(1L); alert.setStudent(student); alert.setCourse(course);
        alert.setAlertType("LOW_ATTENDANCE"); alert.setMessage("Below threshold");
        alert.setThresholdPercentage(new BigDecimal("75.00"));
        alert.setCurrentPercentage(new BigDecimal("60.00"));
        alert.setResolved(false);
    }

    @Test void findAll() {
        when(alertRepo.findAll()).thenReturn(List.of(alert));
        List<AttendanceAlertResponse> result = service.findAll();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).alertType()).isEqualTo("LOW_ATTENDANCE");
    }

    @Test void findById_found() {
        when(alertRepo.findById(1L)).thenReturn(Optional.of(alert));
        assertThat(service.findById(1L).message()).isEqualTo("Below threshold");
    }

    @Test void findById_notFound() {
        when(alertRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(99L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void create_success() {
        AttendanceAlertRequest req = new AttendanceAlertRequest(1L, 1L, "LOW_ATTENDANCE", "Below threshold", new BigDecimal("75.00"), new BigDecimal("60.00"));
        when(studentRepo.findById(1L)).thenReturn(Optional.of(student));
        when(courseRepo.findById(1L)).thenReturn(Optional.of(course));
        when(alertRepo.save(any())).thenReturn(alert);
        assertThat(service.create(req).alertType()).isEqualTo("LOW_ATTENDANCE");
    }

    @Test void create_studentNotFound() {
        AttendanceAlertRequest req = new AttendanceAlertRequest(99L, 1L, "LOW_ATTENDANCE", "Below threshold", null, null);
        when(studentRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void create_courseNotFound() {
        AttendanceAlertRequest req = new AttendanceAlertRequest(1L, 99L, "LOW_ATTENDANCE", "Below threshold", null, null);
        when(studentRepo.findById(1L)).thenReturn(Optional.of(student));
        when(courseRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void update_success() {
        AttendanceAlertRequest req = new AttendanceAlertRequest(1L, 1L, "CRITICAL", "Very low", new BigDecimal("75.00"), new BigDecimal("40.00"));
        when(alertRepo.findById(1L)).thenReturn(Optional.of(alert));
        when(studentRepo.findById(1L)).thenReturn(Optional.of(student));
        when(courseRepo.findById(1L)).thenReturn(Optional.of(course));
        when(alertRepo.save(any())).thenReturn(alert);
        assertThat(service.update(1L, req)).isNotNull();
    }

    @Test void update_notFound() {
        AttendanceAlertRequest req = new AttendanceAlertRequest(1L, 1L, "LOW_ATTENDANCE", "Below threshold", null, null);
        when(alertRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(99L, req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void update_studentNotFound() {
        AttendanceAlertRequest req = new AttendanceAlertRequest(99L, 1L, "LOW_ATTENDANCE", "Below threshold", null, null);
        when(alertRepo.findById(1L)).thenReturn(Optional.of(alert));
        when(studentRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(1L, req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void resolve_success() {
        when(alertRepo.findById(1L)).thenReturn(Optional.of(alert));
        when(alertRepo.save(any())).thenReturn(alert);
        service.resolve(1L);
        assertThat(alert.getResolved()).isTrue();
    }

    @Test void resolve_notFound() {
        when(alertRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.resolve(99L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void delete_success() {
        when(alertRepo.existsById(1L)).thenReturn(true);
        service.delete(1L);
        verify(alertRepo).deleteById(1L);
    }

    @Test void delete_notFound() {
        when(alertRepo.existsById(99L)).thenReturn(false);
        assertThatThrownBy(() -> service.delete(99L)).isInstanceOf(ResourceNotFoundException.class);
    }
}
