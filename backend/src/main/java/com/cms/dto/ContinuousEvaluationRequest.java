package com.cms.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ContinuousEvaluationRequest(
    @NotNull(message = "Student ID is required") Long studentId,
    @NotNull(message = "Course ID is required") Long courseId,
    Long experimentId,
    @NotNull(message = "Semester ID is required") Long semesterId,
    @NotBlank(message = "Evaluation type is required") @Size(max = 50) String evaluationType,
    @NotNull(message = "Marks obtained is required") BigDecimal marksObtained,
    @NotNull(message = "Max marks is required") BigDecimal maxMarks,
    @NotNull(message = "Evaluation date is required") LocalDate evaluationDate,
    String remarks
) {}
