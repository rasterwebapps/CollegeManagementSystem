package com.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cms.dto.SyllabusRequest;
import com.cms.dto.SyllabusResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.AcademicYear;
import com.cms.model.Course;
import com.cms.model.Syllabus;
import com.cms.repository.AcademicYearRepository;
import com.cms.repository.CourseRepository;
import com.cms.repository.SyllabusRepository;

@Service
public class SyllabusService {

    private final SyllabusRepository syllabusRepository;
    private final CourseRepository courseRepository;
    private final AcademicYearRepository academicYearRepository;

    public SyllabusService(SyllabusRepository syllabusRepository, CourseRepository courseRepository,
                           AcademicYearRepository academicYearRepository) {
        this.syllabusRepository = syllabusRepository;
        this.courseRepository = courseRepository;
        this.academicYearRepository = academicYearRepository;
    }

    public List<SyllabusResponse> findAll() {
        return syllabusRepository.findAll().stream()
            .map(this::toResponse)
            .toList();
    }

    public SyllabusResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    public SyllabusResponse create(SyllabusRequest request) {
        Course course = courseRepository.findById(request.courseId())
            .orElseThrow(() -> new ResourceNotFoundException("Course", request.courseId()));
        AcademicYear ay = academicYearRepository.findById(request.academicYearId())
            .orElseThrow(() -> new ResourceNotFoundException("AcademicYear", request.academicYearId()));
        Syllabus syllabus = new Syllabus();
        syllabus.setCourse(course);
        syllabus.setAcademicYear(ay);
        syllabus.setContent(request.content());
        syllabus.setObjectives(request.objectives());
        syllabus.setStatus(request.status() != null ? request.status() : "DRAFT");
        return toResponse(syllabusRepository.save(syllabus));
    }

    public SyllabusResponse update(Long id, SyllabusRequest request) {
        Syllabus syllabus = getOrThrow(id);
        Course course = courseRepository.findById(request.courseId())
            .orElseThrow(() -> new ResourceNotFoundException("Course", request.courseId()));
        AcademicYear ay = academicYearRepository.findById(request.academicYearId())
            .orElseThrow(() -> new ResourceNotFoundException("AcademicYear", request.academicYearId()));
        syllabus.setCourse(course);
        syllabus.setAcademicYear(ay);
        syllabus.setContent(request.content());
        syllabus.setObjectives(request.objectives());
        if (request.status() != null) {
            syllabus.setStatus(request.status());
        }
        return toResponse(syllabusRepository.save(syllabus));
    }

    public void delete(Long id) {
        if (!syllabusRepository.existsById(id)) {
            throw new ResourceNotFoundException("Syllabus", id);
        }
        syllabusRepository.deleteById(id);
    }

    private Syllabus getOrThrow(Long id) {
        return syllabusRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Syllabus", id));
    }

    private SyllabusResponse toResponse(Syllabus s) {
        return new SyllabusResponse(
            s.getId(), s.getCourse().getId(), s.getCourse().getName(),
            s.getAcademicYear().getId(), s.getAcademicYear().getName(),
            s.getContent(), s.getObjectives(), s.getStatus(),
            s.getCreatedAt(), s.getUpdatedAt()
        );
    }
}
