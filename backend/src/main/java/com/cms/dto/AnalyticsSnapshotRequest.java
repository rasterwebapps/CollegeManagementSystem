package com.cms.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AnalyticsSnapshotRequest(
    @NotNull(message = "Department ID is required") Long departmentId,
    Long programId,
    @NotNull(message = "Semester ID is required") Long semesterId,
    @NotBlank(message = "Snapshot type is required") @Size(max = 50) String snapshotType,
    @NotBlank(message = "Metric name is required") @Size(max = 100) String metricName,
    @NotNull(message = "Metric value is required") BigDecimal metricValue,
    @NotNull(message = "Snapshot date is required") LocalDate snapshotDate,
    String details
) {}
