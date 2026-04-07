package com.cms.service;

import com.cms.dto.CourseRequest;
import com.cms.dto.CourseResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Course;
import com.cms.model.Department;
import com.cms.model.Program;
import com.cms.repository.CourseRepository;
import com.cms.repository.DepartmentRepository;
import com.cms.repository.ProgramRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final ProgramRepository programRepository;
    private final DepartmentRepository departmentRepository;

    public CourseService(CourseRepository courseRepository,
                         ProgramRepository programRepository,
                         DepartmentRepository departmentRepository) {
        this.courseRepository = courseRepository;
        this.programRepository = programRepository;
        this.departmentRepository = departmentRepository;
    }

    @Transactional(readOnly = true)
    public List<CourseResponse> findAll() {
        return courseRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public CourseResponse findById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course", id));
        return toResponse(course);
    }

    @Transactional(readOnly = true)
    public List<CourseResponse> findByProgramId(Long programId) {
        return courseRepository.findByProgramId(programId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CourseResponse> findByDepartmentId(Long departmentId) {
        return courseRepository.findByDepartmentId(departmentId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public CourseResponse create(CourseRequest request) {
        if (courseRepository.existsByCode(request.code())) {
            throw new IllegalArgumentException("Course with code '" + request.code() + "' already exists");
        }
        Program program = programRepository.findById(request.programId())
                .orElseThrow(() -> new ResourceNotFoundException("Program", request.programId()));
        Department department = departmentRepository.findById(request.departmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department", request.departmentId()));

        Course course = new Course();
        course.setName(request.name());
        course.setCode(request.code());
        course.setProgram(program);
        course.setDepartment(department);
        course.setCredits(request.credits());
        course.setTheoryHours(request.theoryHours());
        course.setPracticalHours(request.practicalHours());
        course.setCourseType(request.courseType());
        course.setSemesterNumber(request.semesterNumber());
        course.setActive(request.active() != null ? request.active() : true);
        Course saved = courseRepository.save(course);
        return toResponse(saved);
    }

    @Transactional
    public CourseResponse update(Long id, CourseRequest request) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course", id));
        Program program = programRepository.findById(request.programId())
                .orElseThrow(() -> new ResourceNotFoundException("Program", request.programId()));
        Department department = departmentRepository.findById(request.departmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department", request.departmentId()));

        course.setName(request.name());
        course.setCode(request.code());
        course.setProgram(program);
        course.setDepartment(department);
        course.setCredits(request.credits());
        course.setTheoryHours(request.theoryHours());
        course.setPracticalHours(request.practicalHours());
        course.setCourseType(request.courseType());
        course.setSemesterNumber(request.semesterNumber());
        if (request.active() != null) {
            course.setActive(request.active());
        }
        Course saved = courseRepository.save(course);
        return toResponse(saved);
    }

    @Transactional
    public void delete(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course", id));
        courseRepository.delete(course);
    }

    private CourseResponse toResponse(Course entity) {
        return new CourseResponse(
                entity.getId(),
                entity.getName(),
                entity.getCode(),
                entity.getProgram().getId(),
                entity.getProgram().getName(),
                entity.getDepartment().getId(),
                entity.getDepartment().getName(),
                entity.getCredits(),
                entity.getTheoryHours(),
                entity.getPracticalHours(),
                entity.getCourseType(),
                entity.getSemesterNumber(),
                entity.isActive()
        );
    }
}
