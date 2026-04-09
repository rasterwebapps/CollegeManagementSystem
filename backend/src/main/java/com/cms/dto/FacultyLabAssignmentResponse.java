package com.cms.dto;

import java.time.Instant;
import java.time.LocalDate;

public record FacultyLabAssignmentResponse(
    Long id,
    Long facultyId,
    String facultyName,
    Long labId,
    String labName,
    Long semesterId,
    String semesterName,
    LocalDate assignedDate,
    Boolean active,
    Instant createdAt,
    Instant updatedAt
) {}
