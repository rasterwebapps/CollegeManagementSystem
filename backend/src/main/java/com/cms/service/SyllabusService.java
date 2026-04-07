package com.cms.service;

import com.cms.dto.SyllabusRequest;
import com.cms.dto.SyllabusResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.AcademicYear;
import com.cms.model.Course;
import com.cms.model.Syllabus;
import com.cms.repository.AcademicYearRepository;
import com.cms.repository.CourseRepository;
import com.cms.repository.SyllabusRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SyllabusService {

    private final SyllabusRepository syllabusRepository;
    private final CourseRepository courseRepository;
    private final AcademicYearRepository academicYearRepository;

    public SyllabusService(SyllabusRepository syllabusRepository,
                           CourseRepository courseRepository,
                           AcademicYearRepository academicYearRepository) {
        this.syllabusRepository = syllabusRepository;
        this.courseRepository = courseRepository;
        this.academicYearRepository = academicYearRepository;
    }

    @Transactional(readOnly = true)
    public List<SyllabusResponse> findByCourseId(Long courseId) {
        return syllabusRepository.findByCourseId(courseId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public SyllabusResponse findById(Long id) {
        Syllabus syllabus = syllabusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Syllabus", id));
        return toResponse(syllabus);
    }

    @Transactional
    public SyllabusResponse createOrUpdate(SyllabusRequest request) {
        Course course = courseRepository.findById(request.courseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", request.courseId()));
        AcademicYear academicYear = academicYearRepository.findById(request.academicYearId())
                .orElseThrow(() -> new ResourceNotFoundException("AcademicYear", request.academicYearId()));

        Optional<Syllabus> existing = syllabusRepository.findByCourseIdAndAcademicYearId(
                request.courseId(), request.academicYearId());

        Syllabus syllabus;
        if (existing.isPresent()) {
            syllabus = existing.get();
        } else {
            syllabus = new Syllabus();
            syllabus.setCourse(course);
            syllabus.setAcademicYear(academicYear);
        }
        syllabus.setContent(request.content());
        syllabus.setVersion(request.version() != null ? request.version() : 1);
        syllabus.setApproved(request.approved() != null ? request.approved() : false);
        Syllabus saved = syllabusRepository.save(syllabus);
        return toResponse(saved);
    }

    @Transactional
    public void delete(Long id) {
        Syllabus syllabus = syllabusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Syllabus", id));
        syllabusRepository.delete(syllabus);
    }

    private SyllabusResponse toResponse(Syllabus entity) {
        return new SyllabusResponse(
                entity.getId(),
                entity.getCourse().getId(),
                entity.getCourse().getName(),
                entity.getAcademicYear().getId(),
                entity.getAcademicYear().getName(),
                entity.getContent(),
                entity.getVersion(),
                entity.isApproved()
        );
    }
}
