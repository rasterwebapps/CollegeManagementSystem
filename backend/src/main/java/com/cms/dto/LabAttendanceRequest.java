package com.cms.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LabAttendanceRequest(
    @NotNull(message = "Student ID is required")
    Long studentId,

    @NotNull(message = "Experiment ID is required")
    Long experimentId,

    @NotNull(message = "Lab timetable ID is required")
    Long labTimetableId,

    @NotNull(message = "Attendance date is required")
    LocalDate attendanceDate,

    @NotBlank(message = "Status is required")
    @Size(max = 20, message = "Status must not exceed 20 characters")
    String status,

    String remarks
) {}
