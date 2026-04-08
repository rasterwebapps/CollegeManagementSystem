package com.cms.dto;

import com.cms.model.enums.LabStaffRole;

import java.time.LocalDate;

public record LabStaffAssignmentResponse(
        Long id,
        Long labId,
        String labName,
        String userKeycloakId,
        LabStaffRole role,
        LocalDate assignedDate
) {
}
