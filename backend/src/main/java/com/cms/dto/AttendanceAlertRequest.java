package com.cms.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AttendanceAlertRequest(
    @NotNull(message = "Student ID is required")
    Long studentId,

    @NotNull(message = "Course ID is required")
    Long courseId,

    @NotBlank(message = "Alert type is required")
    @Size(max = 50, message = "Alert type must not exceed 50 characters")
    String alertType,

    @NotBlank(message = "Message is required")
    String message,

    BigDecimal thresholdPercentage,

    BigDecimal currentPercentage
) {}
