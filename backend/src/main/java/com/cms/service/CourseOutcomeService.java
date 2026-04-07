package com.cms.service;

import com.cms.dto.CourseOutcomeRequest;
import com.cms.dto.CourseOutcomeResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Course;
import com.cms.model.CourseOutcome;
import com.cms.repository.CourseOutcomeRepository;
import com.cms.repository.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseOutcomeService {

    private final CourseOutcomeRepository courseOutcomeRepository;
    private final CourseRepository courseRepository;

    public CourseOutcomeService(CourseOutcomeRepository courseOutcomeRepository,
                                CourseRepository courseRepository) {
        this.courseOutcomeRepository = courseOutcomeRepository;
        this.courseRepository = courseRepository;
    }

    @Transactional(readOnly = true)
    public List<CourseOutcomeResponse> findByCourseId(Long courseId) {
        return courseOutcomeRepository.findByCourseId(courseId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public CourseOutcomeResponse findById(Long id) {
        CourseOutcome outcome = courseOutcomeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CourseOutcome", id));
        return toResponse(outcome);
    }

    @Transactional
    public CourseOutcomeResponse create(CourseOutcomeRequest request) {
        Course course = courseRepository.findById(request.courseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", request.courseId()));
        if (courseOutcomeRepository.existsByCourseIdAndCode(request.courseId(), request.code())) {
            throw new IllegalArgumentException(
                    "Course outcome with code '" + request.code() + "' already exists for this course");
        }

        CourseOutcome outcome = new CourseOutcome();
        outcome.setCourse(course);
        outcome.setCode(request.code());
        outcome.setDescription(request.description());
        outcome.setBloomLevel(request.bloomLevel());
        CourseOutcome saved = courseOutcomeRepository.save(outcome);
        return toResponse(saved);
    }

    @Transactional
    public CourseOutcomeResponse update(Long id, CourseOutcomeRequest request) {
        CourseOutcome outcome = courseOutcomeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CourseOutcome", id));
        Course course = courseRepository.findById(request.courseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", request.courseId()));

        outcome.setCourse(course);
        outcome.setCode(request.code());
        outcome.setDescription(request.description());
        outcome.setBloomLevel(request.bloomLevel());
        CourseOutcome saved = courseOutcomeRepository.save(outcome);
        return toResponse(saved);
    }

    @Transactional
    public void delete(Long id) {
        CourseOutcome outcome = courseOutcomeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CourseOutcome", id));
        courseOutcomeRepository.delete(outcome);
    }

    private CourseOutcomeResponse toResponse(CourseOutcome entity) {
        return new CourseOutcomeResponse(
                entity.getId(),
                entity.getCourse().getId(),
                entity.getCourse().getName(),
                entity.getCode(),
                entity.getDescription(),
                entity.getBloomLevel()
        );
    }
}
