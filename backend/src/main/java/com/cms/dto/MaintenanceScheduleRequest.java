package com.cms.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MaintenanceScheduleRequest(
    @NotNull(message = "Equipment ID is required") Long equipmentId,
    @NotBlank(message = "Maintenance type is required") @Size(max = 50) String maintenanceType,
    @NotNull(message = "Scheduled date is required") LocalDate scheduledDate,
    LocalDate completedDate,
    @Size(max = 255) String performedBy,
    BigDecimal cost,
    String notes,
    String status
) {}
