package com.cms.dto;

import java.time.Instant;
import java.time.LocalDate;

public record LabStaffAssignmentResponse(
    Long id,
    Long labId,
    String labName,
    String staffKeycloakId,
    String staffRole,
    LocalDate assignedDate,
    LocalDate endDate,
    Boolean active,
    Instant createdAt,
    Instant updatedAt
) {}
