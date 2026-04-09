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

import com.cms.dto.StudentEnrollmentRequest;
import com.cms.dto.StudentEnrollmentResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Course;
import com.cms.model.Department;
import com.cms.model.Program;
import com.cms.model.Semester;
import com.cms.model.StudentEnrollment;
import com.cms.model.StudentProfile;
import com.cms.repository.CourseRepository;
import com.cms.repository.SemesterRepository;
import com.cms.repository.StudentEnrollmentRepository;
import com.cms.repository.StudentProfileRepository;

@ExtendWith(MockitoExtension.class)
class StudentEnrollmentServiceTest {

    @Mock private StudentEnrollmentRepository seRepo;
    @Mock private StudentProfileRepository studentRepo;
    @Mock private CourseRepository courseRepo;
    @Mock private SemesterRepository semesterRepo;
    @InjectMocks private StudentEnrollmentService service;

    private StudentEnrollment se;
    private StudentProfile student;
    private Course course;
    private Semester semester;

    @BeforeEach
    void setUp() {
        Department dept = new Department(); dept.setId(1L); dept.setName("CS");
        Program program = new Program(); program.setId(1L); program.setName("B.Tech");
        student = new StudentProfile();
        student.setId(1L); student.setFirstName("Alice"); student.setLastName("Smith");
        student.setProgram(program); student.setDepartment(dept);
        course = new Course(); course.setId(1L); course.setName("CS101");
        semester = new Semester(); semester.setId(1L); semester.setName("Fall 2024");

        se = new StudentEnrollment();
        se.setId(1L); se.setStudent(student); se.setCourse(course);
        se.setSemester(semester); se.setLabBatchGroup("A");
        se.setEnrollmentDate(LocalDate.of(2024,8,1)); se.setStatus("ENROLLED");
    }

    @Test void findAll() {
        when(seRepo.findAll()).thenReturn(List.of(se));
        List<StudentEnrollmentResponse> result = service.findAll();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).courseName()).isEqualTo("CS101");
    }

    @Test void findById_found() {
        when(seRepo.findById(1L)).thenReturn(Optional.of(se));
        assertThat(service.findById(1L).labBatchGroup()).isEqualTo("A");
    }

    @Test void findById_notFound() {
        when(seRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(99L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void create_success() {
        StudentEnrollmentRequest req = new StudentEnrollmentRequest(1L, 1L, 1L, "A", LocalDate.of(2024,8,1), "ENROLLED");
        when(studentRepo.findById(1L)).thenReturn(Optional.of(student));
        when(courseRepo.findById(1L)).thenReturn(Optional.of(course));
        when(semesterRepo.findById(1L)).thenReturn(Optional.of(semester));
        when(seRepo.save(any())).thenReturn(se);
        assertThat(service.create(req).courseName()).isEqualTo("CS101");
    }

    @Test void create_nullStatus_defaultsEnrolled() {
        StudentEnrollmentRequest req = new StudentEnrollmentRequest(1L, 1L, 1L, null, LocalDate.of(2024,8,1), null);
        when(studentRepo.findById(1L)).thenReturn(Optional.of(student));
        when(courseRepo.findById(1L)).thenReturn(Optional.of(course));
        when(semesterRepo.findById(1L)).thenReturn(Optional.of(semester));
        when(seRepo.save(any())).thenReturn(se);
        service.create(req);
        verify(seRepo).save(any());
    }

    @Test void create_studentNotFound() {
        StudentEnrollmentRequest req = new StudentEnrollmentRequest(99L, 1L, 1L, null, LocalDate.of(2024,8,1), null);
        when(studentRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void create_courseNotFound() {
        StudentEnrollmentRequest req = new StudentEnrollmentRequest(1L, 99L, 1L, null, LocalDate.of(2024,8,1), null);
        when(studentRepo.findById(1L)).thenReturn(Optional.of(student));
        when(courseRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void create_semesterNotFound() {
        StudentEnrollmentRequest req = new StudentEnrollmentRequest(1L, 1L, 99L, null, LocalDate.of(2024,8,1), null);
        when(studentRepo.findById(1L)).thenReturn(Optional.of(student));
        when(courseRepo.findById(1L)).thenReturn(Optional.of(course));
        when(semesterRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void update_success() {
        StudentEnrollmentRequest req = new StudentEnrollmentRequest(1L, 1L, 1L, "B", LocalDate.of(2024,9,1), "COMPLETED");
        when(seRepo.findById(1L)).thenReturn(Optional.of(se));
        when(studentRepo.findById(1L)).thenReturn(Optional.of(student));
        when(courseRepo.findById(1L)).thenReturn(Optional.of(course));
        when(semesterRepo.findById(1L)).thenReturn(Optional.of(semester));
        when(seRepo.save(any())).thenReturn(se);
        assertThat(service.update(1L, req)).isNotNull();
    }

    @Test void update_nullStatus_keepsCurrent() {
        StudentEnrollmentRequest req = new StudentEnrollmentRequest(1L, 1L, 1L, "A", LocalDate.of(2024,8,1), null);
        when(seRepo.findById(1L)).thenReturn(Optional.of(se));
        when(studentRepo.findById(1L)).thenReturn(Optional.of(student));
        when(courseRepo.findById(1L)).thenReturn(Optional.of(course));
        when(semesterRepo.findById(1L)).thenReturn(Optional.of(semester));
        when(seRepo.save(any())).thenReturn(se);
        service.update(1L, req);
        assertThat(se.getStatus()).isEqualTo("ENROLLED");
    }

    @Test void update_notFound() {
        StudentEnrollmentRequest req = new StudentEnrollmentRequest(1L, 1L, 1L, null, LocalDate.of(2024,8,1), null);
        when(seRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(99L, req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void delete_success() {
        when(seRepo.existsById(1L)).thenReturn(true);
        service.delete(1L);
        verify(seRepo).deleteById(1L);
    }

    @Test void delete_notFound() {
        when(seRepo.existsById(99L)).thenReturn(false);
        assertThatThrownBy(() -> service.delete(99L)).isInstanceOf(ResourceNotFoundException.class);
    }
}
