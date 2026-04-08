package com.cms.service;

import com.cms.dto.SemesterRequest;
import com.cms.dto.SemesterResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.AcademicYear;
import com.cms.model.Semester;
import com.cms.repository.AcademicYearRepository;
import com.cms.repository.SemesterRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SemesterService {

    private final SemesterRepository semesterRepository;
    private final AcademicYearRepository academicYearRepository;

    public SemesterService(SemesterRepository semesterRepository,
                           AcademicYearRepository academicYearRepository) {
        this.semesterRepository = semesterRepository;
        this.academicYearRepository = academicYearRepository;
    }

    @Transactional(readOnly = true)
    public List<SemesterResponse> findAll() {
        return semesterRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public SemesterResponse findById(Long id) {
        Semester semester = semesterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Semester", id));
        return toResponse(semester);
    }

    @Transactional(readOnly = true)
    public List<SemesterResponse> findByAcademicYearId(Long academicYearId) {
        return semesterRepository.findByAcademicYearId(academicYearId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public SemesterResponse create(SemesterRequest request) {
        AcademicYear academicYear = academicYearRepository.findById(request.academicYearId())
                .orElseThrow(() -> new ResourceNotFoundException("AcademicYear", request.academicYearId()));
        if (request.startDate() != null && request.endDate() != null
                && !request.startDate().isBefore(request.endDate())) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
        Semester semester = new Semester();
        semester.setAcademicYear(academicYear);
        semester.setName(request.name());
        semester.setNumber(request.number());
        semester.setStartDate(request.startDate());
        semester.setEndDate(request.endDate());
        Semester saved = semesterRepository.save(semester);
        return toResponse(saved);
    }

    @Transactional
    public SemesterResponse update(Long id, SemesterRequest request) {
        Semester semester = semesterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Semester", id));
        AcademicYear academicYear = academicYearRepository.findById(request.academicYearId())
                .orElseThrow(() -> new ResourceNotFoundException("AcademicYear", request.academicYearId()));
        if (request.startDate() != null && request.endDate() != null
                && !request.startDate().isBefore(request.endDate())) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
        semester.setAcademicYear(academicYear);
        semester.setName(request.name());
        semester.setNumber(request.number());
        semester.setStartDate(request.startDate());
        semester.setEndDate(request.endDate());
        Semester saved = semesterRepository.save(semester);
        return toResponse(saved);
    }

    @Transactional
    public void delete(Long id) {
        Semester semester = semesterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Semester", id));
        semesterRepository.delete(semester);
    }

    private SemesterResponse toResponse(Semester entity) {
        return new SemesterResponse(
                entity.getId(),
                entity.getAcademicYear().getId(),
                entity.getAcademicYear().getName(),
                entity.getName(),
                entity.getNumber(),
                entity.getStartDate(),
                entity.getEndDate()
        );
    }
}
