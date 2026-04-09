package com.cms.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record StudentEnrollmentRequest(
    @NotNull(message = "Student ID is required")
    Long studentId,

    @NotNull(message = "Course ID is required")
    Long courseId,

    @NotNull(message = "Semester ID is required")
    Long semesterId,

    @Size(max = 10, message = "Lab batch group must not exceed 10 characters")
    String labBatchGroup,

    @NotNull(message = "Enrollment date is required")
    LocalDate enrollmentDate,

    String status
) {}
