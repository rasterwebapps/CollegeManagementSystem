package com.cms.dto;

import com.cms.model.enums.LabStaffRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record LabStaffAssignmentRequest(
        @NotNull Long labId,
        @NotBlank String userKeycloakId,
        @NotNull LabStaffRole role,
        LocalDate assignedDate
) {
}
