package com.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cms.dto.SemesterRequest;
import com.cms.dto.SemesterResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.AcademicYear;
import com.cms.model.Semester;
import com.cms.repository.AcademicYearRepository;
import com.cms.repository.SemesterRepository;

@Service
public class SemesterService {

    private final SemesterRepository semesterRepository;
    private final AcademicYearRepository academicYearRepository;

    public SemesterService(SemesterRepository semesterRepository, AcademicYearRepository academicYearRepository) {
        this.semesterRepository = semesterRepository;
        this.academicYearRepository = academicYearRepository;
    }

    public List<SemesterResponse> findAll() {
        return semesterRepository.findAll().stream()
            .map(this::toResponse)
            .toList();
    }

    public SemesterResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    public SemesterResponse create(SemesterRequest request) {
        AcademicYear ay = academicYearRepository.findById(request.academicYearId())
            .orElseThrow(() -> new ResourceNotFoundException("AcademicYear", request.academicYearId()));
        Semester semester = new Semester();
        semester.setName(request.name());
        semester.setSemesterNumber(request.semesterNumber());
        semester.setAcademicYear(ay);
        semester.setStartDate(request.startDate());
        semester.setEndDate(request.endDate());
        return toResponse(semesterRepository.save(semester));
    }

    public SemesterResponse update(Long id, SemesterRequest request) {
        Semester semester = getOrThrow(id);
        AcademicYear ay = academicYearRepository.findById(request.academicYearId())
            .orElseThrow(() -> new ResourceNotFoundException("AcademicYear", request.academicYearId()));
        semester.setName(request.name());
        semester.setSemesterNumber(request.semesterNumber());
        semester.setAcademicYear(ay);
        semester.setStartDate(request.startDate());
        semester.setEndDate(request.endDate());
        return toResponse(semesterRepository.save(semester));
    }

    public void delete(Long id) {
        if (!semesterRepository.existsById(id)) {
            throw new ResourceNotFoundException("Semester", id);
        }
        semesterRepository.deleteById(id);
    }

    private Semester getOrThrow(Long id) {
        return semesterRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Semester", id));
    }

    private SemesterResponse toResponse(Semester s) {
        return new SemesterResponse(
            s.getId(), s.getName(), s.getSemesterNumber(),
            s.getAcademicYear().getId(), s.getAcademicYear().getName(),
            s.getStartDate(), s.getEndDate(),
            s.getCreatedAt(), s.getUpdatedAt()
        );
    }
}
