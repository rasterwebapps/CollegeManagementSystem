package com.cms.dto;

import java.time.Instant;
import java.time.LocalDate;

public record StudentEnrollmentResponse(
    Long id,
    Long studentId,
    String studentName,
    Long courseId,
    String courseName,
    Long semesterId,
    String semesterName,
    String labBatchGroup,
    LocalDate enrollmentDate,
    String status,
    Instant createdAt,
    Instant updatedAt
) {}
