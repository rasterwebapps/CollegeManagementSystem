package com.cms.service;

import com.cms.dto.LabStaffAssignmentRequest;
import com.cms.dto.LabStaffAssignmentResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Lab;
import com.cms.model.LabStaffAssignment;
import com.cms.repository.LabRepository;
import com.cms.repository.LabStaffAssignmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LabStaffAssignmentService {

    private final LabStaffAssignmentRepository labStaffAssignmentRepository;
    private final LabRepository labRepository;

    public LabStaffAssignmentService(LabStaffAssignmentRepository labStaffAssignmentRepository,
                                     LabRepository labRepository) {
        this.labStaffAssignmentRepository = labStaffAssignmentRepository;
        this.labRepository = labRepository;
    }

    @Transactional(readOnly = true)
    public List<LabStaffAssignmentResponse> findByLabId(Long labId) {
        return labStaffAssignmentRepository.findByLabId(labId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public LabStaffAssignmentResponse findById(Long id) {
        LabStaffAssignment assignment = labStaffAssignmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LabStaffAssignment", id));
        return toResponse(assignment);
    }

    @Transactional
    public LabStaffAssignmentResponse create(LabStaffAssignmentRequest request) {
        Lab lab = labRepository.findById(request.labId())
                .orElseThrow(() -> new ResourceNotFoundException("Lab", request.labId()));

        LabStaffAssignment assignment = new LabStaffAssignment();
        assignment.setLab(lab);
        assignment.setUserKeycloakId(request.userKeycloakId());
        assignment.setRole(request.role());
        assignment.setAssignedDate(request.assignedDate());
        LabStaffAssignment saved = labStaffAssignmentRepository.save(assignment);
        return toResponse(saved);
    }

    @Transactional
    public void delete(Long id) {
        LabStaffAssignment assignment = labStaffAssignmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LabStaffAssignment", id));
        labStaffAssignmentRepository.delete(assignment);
    }

    private LabStaffAssignmentResponse toResponse(LabStaffAssignment entity) {
        return new LabStaffAssignmentResponse(
                entity.getId(),
                entity.getLab().getId(),
                entity.getLab().getName(),
                entity.getUserKeycloakId(),
                entity.getRole(),
                entity.getAssignedDate()
        );
    }
}
