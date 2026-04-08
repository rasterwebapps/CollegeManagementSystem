package com.cms.service;

import com.cms.dto.AcademicYearRequest;
import com.cms.dto.AcademicYearResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.AcademicYear;
import com.cms.repository.AcademicYearRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AcademicYearService {

    private final AcademicYearRepository academicYearRepository;

    public AcademicYearService(AcademicYearRepository academicYearRepository) {
        this.academicYearRepository = academicYearRepository;
    }

    @Transactional(readOnly = true)
    public List<AcademicYearResponse> findAll() {
        return academicYearRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public AcademicYearResponse findById(Long id) {
        AcademicYear academicYear = academicYearRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AcademicYear", id));
        return toResponse(academicYear);
    }

    @Transactional(readOnly = true)
    public AcademicYearResponse findCurrent() {
        AcademicYear academicYear = academicYearRepository.findByCurrentTrue()
                .orElseThrow(() -> new ResourceNotFoundException("No current academic year found"));
        return toResponse(academicYear);
    }

    @Transactional
    public AcademicYearResponse create(AcademicYearRequest request) {
        if (request.startDate() != null && request.endDate() != null
                && !request.startDate().isBefore(request.endDate())) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
        AcademicYear academicYear = new AcademicYear();
        academicYear.setName(request.name());
        academicYear.setStartDate(request.startDate());
        academicYear.setEndDate(request.endDate());
        academicYear.setCurrent(request.current() != null ? request.current() : false);
        AcademicYear saved = academicYearRepository.save(academicYear);
        return toResponse(saved);
    }

    @Transactional
    public AcademicYearResponse update(Long id, AcademicYearRequest request) {
        AcademicYear academicYear = academicYearRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AcademicYear", id));
        if (request.startDate() != null && request.endDate() != null
                && !request.startDate().isBefore(request.endDate())) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
        academicYear.setName(request.name());
        academicYear.setStartDate(request.startDate());
        academicYear.setEndDate(request.endDate());
        if (request.current() != null) {
            academicYear.setCurrent(request.current());
        }
        AcademicYear saved = academicYearRepository.save(academicYear);
        return toResponse(saved);
    }

    @Transactional
    public void delete(Long id) {
        AcademicYear academicYear = academicYearRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AcademicYear", id));
        academicYearRepository.delete(academicYear);
    }

    private AcademicYearResponse toResponse(AcademicYear entity) {
        return new AcademicYearResponse(
                entity.getId(),
                entity.getName(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.isCurrent()
        );
    }
}
