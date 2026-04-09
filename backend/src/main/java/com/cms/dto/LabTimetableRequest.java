package com.cms.dto;

import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LabTimetableRequest(
    @NotNull(message = "Lab ID is required")
    Long labId,

    @NotNull(message = "Course ID is required")
    Long courseId,

    @NotNull(message = "Semester ID is required")
    Long semesterId,

    @NotNull(message = "Faculty ID is required")
    Long facultyId,

    @NotBlank(message = "Day of week is required")
    @Size(max = 10, message = "Day of week must not exceed 10 characters")
    String dayOfWeek,

    @NotNull(message = "Start time is required")
    LocalTime startTime,

    @NotNull(message = "End time is required")
    LocalTime endTime,

    @Size(max = 10, message = "Batch group must not exceed 10 characters")
    String batchGroup,

    String status
) {}
