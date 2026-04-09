package com.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cms.dto.AccreditationReportRequest;
import com.cms.dto.AccreditationReportResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.AccreditationReport;
import com.cms.model.Department;
import com.cms.repository.AccreditationReportRepository;
import com.cms.repository.DepartmentRepository;

@Service
public class AccreditationReportService {

    private final AccreditationReportRepository accreditationReportRepository;
    private final DepartmentRepository departmentRepository;

    public AccreditationReportService(AccreditationReportRepository accreditationReportRepository,
                                      DepartmentRepository departmentRepository) {
        this.accreditationReportRepository = accreditationReportRepository;
        this.departmentRepository = departmentRepository;
    }

    public List<AccreditationReportResponse> findAll() {
        return accreditationReportRepository.findAll().stream().map(this::toResponse).toList();
    }

    public AccreditationReportResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    public AccreditationReportResponse create(AccreditationReportRequest request) {
        Department department = departmentRepository.findById(request.departmentId())
            .orElseThrow(() -> new ResourceNotFoundException("Department", request.departmentId()));
        AccreditationReport e = new AccreditationReport();
        e.setDepartment(department);
        e.setReportType(request.reportType());
        e.setAcademicYear(request.academicYear());
        e.setGeneratedDate(request.generatedDate());
        e.setOverallScore(request.overallScore());
        e.setStatus(request.status() != null ? request.status() : "DRAFT");
        e.setSummary(request.summary());
        return toResponse(accreditationReportRepository.save(e));
    }

    public AccreditationReportResponse update(Long id, AccreditationReportRequest request) {
        AccreditationReport e = getOrThrow(id);
        Department department = departmentRepository.findById(request.departmentId())
            .orElseThrow(() -> new ResourceNotFoundException("Department", request.departmentId()));
        e.setDepartment(department);
        e.setReportType(request.reportType());
        e.setAcademicYear(request.academicYear());
        e.setGeneratedDate(request.generatedDate());
        e.setOverallScore(request.overallScore());
        if (request.status() != null) { e.setStatus(request.status()); }
        e.setSummary(request.summary());
        return toResponse(accreditationReportRepository.save(e));
    }

    public void delete(Long id) {
        if (!accreditationReportRepository.existsById(id)) {
            throw new ResourceNotFoundException("AccreditationReport", id);
        }
        accreditationReportRepository.deleteById(id);
    }

    private AccreditationReport getOrThrow(Long id) {
        return accreditationReportRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("AccreditationReport", id));
    }

    private AccreditationReportResponse toResponse(AccreditationReport e) {
        return new AccreditationReportResponse(
            e.getId(),
            e.getDepartment().getId(), e.getDepartment().getName(),
            e.getReportType(), e.getAcademicYear(),
            e.getGeneratedDate(), e.getOverallScore(),
            e.getStatus(), e.getSummary(),
            e.getCreatedAt(), e.getUpdatedAt()
        );
    }
}
