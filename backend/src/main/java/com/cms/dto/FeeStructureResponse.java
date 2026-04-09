package com.cms.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record FeeStructureResponse(
    Long id, Long programId, String programName, Long semesterId, String semesterName,
    String feeType, BigDecimal amount, String currency,
    LocalDate effectiveFrom, LocalDate effectiveTo, String status,
    Instant createdAt, Instant updatedAt
) {}
