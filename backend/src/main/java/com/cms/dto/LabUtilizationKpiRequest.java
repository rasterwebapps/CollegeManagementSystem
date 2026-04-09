package com.cms.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LabUtilizationKpiRequest(
    @NotNull(message = "Lab ID is required") Long labId,
    @NotNull(message = "Semester ID is required") Long semesterId,
    @NotNull(message = "Total slots is required") Integer totalSlots,
    @NotNull(message = "Utilized slots is required") Integer utilizedSlots,
    BigDecimal utilizationPercentage,
    @Size(max = 100) String peakHours,
    BigDecimal avgOccupancy,
    @NotNull(message = "Measurement date is required") LocalDate measurementDate
) {}
