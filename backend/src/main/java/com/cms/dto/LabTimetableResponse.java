package com.cms.dto;

import java.time.Instant;
import java.time.LocalTime;

public record LabTimetableResponse(
    Long id,
    Long labId,
    String labName,
    Long courseId,
    String courseName,
    Long semesterId,
    String semesterName,
    Long facultyId,
    String facultyName,
    String dayOfWeek,
    LocalTime startTime,
    LocalTime endTime,
    String batchGroup,
    String status,
    Instant createdAt,
    Instant updatedAt
) {}
