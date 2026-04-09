package com.cms.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record LabFeeResponse(
    Long id, Long labId, String labName, Long courseId, String courseName,
    Long semesterId, String semesterName,
    BigDecimal amount, String description, String status,
    Instant createdAt, Instant updatedAt
) {}
