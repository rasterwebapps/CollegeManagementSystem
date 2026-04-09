package com.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cms.dto.CourseOutcomeRequest;
import com.cms.dto.CourseOutcomeResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.CourseOutcome;
import com.cms.model.Syllabus;
import com.cms.repository.CourseOutcomeRepository;
import com.cms.repository.SyllabusRepository;

@Service
public class CourseOutcomeService {

    private final CourseOutcomeRepository courseOutcomeRepository;
    private final SyllabusRepository syllabusRepository;

    public CourseOutcomeService(CourseOutcomeRepository courseOutcomeRepository,
                                SyllabusRepository syllabusRepository) {
        this.courseOutcomeRepository = courseOutcomeRepository;
        this.syllabusRepository = syllabusRepository;
    }

    public List<CourseOutcomeResponse> findAll() {
        return courseOutcomeRepository.findAll().stream()
            .map(this::toResponse)
            .toList();
    }

    public CourseOutcomeResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    public CourseOutcomeResponse create(CourseOutcomeRequest request) {
        Syllabus syllabus = syllabusRepository.findById(request.syllabusId())
            .orElseThrow(() -> new ResourceNotFoundException("Syllabus", request.syllabusId()));
        CourseOutcome co = new CourseOutcome();
        co.setSyllabus(syllabus);
        co.setCode(request.code());
        co.setDescription(request.description());
        co.setBloomLevel(request.bloomLevel());
        return toResponse(courseOutcomeRepository.save(co));
    }

    public CourseOutcomeResponse update(Long id, CourseOutcomeRequest request) {
        CourseOutcome co = getOrThrow(id);
        Syllabus syllabus = syllabusRepository.findById(request.syllabusId())
            .orElseThrow(() -> new ResourceNotFoundException("Syllabus", request.syllabusId()));
        co.setSyllabus(syllabus);
        co.setCode(request.code());
        co.setDescription(request.description());
        co.setBloomLevel(request.bloomLevel());
        return toResponse(courseOutcomeRepository.save(co));
    }

    public void delete(Long id) {
        if (!courseOutcomeRepository.existsById(id)) {
            throw new ResourceNotFoundException("CourseOutcome", id);
        }
        courseOutcomeRepository.deleteById(id);
    }

    private CourseOutcome getOrThrow(Long id) {
        return courseOutcomeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("CourseOutcome", id));
    }

    private CourseOutcomeResponse toResponse(CourseOutcome co) {
        return new CourseOutcomeResponse(
            co.getId(), co.getSyllabus().getId(),
            co.getCode(), co.getDescription(), co.getBloomLevel(),
            co.getCreatedAt(), co.getUpdatedAt()
        );
    }
}
