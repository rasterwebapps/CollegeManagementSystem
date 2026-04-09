package com.cms.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PracticalExamRequest(
    @NotNull(message = "Course ID is required") Long courseId,
    @NotNull(message = "Semester ID is required") Long semesterId,
    Long experimentId,
    @NotNull(message = "Exam date is required") LocalDate examDate,
    @Size(max = 10) String startTime,
    @Size(max = 10) String endTime,
    Long labId,
    @NotNull(message = "Max marks is required") BigDecimal maxMarks,
    BigDecimal passingMarks,
    @Size(max = 255) String examiner,
    String status
) {}
