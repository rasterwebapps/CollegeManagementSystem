package com.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cms.dto.LabAttendanceRequest;
import com.cms.dto.LabAttendanceResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Experiment;
import com.cms.model.LabAttendance;
import com.cms.model.LabTimetable;
import com.cms.model.StudentProfile;
import com.cms.repository.ExperimentRepository;
import com.cms.repository.LabAttendanceRepository;
import com.cms.repository.LabTimetableRepository;
import com.cms.repository.StudentProfileRepository;

@Service
public class LabAttendanceService {

    private final LabAttendanceRepository labAttendanceRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final ExperimentRepository experimentRepository;
    private final LabTimetableRepository labTimetableRepository;

    public LabAttendanceService(LabAttendanceRepository labAttendanceRepository,
                                StudentProfileRepository studentProfileRepository,
                                ExperimentRepository experimentRepository,
                                LabTimetableRepository labTimetableRepository) {
        this.labAttendanceRepository = labAttendanceRepository;
        this.studentProfileRepository = studentProfileRepository;
        this.experimentRepository = experimentRepository;
        this.labTimetableRepository = labTimetableRepository;
    }

    public List<LabAttendanceResponse> findAll() {
        return labAttendanceRepository.findAll().stream()
            .map(this::toResponse)
            .toList();
    }

    public LabAttendanceResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    public LabAttendanceResponse create(LabAttendanceRequest request) {
        StudentProfile student = studentProfileRepository.findById(request.studentId())
            .orElseThrow(() -> new ResourceNotFoundException("StudentProfile", request.studentId()));
        Experiment experiment = experimentRepository.findById(request.experimentId())
            .orElseThrow(() -> new ResourceNotFoundException("Experiment", request.experimentId()));
        LabTimetable labTimetable = labTimetableRepository.findById(request.labTimetableId())
            .orElseThrow(() -> new ResourceNotFoundException("LabTimetable", request.labTimetableId()));

        LabAttendance la = new LabAttendance();
        mapRequestToEntity(la, request, student, experiment, labTimetable);
        return toResponse(labAttendanceRepository.save(la));
    }

    public LabAttendanceResponse update(Long id, LabAttendanceRequest request) {
        LabAttendance la = getOrThrow(id);
        StudentProfile student = studentProfileRepository.findById(request.studentId())
            .orElseThrow(() -> new ResourceNotFoundException("StudentProfile", request.studentId()));
        Experiment experiment = experimentRepository.findById(request.experimentId())
            .orElseThrow(() -> new ResourceNotFoundException("Experiment", request.experimentId()));
        LabTimetable labTimetable = labTimetableRepository.findById(request.labTimetableId())
            .orElseThrow(() -> new ResourceNotFoundException("LabTimetable", request.labTimetableId()));

        mapRequestToEntity(la, request, student, experiment, labTimetable);
        return toResponse(labAttendanceRepository.save(la));
    }

    public void delete(Long id) {
        if (!labAttendanceRepository.existsById(id)) {
            throw new ResourceNotFoundException("LabAttendance", id);
        }
        labAttendanceRepository.deleteById(id);
    }

    private void mapRequestToEntity(LabAttendance la, LabAttendanceRequest request,
                                    StudentProfile student, Experiment experiment, LabTimetable labTimetable) {
        la.setStudent(student);
        la.setExperiment(experiment);
        la.setLabTimetable(labTimetable);
        la.setAttendanceDate(request.attendanceDate());
        la.setStatus(request.status());
        la.setRemarks(request.remarks());
    }

    private LabAttendance getOrThrow(Long id) {
        return labAttendanceRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("LabAttendance", id));
    }

    private LabAttendanceResponse toResponse(LabAttendance la) {
        return new LabAttendanceResponse(
            la.getId(),
            la.getStudent().getId(),
            la.getStudent().getFirstName() + " " + la.getStudent().getLastName(),
            la.getExperiment().getId(), la.getExperiment().getTitle(),
            la.getLabTimetable().getId(),
            la.getAttendanceDate(), la.getStatus(), la.getRemarks(),
            la.getCreatedAt(), la.getUpdatedAt()
        );
    }
}
