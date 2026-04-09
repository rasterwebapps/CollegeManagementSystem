package com.cms.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record AnalyticsSnapshotResponse(
    Long id,
    Long departmentId,
    String departmentName,
    Long programId,
    String programName,
    Long semesterId,
    String semesterName,
    String snapshotType,
    String metricName,
    BigDecimal metricValue,
    LocalDate snapshotDate,
    String details,
    Instant createdAt,
    Instant updatedAt
) {}
