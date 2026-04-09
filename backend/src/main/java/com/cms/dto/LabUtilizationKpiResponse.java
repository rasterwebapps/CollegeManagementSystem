package com.cms.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record LabUtilizationKpiResponse(
    Long id,
    Long labId,
    String labName,
    Long semesterId,
    String semesterName,
    Integer totalSlots,
    Integer utilizedSlots,
    BigDecimal utilizationPercentage,
    String peakHours,
    BigDecimal avgOccupancy,
    LocalDate measurementDate,
    Instant createdAt,
    Instant updatedAt
) {}
