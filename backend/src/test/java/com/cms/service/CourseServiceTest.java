package com.cms.service;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cms.dto.CourseRequest;
import com.cms.dto.CourseResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Course;
import com.cms.model.Department;
import com.cms.model.Program;
import com.cms.repository.CourseRepository;
import com.cms.repository.ProgramRepository;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock private CourseRepository courseRepository;
    @Mock private ProgramRepository programRepository;

    @InjectMocks
    private CourseService courseService;

    private Course course;
    private Program program;

    @BeforeEach
    void setUp() {
        Department dept = new Department();
        dept.setId(1L);
        dept.setName("CS");

        program = new Program();
        program.setId(1L);
        program.setName("B.Tech CS");
        program.setDepartment(dept);

        course = new Course();
        course.setId(1L);
        course.setName("Data Structures");
        course.setCode("CS201");
        course.setProgram(program);
        course.setCredits(4);
        course.setCourseType("THEORY");
    }

    @Test
    void findAll_returnsAll() {
        when(courseRepository.findAll()).thenReturn(List.of(course));
        List<CourseResponse> result = courseService.findAll();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).programName()).isEqualTo("B.Tech CS");
    }

    @Test
    void findById_found() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        assertThat(courseService.findById(1L).code()).isEqualTo("CS201");
    }

    @Test
    void findById_notFound() {
        when(courseRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> courseService.findById(99L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void create_success() {
        CourseRequest req = new CourseRequest("Data Structures", "CS201", 1L, 4, "THEORY");
        when(courseRepository.existsByCode("CS201")).thenReturn(false);
        when(programRepository.findById(1L)).thenReturn(Optional.of(program));
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        CourseResponse result = courseService.create(req);
        assertThat(result.name()).isEqualTo("Data Structures");
    }

    @Test
    void create_duplicateCode() {
        CourseRequest req = new CourseRequest("Data Structures", "CS201", 1L, 4, "THEORY");
        when(courseRepository.existsByCode("CS201")).thenReturn(true);
        assertThatThrownBy(() -> courseService.create(req)).isInstanceOf(IllegalArgumentException.class);
        verify(courseRepository, never()).save(any());
    }

    @Test
    void create_programNotFound() {
        CourseRequest req = new CourseRequest("Data Structures", "CS201", 99L, 4, "THEORY");
        when(courseRepository.existsByCode("CS201")).thenReturn(false);
        when(programRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> courseService.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void update_success() {
        CourseRequest req = new CourseRequest("Updated", "CS202", 1L, 3, "LAB");
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(courseRepository.findByCode("CS202")).thenReturn(Optional.empty());
        when(programRepository.findById(1L)).thenReturn(Optional.of(program));
        when(courseRepository.save(any(Course.class))).thenReturn(course);
        assertThat(courseService.update(1L, req)).isNotNull();
    }

    @Test
    void update_notFound() {
        CourseRequest req = new CourseRequest("Updated", "CS202", 1L, 3, "LAB");
        when(courseRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> courseService.update(99L, req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void update_duplicateCode() {
        Course other = new Course();
        other.setId(2L);
        other.setCode("CS202");

        CourseRequest req = new CourseRequest("Updated", "CS202", 1L, 3, "LAB");
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(courseRepository.findByCode("CS202")).thenReturn(Optional.of(other));
        assertThatThrownBy(() -> courseService.update(1L, req)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void update_sameCode() {
        CourseRequest req = new CourseRequest("Updated", "CS201", 1L, 3, "LAB");
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(courseRepository.findByCode("CS201")).thenReturn(Optional.of(course));
        when(programRepository.findById(1L)).thenReturn(Optional.of(program));
        when(courseRepository.save(any())).thenReturn(course);
        assertThat(courseService.update(1L, req)).isNotNull();
    }

    @Test
    void delete_success() {
        when(courseRepository.existsById(1L)).thenReturn(true);
        courseService.delete(1L);
        verify(courseRepository).deleteById(1L);
    }

    @Test
    void delete_notFound() {
        when(courseRepository.existsById(99L)).thenReturn(false);
        assertThatThrownBy(() -> courseService.delete(99L)).isInstanceOf(ResourceNotFoundException.class);
    }
}
