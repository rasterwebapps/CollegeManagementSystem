package com.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cms.dto.AcademicYearRequest;
import com.cms.dto.AcademicYearResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.AcademicYear;
import com.cms.repository.AcademicYearRepository;

@Service
public class AcademicYearService {

    private final AcademicYearRepository academicYearRepository;

    public AcademicYearService(AcademicYearRepository academicYearRepository) {
        this.academicYearRepository = academicYearRepository;
    }

    public List<AcademicYearResponse> findAll() {
        return academicYearRepository.findAll().stream()
            .map(this::toResponse)
            .toList();
    }

    public AcademicYearResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    public AcademicYearResponse create(AcademicYearRequest request) {
        AcademicYear ay = new AcademicYear();
        ay.setName(request.name());
        ay.setStartDate(request.startDate());
        ay.setEndDate(request.endDate());
        ay.setActive(request.active() != null ? request.active() : false);
        return toResponse(academicYearRepository.save(ay));
    }

    public AcademicYearResponse update(Long id, AcademicYearRequest request) {
        AcademicYear ay = getOrThrow(id);
        ay.setName(request.name());
        ay.setStartDate(request.startDate());
        ay.setEndDate(request.endDate());
        if (request.active() != null) {
            ay.setActive(request.active());
        }
        return toResponse(academicYearRepository.save(ay));
    }

    public void delete(Long id) {
        if (!academicYearRepository.existsById(id)) {
            throw new ResourceNotFoundException("AcademicYear", id);
        }
        academicYearRepository.deleteById(id);
    }

    private AcademicYear getOrThrow(Long id) {
        return academicYearRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("AcademicYear", id));
    }

    private AcademicYearResponse toResponse(AcademicYear ay) {
        return new AcademicYearResponse(
            ay.getId(), ay.getName(), ay.getStartDate(), ay.getEndDate(),
            ay.getActive(), ay.getCreatedAt(), ay.getUpdatedAt()
        );
    }
}
