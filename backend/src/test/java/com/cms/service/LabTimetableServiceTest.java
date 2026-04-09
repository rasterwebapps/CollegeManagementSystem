package com.cms.service;

import java.time.LocalTime;
import java.util.Collections;
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

import com.cms.dto.LabTimetableRequest;
import com.cms.dto.LabTimetableResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Course;
import com.cms.model.Department;
import com.cms.model.FacultyProfile;
import com.cms.model.Lab;
import com.cms.model.LabTimetable;
import com.cms.model.Semester;
import com.cms.repository.CourseRepository;
import com.cms.repository.FacultyProfileRepository;
import com.cms.repository.LabRepository;
import com.cms.repository.LabTimetableRepository;
import com.cms.repository.SemesterRepository;

@ExtendWith(MockitoExtension.class)
class LabTimetableServiceTest {

    @Mock private LabTimetableRepository ltRepo;
    @Mock private LabRepository labRepo;
    @Mock private CourseRepository courseRepo;
    @Mock private SemesterRepository semesterRepo;
    @Mock private FacultyProfileRepository facultyRepo;
    @InjectMocks private LabTimetableService service;

    private LabTimetable lt;
    private Lab lab;
    private Course course;
    private Semester semester;
    private FacultyProfile faculty;

    @BeforeEach
    void setUp() {
        lab = new Lab(); lab.setId(1L); lab.setName("Lab A");
        course = new Course(); course.setId(1L); course.setName("CS101");
        semester = new Semester(); semester.setId(1L); semester.setName("Fall 2024");
        Department dept = new Department(); dept.setId(1L); dept.setName("CS");
        faculty = new FacultyProfile(); faculty.setId(1L); faculty.setFirstName("John"); faculty.setLastName("Doe"); faculty.setDepartment(dept);

        lt = new LabTimetable();
        lt.setId(1L); lt.setLab(lab); lt.setCourse(course); lt.setSemester(semester);
        lt.setFaculty(faculty); lt.setDayOfWeek("MONDAY");
        lt.setStartTime(LocalTime.of(9, 0)); lt.setEndTime(LocalTime.of(11, 0));
        lt.setBatchGroup("A"); lt.setStatus("SCHEDULED");
    }

    @Test void findAll() {
        when(ltRepo.findAll()).thenReturn(List.of(lt));
        List<LabTimetableResponse> result = service.findAll();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).labName()).isEqualTo("Lab A");
    }

    @Test void findById_found() {
        when(ltRepo.findById(1L)).thenReturn(Optional.of(lt));
        assertThat(service.findById(1L).dayOfWeek()).isEqualTo("MONDAY");
    }

    @Test void findById_notFound() {
        when(ltRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(99L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void create_success() {
        LabTimetableRequest req = new LabTimetableRequest(1L, 1L, 1L, 1L, "MONDAY", LocalTime.of(9,0), LocalTime.of(11,0), "A", "SCHEDULED");
        when(labRepo.findById(1L)).thenReturn(Optional.of(lab));
        when(courseRepo.findById(1L)).thenReturn(Optional.of(course));
        when(semesterRepo.findById(1L)).thenReturn(Optional.of(semester));
        when(facultyRepo.findById(1L)).thenReturn(Optional.of(faculty));
        when(ltRepo.findByLabIdAndDayOfWeek(1L, "MONDAY")).thenReturn(Collections.emptyList());
        when(ltRepo.findByFacultyIdAndDayOfWeek(1L, "MONDAY")).thenReturn(Collections.emptyList());
        when(ltRepo.save(any())).thenReturn(lt);
        assertThat(service.create(req).labName()).isEqualTo("Lab A");
    }

    @Test void create_nullStatus_defaultsScheduled() {
        LabTimetableRequest req = new LabTimetableRequest(1L, 1L, 1L, 1L, "MONDAY", LocalTime.of(9,0), LocalTime.of(11,0), null, null);
        when(labRepo.findById(1L)).thenReturn(Optional.of(lab));
        when(courseRepo.findById(1L)).thenReturn(Optional.of(course));
        when(semesterRepo.findById(1L)).thenReturn(Optional.of(semester));
        when(facultyRepo.findById(1L)).thenReturn(Optional.of(faculty));
        when(ltRepo.findByLabIdAndDayOfWeek(1L, "MONDAY")).thenReturn(Collections.emptyList());
        when(ltRepo.findByFacultyIdAndDayOfWeek(1L, "MONDAY")).thenReturn(Collections.emptyList());
        when(ltRepo.save(any())).thenReturn(lt);
        service.create(req);
        verify(ltRepo).save(any());
    }

    @Test void create_labNotFound() {
        LabTimetableRequest req = new LabTimetableRequest(99L, 1L, 1L, 1L, "MONDAY", LocalTime.of(9,0), LocalTime.of(11,0), null, null);
        when(labRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void create_courseNotFound() {
        LabTimetableRequest req = new LabTimetableRequest(1L, 99L, 1L, 1L, "MONDAY", LocalTime.of(9,0), LocalTime.of(11,0), null, null);
        when(labRepo.findById(1L)).thenReturn(Optional.of(lab));
        when(courseRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void create_semesterNotFound() {
        LabTimetableRequest req = new LabTimetableRequest(1L, 1L, 99L, 1L, "MONDAY", LocalTime.of(9,0), LocalTime.of(11,0), null, null);
        when(labRepo.findById(1L)).thenReturn(Optional.of(lab));
        when(courseRepo.findById(1L)).thenReturn(Optional.of(course));
        when(semesterRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void create_facultyNotFound() {
        LabTimetableRequest req = new LabTimetableRequest(1L, 1L, 1L, 99L, "MONDAY", LocalTime.of(9,0), LocalTime.of(11,0), null, null);
        when(labRepo.findById(1L)).thenReturn(Optional.of(lab));
        when(courseRepo.findById(1L)).thenReturn(Optional.of(course));
        when(semesterRepo.findById(1L)).thenReturn(Optional.of(semester));
        when(facultyRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void create_labConflict() {
        LabTimetableRequest req = new LabTimetableRequest(1L, 1L, 1L, 1L, "MONDAY", LocalTime.of(10,0), LocalTime.of(12,0), null, null);
        when(labRepo.findById(1L)).thenReturn(Optional.of(lab));
        when(courseRepo.findById(1L)).thenReturn(Optional.of(course));
        when(semesterRepo.findById(1L)).thenReturn(Optional.of(semester));
        when(facultyRepo.findById(1L)).thenReturn(Optional.of(faculty));
        LabTimetable existing = new LabTimetable(); existing.setId(2L);
        existing.setStartTime(LocalTime.of(9,0)); existing.setEndTime(LocalTime.of(11,0));
        when(ltRepo.findByLabIdAndDayOfWeek(1L, "MONDAY")).thenReturn(List.of(existing));
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("Lab room conflict");
    }

    @Test void create_facultyConflict() {
        LabTimetableRequest req = new LabTimetableRequest(1L, 1L, 1L, 1L, "MONDAY", LocalTime.of(10,0), LocalTime.of(12,0), null, null);
        when(labRepo.findById(1L)).thenReturn(Optional.of(lab));
        when(courseRepo.findById(1L)).thenReturn(Optional.of(course));
        when(semesterRepo.findById(1L)).thenReturn(Optional.of(semester));
        when(facultyRepo.findById(1L)).thenReturn(Optional.of(faculty));
        when(ltRepo.findByLabIdAndDayOfWeek(1L, "MONDAY")).thenReturn(Collections.emptyList());
        LabTimetable existing = new LabTimetable(); existing.setId(2L);
        existing.setStartTime(LocalTime.of(9,0)); existing.setEndTime(LocalTime.of(11,0));
        when(ltRepo.findByFacultyIdAndDayOfWeek(1L, "MONDAY")).thenReturn(List.of(existing));
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("Faculty conflict");
    }

    @Test void update_success() {
        LabTimetableRequest req = new LabTimetableRequest(1L, 1L, 1L, 1L, "TUESDAY", LocalTime.of(14,0), LocalTime.of(16,0), "B", "ACTIVE");
        when(ltRepo.findById(1L)).thenReturn(Optional.of(lt));
        when(labRepo.findById(1L)).thenReturn(Optional.of(lab));
        when(courseRepo.findById(1L)).thenReturn(Optional.of(course));
        when(semesterRepo.findById(1L)).thenReturn(Optional.of(semester));
        when(facultyRepo.findById(1L)).thenReturn(Optional.of(faculty));
        when(ltRepo.findByLabIdAndDayOfWeek(1L, "TUESDAY")).thenReturn(Collections.emptyList());
        when(ltRepo.findByFacultyIdAndDayOfWeek(1L, "TUESDAY")).thenReturn(Collections.emptyList());
        when(ltRepo.save(any())).thenReturn(lt);
        assertThat(service.update(1L, req)).isNotNull();
    }

    @Test void update_nullStatus_keepsCurrent() {
        LabTimetableRequest req = new LabTimetableRequest(1L, 1L, 1L, 1L, "TUESDAY", LocalTime.of(14,0), LocalTime.of(16,0), null, null);
        when(ltRepo.findById(1L)).thenReturn(Optional.of(lt));
        when(labRepo.findById(1L)).thenReturn(Optional.of(lab));
        when(courseRepo.findById(1L)).thenReturn(Optional.of(course));
        when(semesterRepo.findById(1L)).thenReturn(Optional.of(semester));
        when(facultyRepo.findById(1L)).thenReturn(Optional.of(faculty));
        when(ltRepo.findByLabIdAndDayOfWeek(1L, "TUESDAY")).thenReturn(Collections.emptyList());
        when(ltRepo.findByFacultyIdAndDayOfWeek(1L, "TUESDAY")).thenReturn(Collections.emptyList());
        when(ltRepo.save(any())).thenReturn(lt);
        service.update(1L, req);
        assertThat(lt.getStatus()).isEqualTo("SCHEDULED");
    }

    @Test void update_notFound() {
        LabTimetableRequest req = new LabTimetableRequest(1L, 1L, 1L, 1L, "MONDAY", LocalTime.of(9,0), LocalTime.of(11,0), null, null);
        when(ltRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(99L, req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void delete_success() {
        when(ltRepo.existsById(1L)).thenReturn(true);
        service.delete(1L);
        verify(ltRepo).deleteById(1L);
    }

    @Test void delete_notFound() {
        when(ltRepo.existsById(99L)).thenReturn(false);
        assertThatThrownBy(() -> service.delete(99L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void timesOverlap_true() {
        assertThat(service.timesOverlap(LocalTime.of(9,0), LocalTime.of(11,0), LocalTime.of(10,0), LocalTime.of(12,0))).isTrue();
    }

    @Test void timesOverlap_false() {
        assertThat(service.timesOverlap(LocalTime.of(9,0), LocalTime.of(10,0), LocalTime.of(10,0), LocalTime.of(11,0))).isFalse();
    }
}
