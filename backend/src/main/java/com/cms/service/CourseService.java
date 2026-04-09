package com.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cms.dto.CourseRequest;
import com.cms.dto.CourseResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Course;
import com.cms.model.Program;
import com.cms.repository.CourseRepository;
import com.cms.repository.ProgramRepository;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final ProgramRepository programRepository;

    public CourseService(CourseRepository courseRepository, ProgramRepository programRepository) {
        this.courseRepository = courseRepository;
        this.programRepository = programRepository;
    }

    public List<CourseResponse> findAll() {
        return courseRepository.findAll().stream()
            .map(this::toResponse)
            .toList();
    }

    public CourseResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    public CourseResponse create(CourseRequest request) {
        if (courseRepository.existsByCode(request.code())) {
            throw new IllegalArgumentException("Course with code '" + request.code() + "' already exists");
        }
        Program program = programRepository.findById(request.programId())
            .orElseThrow(() -> new ResourceNotFoundException("Program", request.programId()));
        Course course = new Course();
        course.setName(request.name());
        course.setCode(request.code());
        course.setProgram(program);
        course.setCredits(request.credits());
        course.setCourseType(request.courseType());
        return toResponse(courseRepository.save(course));
    }

    public CourseResponse update(Long id, CourseRequest request) {
        Course course = getOrThrow(id);
        courseRepository.findByCode(request.code())
            .filter(c -> !c.getId().equals(id))
            .ifPresent(c -> {
                throw new IllegalArgumentException("Course with code '" + request.code() + "' already exists");
            });
        Program program = programRepository.findById(request.programId())
            .orElseThrow(() -> new ResourceNotFoundException("Program", request.programId()));
        course.setName(request.name());
        course.setCode(request.code());
        course.setProgram(program);
        course.setCredits(request.credits());
        course.setCourseType(request.courseType());
        return toResponse(courseRepository.save(course));
    }

    public void delete(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Course", id);
        }
        courseRepository.deleteById(id);
    }

    private Course getOrThrow(Long id) {
        return courseRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Course", id));
    }

    private CourseResponse toResponse(Course c) {
        return new CourseResponse(
            c.getId(), c.getName(), c.getCode(),
            c.getProgram().getId(), c.getProgram().getName(),
            c.getCredits(), c.getCourseType(),
            c.getCreatedAt(), c.getUpdatedAt()
        );
    }
}
