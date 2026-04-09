package com.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cms.dto.LabUtilizationKpiRequest;
import com.cms.dto.LabUtilizationKpiResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Lab;
import com.cms.model.LabUtilizationKpi;
import com.cms.model.Semester;
import com.cms.repository.LabRepository;
import com.cms.repository.LabUtilizationKpiRepository;
import com.cms.repository.SemesterRepository;

@Service
public class LabUtilizationKpiService {

    private final LabUtilizationKpiRepository labUtilizationKpiRepository;
    private final LabRepository labRepository;
    private final SemesterRepository semesterRepository;

    public LabUtilizationKpiService(LabUtilizationKpiRepository labUtilizationKpiRepository,
                                    LabRepository labRepository,
                                    SemesterRepository semesterRepository) {
        this.labUtilizationKpiRepository = labUtilizationKpiRepository;
        this.labRepository = labRepository;
        this.semesterRepository = semesterRepository;
    }

    public List<LabUtilizationKpiResponse> findAll() {
        return labUtilizationKpiRepository.findAll().stream().map(this::toResponse).toList();
    }

    public LabUtilizationKpiResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    public LabUtilizationKpiResponse create(LabUtilizationKpiRequest request) {
        Lab lab = labRepository.findById(request.labId())
            .orElseThrow(() -> new ResourceNotFoundException("Lab", request.labId()));
        Semester semester = semesterRepository.findById(request.semesterId())
            .orElseThrow(() -> new ResourceNotFoundException("Semester", request.semesterId()));
        LabUtilizationKpi e = new LabUtilizationKpi();
        e.setLab(lab);
        e.setSemester(semester);
        e.setTotalSlots(request.totalSlots());
        e.setUtilizedSlots(request.utilizedSlots());
        e.setUtilizationPercentage(request.utilizationPercentage());
        e.setPeakHours(request.peakHours());
        e.setAvgOccupancy(request.avgOccupancy());
        e.setMeasurementDate(request.measurementDate());
        return toResponse(labUtilizationKpiRepository.save(e));
    }

    public LabUtilizationKpiResponse update(Long id, LabUtilizationKpiRequest request) {
        LabUtilizationKpi e = getOrThrow(id);
        Lab lab = labRepository.findById(request.labId())
            .orElseThrow(() -> new ResourceNotFoundException("Lab", request.labId()));
        Semester semester = semesterRepository.findById(request.semesterId())
            .orElseThrow(() -> new ResourceNotFoundException("Semester", request.semesterId()));
        e.setLab(lab);
        e.setSemester(semester);
        e.setTotalSlots(request.totalSlots());
        e.setUtilizedSlots(request.utilizedSlots());
        e.setUtilizationPercentage(request.utilizationPercentage());
        e.setPeakHours(request.peakHours());
        e.setAvgOccupancy(request.avgOccupancy());
        e.setMeasurementDate(request.measurementDate());
        return toResponse(labUtilizationKpiRepository.save(e));
    }

    public void delete(Long id) {
        if (!labUtilizationKpiRepository.existsById(id)) {
            throw new ResourceNotFoundException("LabUtilizationKpi", id);
        }
        labUtilizationKpiRepository.deleteById(id);
    }

    private LabUtilizationKpi getOrThrow(Long id) {
        return labUtilizationKpiRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("LabUtilizationKpi", id));
    }

    private LabUtilizationKpiResponse toResponse(LabUtilizationKpi e) {
        return new LabUtilizationKpiResponse(
            e.getId(),
            e.getLab().getId(), e.getLab().getName(),
            e.getSemester().getId(), e.getSemester().getName(),
            e.getTotalSlots(), e.getUtilizedSlots(),
            e.getUtilizationPercentage(), e.getPeakHours(),
            e.getAvgOccupancy(), e.getMeasurementDate(),
            e.getCreatedAt(), e.getUpdatedAt()
        );
    }
}
