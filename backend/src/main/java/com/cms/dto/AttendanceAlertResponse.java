package com.cms.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record AttendanceAlertResponse(
    Long id,
    Long studentId,
    String studentName,
    Long courseId,
    String courseName,
    String alertType,
    String message,
    BigDecimal thresholdPercentage,
    BigDecimal currentPercentage,
    Boolean resolved,
    Instant createdAt,
    Instant updatedAt
) {}
