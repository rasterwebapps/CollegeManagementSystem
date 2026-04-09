package com.cms.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LabStaffAssignmentRequest(
    @NotNull(message = "Lab ID is required")
    Long labId,

    @NotBlank(message = "Staff Keycloak ID is required")
    @Size(max = 255, message = "Staff Keycloak ID must not exceed 255 characters")
    String staffKeycloakId,

    @NotBlank(message = "Staff role is required")
    @Size(max = 50, message = "Staff role must not exceed 50 characters")
    String staffRole,

    @NotNull(message = "Assigned date is required")
    LocalDate assignedDate,

    LocalDate endDate,

    Boolean active
) {}
