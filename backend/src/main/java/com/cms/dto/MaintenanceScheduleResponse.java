package com.cms.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record MaintenanceScheduleResponse(
    Long id, Long equipmentId, String equipmentName,
    String maintenanceType, LocalDate scheduledDate, LocalDate completedDate,
    String performedBy, BigDecimal cost, String notes, String status,
    Instant createdAt, Instant updatedAt
) {}
