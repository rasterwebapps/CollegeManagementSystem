package com.cms.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public record FacultyLabAssignmentRequest(
    @NotNull(message = "Faculty ID is required")
    Long facultyId,

    @NotNull(message = "Lab ID is required")
    Long labId,

    @NotNull(message = "Semester ID is required")
    Long semesterId,

    @NotNull(message = "Assigned date is required")
    LocalDate assignedDate,

    Boolean active
) {}
