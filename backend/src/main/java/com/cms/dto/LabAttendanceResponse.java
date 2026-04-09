package com.cms.dto;

import java.time.Instant;
import java.time.LocalDate;

public record LabAttendanceResponse(
    Long id,
    Long studentId,
    String studentName,
    Long experimentId,
    String experimentTitle,
    Long labTimetableId,
    LocalDate attendanceDate,
    String status,
    String remarks,
    Instant createdAt,
    Instant updatedAt
) {}
