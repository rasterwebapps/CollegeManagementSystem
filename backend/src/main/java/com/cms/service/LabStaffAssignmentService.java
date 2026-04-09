package com.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cms.dto.LabStaffAssignmentRequest;
import com.cms.dto.LabStaffAssignmentResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Lab;
import com.cms.model.LabStaffAssignment;
import com.cms.repository.LabRepository;
import com.cms.repository.LabStaffAssignmentRepository;

@Service
public class LabStaffAssignmentService {

    private final LabStaffAssignmentRepository assignmentRepository;
    private final LabRepository labRepository;

    public LabStaffAssignmentService(LabStaffAssignmentRepository assignmentRepository,
                                     LabRepository labRepository) {
        this.assignmentRepository = assignmentRepository;
        this.labRepository = labRepository;
    }

    public List<LabStaffAssignmentResponse> findAll() {
        return assignmentRepository.findAll().stream()
            .map(this::toResponse)
            .toList();
    }

    public LabStaffAssignmentResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    public LabStaffAssignmentResponse create(LabStaffAssignmentRequest request) {
        Lab lab = labRepository.findById(request.labId())
            .orElseThrow(() -> new ResourceNotFoundException("Lab", request.labId()));
        LabStaffAssignment assignment = new LabStaffAssignment();
        assignment.setLab(lab);
        assignment.setStaffKeycloakId(request.staffKeycloakId());
        assignment.setStaffRole(request.staffRole());
        assignment.setAssignedDate(request.assignedDate());
        assignment.setEndDate(request.endDate());
        assignment.setActive(request.active() != null ? request.active() : true);
        return toResponse(assignmentRepository.save(assignment));
    }

    public LabStaffAssignmentResponse update(Long id, LabStaffAssignmentRequest request) {
        LabStaffAssignment assignment = getOrThrow(id);
        Lab lab = labRepository.findById(request.labId())
            .orElseThrow(() -> new ResourceNotFoundException("Lab", request.labId()));
        assignment.setLab(lab);
        assignment.setStaffKeycloakId(request.staffKeycloakId());
        assignment.setStaffRole(request.staffRole());
        assignment.setAssignedDate(request.assignedDate());
        assignment.setEndDate(request.endDate());
        if (request.active() != null) {
            assignment.setActive(request.active());
        }
        return toResponse(assignmentRepository.save(assignment));
    }

    public void delete(Long id) {
        if (!assignmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("LabStaffAssignment", id);
        }
        assignmentRepository.deleteById(id);
    }

    private LabStaffAssignment getOrThrow(Long id) {
        return assignmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("LabStaffAssignment", id));
    }

    private LabStaffAssignmentResponse toResponse(LabStaffAssignment a) {
        return new LabStaffAssignmentResponse(
            a.getId(), a.getLab().getId(), a.getLab().getName(),
            a.getStaffKeycloakId(), a.getStaffRole(),
            a.getAssignedDate(), a.getEndDate(), a.getActive(),
            a.getCreatedAt(), a.getUpdatedAt()
        );
    }
}
