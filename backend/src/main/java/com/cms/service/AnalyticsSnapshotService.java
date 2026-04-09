package com.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cms.dto.AnalyticsSnapshotRequest;
import com.cms.dto.AnalyticsSnapshotResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.AnalyticsSnapshot;
import com.cms.model.Department;
import com.cms.model.Program;
import com.cms.model.Semester;
import com.cms.repository.AnalyticsSnapshotRepository;
import com.cms.repository.DepartmentRepository;
import com.cms.repository.ProgramRepository;
import com.cms.repository.SemesterRepository;

@Service
public class AnalyticsSnapshotService {

    private final AnalyticsSnapshotRepository analyticsSnapshotRepository;
    private final DepartmentRepository departmentRepository;
    private final ProgramRepository programRepository;
    private final SemesterRepository semesterRepository;

    public AnalyticsSnapshotService(AnalyticsSnapshotRepository analyticsSnapshotRepository,
                                    DepartmentRepository departmentRepository,
                                    ProgramRepository programRepository,
                                    SemesterRepository semesterRepository) {
        this.analyticsSnapshotRepository = analyticsSnapshotRepository;
        this.departmentRepository = departmentRepository;
        this.programRepository = programRepository;
        this.semesterRepository = semesterRepository;
    }

    public List<AnalyticsSnapshotResponse> findAll() {
        return analyticsSnapshotRepository.findAll().stream().map(this::toResponse).toList();
    }

    public AnalyticsSnapshotResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    public AnalyticsSnapshotResponse create(AnalyticsSnapshotRequest request) {
        Department department = departmentRepository.findById(request.departmentId())
            .orElseThrow(() -> new ResourceNotFoundException("Department", request.departmentId()));
        Semester semester = semesterRepository.findById(request.semesterId())
            .orElseThrow(() -> new ResourceNotFoundException("Semester", request.semesterId()));
        Program program = null;
        if (request.programId() != null) {
            program = programRepository.findById(request.programId())
                .orElseThrow(() -> new ResourceNotFoundException("Program", request.programId()));
        }
        AnalyticsSnapshot e = new AnalyticsSnapshot();
        e.setDepartment(department);
        e.setProgram(program);
        e.setSemester(semester);
        e.setSnapshotType(request.snapshotType());
        e.setMetricName(request.metricName());
        e.setMetricValue(request.metricValue());
        e.setSnapshotDate(request.snapshotDate());
        e.setDetails(request.details());
        return toResponse(analyticsSnapshotRepository.save(e));
    }

    public AnalyticsSnapshotResponse update(Long id, AnalyticsSnapshotRequest request) {
        AnalyticsSnapshot e = getOrThrow(id);
        Department department = departmentRepository.findById(request.departmentId())
            .orElseThrow(() -> new ResourceNotFoundException("Department", request.departmentId()));
        Semester semester = semesterRepository.findById(request.semesterId())
            .orElseThrow(() -> new ResourceNotFoundException("Semester", request.semesterId()));
        Program program = null;
        if (request.programId() != null) {
            program = programRepository.findById(request.programId())
                .orElseThrow(() -> new ResourceNotFoundException("Program", request.programId()));
        }
        e.setDepartment(department);
        e.setProgram(program);
        e.setSemester(semester);
        e.setSnapshotType(request.snapshotType());
        e.setMetricName(request.metricName());
        e.setMetricValue(request.metricValue());
        e.setSnapshotDate(request.snapshotDate());
        e.setDetails(request.details());
        return toResponse(analyticsSnapshotRepository.save(e));
    }

    public void delete(Long id) {
        if (!analyticsSnapshotRepository.existsById(id)) {
            throw new ResourceNotFoundException("AnalyticsSnapshot", id);
        }
        analyticsSnapshotRepository.deleteById(id);
    }

    private AnalyticsSnapshot getOrThrow(Long id) {
        return analyticsSnapshotRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("AnalyticsSnapshot", id));
    }

    private AnalyticsSnapshotResponse toResponse(AnalyticsSnapshot e) {
        return new AnalyticsSnapshotResponse(
            e.getId(),
            e.getDepartment().getId(), e.getDepartment().getName(),
            e.getProgram() != null ? e.getProgram().getId() : null,
            e.getProgram() != null ? e.getProgram().getName() : null,
            e.getSemester().getId(), e.getSemester().getName(),
            e.getSnapshotType(), e.getMetricName(), e.getMetricValue(),
            e.getSnapshotDate(), e.getDetails(),
            e.getCreatedAt(), e.getUpdatedAt()
        );
    }
}
