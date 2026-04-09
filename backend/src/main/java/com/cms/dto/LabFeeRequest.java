package com.cms.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;

public record LabFeeRequest(
    @NotNull(message = "Lab ID is required") Long labId,
    @NotNull(message = "Course ID is required") Long courseId,
    @NotNull(message = "Semester ID is required") Long semesterId,
    @NotNull(message = "Amount is required") BigDecimal amount,
    String description,
    String status
) {}
